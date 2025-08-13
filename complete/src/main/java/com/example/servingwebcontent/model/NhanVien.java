package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 👨‍💼 NhanVien Entity - Advanced HR Management
 */
@Entity
@Table(name = "nhan_vien",
       indexes = {
           @Index(name = "idx_ma_nhan_vien", columnList = "maNhanVien"),
           @Index(name = "idx_phong_ban", columnList = "phongBan"),
           @Index(name = "idx_chuc_vu", columnList = "chucVu"),
           @Index(name = "idx_trang_thai", columnList = "trangThai"),
           @Index(name = "idx_created_at", columnList = "createdAt")
       })
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Mã nhân viên không được để trống")
    @Size(min = 5, max = 20, message = "Mã nhân viên phải từ 5-20 ký tự")
    @Pattern(regexp = "^NV\\d{3,6}$", message = "Mã nhân viên phải theo format: NV001")
    @Column(name = "ma_nhan_vien", unique = true, nullable = false, length = 20)
    private String maNhanVien;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2-100 ký tự")
    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioi_tinh")
    private GioiTinh gioiTinh;

    public enum GioiTinh {
        NAM("Nam", "👨", "#4A90E2"),
        NU("Nữ", "👩", "#E24A90"),
        KHAC("Khác", "👤", "#7ED321");

        private final String ten;
        private final String icon;
        private final String color;

        GioiTinh(String ten, String icon, String color) {
            this.ten = ten;
            this.icon = icon;
            this.color = color;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public String getColor() { return color; }
    }

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(\\+84|0)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    @Column(name = "so_dien_thoai", nullable = false, length = 15)
    private String soDienThoai;

    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được quá 100 ký tự")
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 200, message = "Địa chỉ không được quá 200 ký tự")
    @Column(name = "dia_chi", length = 200)
    private String diaChi;

    @Enumerated(EnumType.STRING)
    @Column(name = "phong_ban", nullable = false)
    private PhongBan phongBan;

    public enum PhongBan {
        QUAN_LY("Quản Lý", "👑", "#FFD700", 1),
        BAN_HANG("Bán Hàng", "🤝", "#81C784", 2),
        KE_TOAN("Kế Toán", "🧮", "#64B5F6", 3),
        KHO("Kho", "📦", "#FFB74D", 4),
        MARKETING("Marketing", "📢", "#BA68C8", 5);

        private final String ten;
        private final String icon;
        private final String color;
        private final int thuTu;

        PhongBan(String ten, String icon, String color, int thuTu) {
            this.ten = ten;
            this.icon = icon;
            this.color = color;
            this.thuTu = thuTu;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public String getColor() { return color; }
        public int getThuTu() { return thuTu; }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "chuc_vu", nullable = false)
    private ChucVu chucVu;

    public enum ChucVu {
        GIAM_DOC("Giám Đốc", "🎯", 10),
        TRUONG_PHONG("Trưởng Phòng", "👨‍💼", 😎,
        PHO_PHONG("Phó Phòng", "👩‍💼", 6),
        NHAN_VIEN_CHINH("Nhân Viên Chính", "⭐", 4),
        NHAN_VIEN("Nhân Viên", "👤", 2),
        THUC_TAP_SINH("Thực Tập Sinh", "🎓", 1);

        private final String ten;
        private final String icon;
        private final int capBac;

        ChucVu(String ten, String icon, int capBac) {
            this.ten = ten;
            this.icon = icon;
            this.capBac = capBac;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public int getCapBac() { return capBac; }
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Ngày vào làm không được là tương lai")
    @Column(name = "ngay_vao_lam", nullable = false)
    private LocalDate ngayVaoLam;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngay_nghi_viec")
    private LocalDate ngayNghiViec;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_hop_dong", nullable = false)
    private LoaiHopDong loaiHopDong;

    public enum LoaiHopDong {
        THU_VIEC("Thử Việc", "⏳", "#FFA726"),
        XAC_DINH_THOI_HAN("Xác Định Thời Hạn", "📅", "#66BB6A"),
        KHONG_XAC_DINH_THOI_HAN("Không Xác Định Thời Hạn", "♾️", "#42A5F5"),
        BAN_THOI_GIAN("Bán Thời Gian", "⏰", "#AB47BC");

        private final String ten;
        private final String icon;
        private final String color;

        LoaiHopDong(String ten, String icon, String color) {
            this.ten = ten;
            this.icon = icon;
            this.color = color;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public String getColor() { return color; }
    }

    @NotNull(message = "Lương cơ bản không được để trống")
    @DecimalMin(value = "1000000.0", message = "Lương cơ bản phải ít nhất 1,000,000 VND")
    @DecimalMax(value = "100000000.0", message = "Lương cơ bản không được quá 100,000,000 VND")
    @Column(name = "luong_co_ban", nullable = false, precision = 12, scale = 2)
    private Double luongCoBan;

    @DecimalMin(value = "0.0", message = "Hoa hồng không được âm")
    @Column(name = "hoa_hong", precision = 10, scale = 2)
    private Double hoaHong = 0.0;

    @DecimalMin(value = "0.0", message = "Thưởng không được âm")
    @Column(name = "thuong", precision = 10, scale = 2)
    private Double thuong = 0.0;

    @DecimalMin(value = "0.0", message = "Phụ cấp không được âm")
    @Column(name = "phu_cap", precision = 10, scale = 2)
    private Double phuCap = 0.0;

    @DecimalMin(value = "0.0", message = "Doanh số tháng không được âm")
    @Column(name = "doanh_so_thang", precision = 15, scale = 2)
    private Double doanhSoThang = 0.0;

    @Min(value = 0, message = "Số đơn hàng không được âm")
    @Column(name = "so_don_hang_thang")
    private Integer soDonHangThang = 0;

    @DecimalMin(value = "0.0", message = "Hiệu suất không được âm")
    @DecimalMax(value = "200.0", message = "Hiệu suất không được quá 200%")
    @Column(name = "hieu_suat_phan_tram", precision = 5, scale = 2)
    private Double hieuSuatPhanTram = 100.0;

    @Min(value = 0, message = "Số ngày làm việc không được âm")
    @Max(value = 31, message = "Số ngày làm việc không được quá 31")
    @Column(name = "so_ngay_lam_viec_thang")
    private Integer soNgayLamViecThang = 0;

    @Min(value = 0, message = "Số ngày nghỉ không được âm")
    @Column(name = "so_ngay_nghi_thang")
    private Integer soNgayNghiThang = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false)
    private TrangThaiNhanVien trangThai;

    public enum TrangThaiNhanVien {
        DANG_LAM_VIEC("Đang Làm Việc", "success", "✅"),
        NGHI_PHEP("Nghỉ Phép", "warning", "🏖️"),
        TAM_NGHI("Tạm Nghỉ", "secondary", "⏸️"),
        NGHI_VIEC("Nghỉ Việc", "danger", "❌");

        private final String ten;
        private final String bootstrapClass;
        private final String icon;

        TrangThaiNhanVien(String ten, String bootstrapClass, String icon) {
            this.ten = ten;
            this.bootstrapClass = bootstrapClass;
            this.icon = icon;
        }

        public String getTen() { return ten; }
        public String getBootstrapClass() { return bootstrapClass; }
        public String getIcon() { return icon; }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "danh_gia_hieu_suat")
    private DanhGiaHieuSuat danhGiaHieuSuat;

    public enum DanhGiaHieuSuat {
        XUAT_SAC("Xuất Sắc", "🌟", "#FFD700", 95.0),
        TOT("Tốt", "⭐", "#4CAF50", 85.0),
        TRUNG_BINH("Trung Bình", "👍", "#FF9800", 70.0),
        YEU("Yếu", "👎", "#F44336", 50.0),
        CHUA_DANH_GIA("Chưa Đánh Giá", "❓", "#9E9E9E", 0.0);

        private final String ten;
        private final String icon;
        private final String color;
        private final double nguongHieuSuat;

        DanhGiaHieuSuat(String ten, String icon, String color, double nguongHieuSuat) {
            this.ten = ten;
            this.icon = icon;
            this.color = color;
            this.nguongHieuSuat = nguongHieuSuat;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public String getColor() { return color; }
        public double getNguongHieuSuat() { return nguongHieuSuat; }

        public static DanhGiaHieuSuat xacDinhDanhGia(double hieuSuat) {
            if (hieuSuat >= XUAT_SAC.nguongHieuSuat) return XUAT_SAC;
            if (hieuSuat >= TOT.nguongHieuSuat) return TOT;
            if (hieuSuat >= TRUNG_BINH.nguongHieuSuat) return TRUNG_BINH;
            if (hieuSuat >= YEU.nguongHieuSuat) return YEU;
            return CHUA_DANH_GIA;
        }
    }

    @Size(max = 500, message = "Ghi chú không được quá 500 ký tự")
    @Column(name = "ghi_chu", length = 500)
    private String ghiChu;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GiaoDich> giaoDichList = new ArrayList<>();

    // =================== CONSTRUCTORS ===================
    
    public NhanVien() {
        this.trangThai = TrangThaiNhanVien.DANG_LAM_VIEC;
        this.danhGiaHieuSuat = DanhGiaHieuSuat.CHUA_DANH_GIA;
        this.hoaHong = 0.0;
        this.thuong = 0.0;
        this.phuCap = 0.0;
        this.doanhSoThang = 0.0;
        this.soDonHangThang = 0;
        this.hieuSuatPhanTram = 100.0;
        this.soNgayLamViecThang = 0;
        this.soNgayNghiThang = 0;
    }

    public NhanVien(String maNhanVien, String hoTen, PhongBan phongBan, 
                    ChucVu chucVu, Double luongCoBan) {
        this();
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.phongBan = phongBan;
        this.chucVu = chucVu;
        this.luongCoBan = luongCoBan;
        this.ngayVaoLam = LocalDate.now();
        this.loaiHopDong = LoaiHopDong.THU_VIEC;
    }

    // =================== BUSINESS METHODS ===================
    
    /**
     * 💰 Tính tổng lương tháng
     */
    public Double tinhTongLuongThang() {
        double tongLuong = luongCoBan != null ? luongCoBan : 0.0;
        tongLuong += hoaHong != null ? hoaHong : 0.0;
        tongLuong += thuong != null ? thuong : 0.0;
        tongLuong += phuCap != null ? phuCap : 0.0;
        return tongLuong;
    }

    /**
     * 📊 Tính hoa hồng dựa trên doanh số
     */
    public Double tinhHoaHongTheoDoanhSo() {
        if (doanhSoThang == null || doanhSoThang <= 0) return 0.0;
        
        double tyLeHoaHong = switch (chucVu) {
            case GIAM_DOC -> 0.05; // 5%
            case TRUONG_PHONG -> 0.04; // 4%
            case PHO_PHONG -> 0.03; // 3%
            case NHAN_VIEN_CHINH -> 0.02; // 2%
            case NHAN_VIEN -> 0.015; // 1.5%
            case THUC_TAP_SINH -> 0.01; // 1%
        };
        
        return doanhSoThang * tyLeHoaHong;
    }

    /**
     * 🎯 Cập nhật hiệu suất và đánh giá tự động
     */
    public void capNhatHieuSuat() {
        if (soNgayLamViecThang != null && soNgayLamViecThang > 0) {
            double hieuSuatChamCong = (soNgayLamViecThang / 22.0) * 100;
            
            double hieuSuatDoanhSo = 100.0;
            if (phongBan == PhongBan.BAN_HANG && doanhSoThang != null) {
                double doanhSoMucTieu = 50000000.0; // 50M VND mục tiêu
                hieuSuatDoanhSo = Math.min(150.0, (doanhSoThang / doanhSoMucTieu) * 100);
            }
            
            this.hieuSuatPhanTram = (hieuSuatChamCong * 0.7) + (hieuSuatDoanhSo * 0.3);
        }
        
        this.danhGiaHieuSuat = DanhGiaHieuSuat.xacDinhDanhGia(this.hieuSuatPhanTram);
        this.hoaHong = tinhHoaHongTheoDoanhSo();
    }

    /**
     * 📅 Tính số năm kinh nghiệm
     */
    public int tinhSoNamKinhNghiem() {
        if (ngayVaoLam == null) return 0;
        LocalDate ngayKetThuc = ngayNghiViec != null ? ngayNghiViec : LocalDate.now();
        return Period.between(ngayVaoLam, ngayKetThuc).getYears();
    }

    /**
     * 🎂 Tính tuổi
     */
    public int tinhTuoi() {
        if (ngaySinh == null) return 0;
        return Period.between(ngaySinh, LocalDate.now()).getYears();
    }

    /**
     * ⚡ Kiểm tra có đủ điều kiện thăng chức không
     */
    public boolean coDieuKienThangChuc() {
        if (tinhSoNamKinhNghiem() < 2) return false;
        if (hieuSuatPhanTram == null || hieuSuatPhanTram < 85.0) return false;
        if (trangThai != TrangThaiNhanVien.DANG_LAM_VIEC) return false;
        return true;
    }

    /**
     * 📈 Xử lý bán hàng
     */
    public void xuLyBanHang(Double giaTriDonHang) {
        if (giaTriDonHang == null || giaTriDonHang <= 0) return;
        
        this.doanhSoThang += giaTriDonHang;
        this.soDonHangThang++;
        capNhatHieuSuat();
    }

    /**
     * 🎨 Lấy avatar mặc định theo giới tính
     */
    public String getAvatarUrl() {
        return switch (gioiTinh != null ? gioiTinh : GioiTinh.KHAC) {
            case NAM -> "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&h=150&fit=crop&crop=face";
            case NU -> "https://images.unsplash.com/photo-1494790108755-2616b612b5c5?w=150&h=150&fit=crop&crop=face";
            case KHAC -> "https://images.unsplash.com/photo-1511367461989-f85a21fda167?w=150&h=150&fit=crop&crop=face";
        };
    }

    // =================== GETTERS & SETTERS ===================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public GioiTinh getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(GioiTinh gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public PhongBan getPhongBan() { return phongBan; }
    public void setPhongBan(PhongBan phongBan) { this.phongBan = phongBan; }

    public ChucVu getChucVu() { return chucVu; }
    public void setChucVu(ChucVu chucVu) { this.chucVu = chucVu; }

    public LocalDate getNgayVaoLam() { return ngayVaoLam; }
    public void setNgayVaoLam(LocalDate ngayVaoLam) { this.ngayVaoLam = ngayVaoLam; }

    public LocalDate getNgayNghiViec() { return ngayNghiViec; }
    public void setNgayNghiViec(LocalDate ngayNghiViec) { this.ngayNghiViec = ngayNghiViec; }

    public LoaiHopDong getLoaiHopDong() { return loaiHopDong; }
    public void setLoaiHopDong(LoaiHopDong loaiHopDong) { this.loaiHopDong = loaiHopDong; }

    public Double getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(Double luongCoBan) { this.luongCoBan = luongCoBan; }

    public Double getHoaHong() { return hoaHong; }
    public void setHoaHong(Double hoaHong) { this.hoaHong = hoaHong; }

    public Double getThuong() { return thuong; }
    public void setThuong(Double thuong) { this.thuong = thuong; }

    public Double getPhuCap() { return phuCap; }
    public void setPhuCap(Double phuCap) { this.phuCap = phuCap; }

    public Double getDoanhSoThang() { return doanhSoThang; }
    public void setDoanhSoThang(Double doanhSoThang) { 
        this.doanhSoThang = doanhSoThang; 
        capNhatHieuSuat();
    }

    public Integer getSoDonHangThang() { return soDonHangThang; }
    public void setSoDonHangThang(Integer soDonHangThang) { this.soDonHangThang = soDonHangThang; }

    public Double getHieuSuatPhanTram() { return hieuSuatPhanTram; }
    public void setHieuSuatPhanTram(Double hieuSuatPhanTram) { 
        this.hieuSuatPhanTram = hieuSuatPhanTram;
        this.danhGiaHieuSuat = DanhGiaHieuSuat.xacDinhDanhGia(hieuSuatPhanTram);
    }

    public Integer getSoNgayLamViecThang() { return soNgayLamViecThang; }
    public void setSoNgayLamViecThang(Integer soNgayLamViecThang) { 
        this.soNgayLamViecThang = soNgayLamViecThang; 
        capNhatHieuSuat();
    }

    public Integer getSoNgayNghiThang() { return soNgayNghiThang; }
    public void setSoNgayNghiThang(Integer soNgayNghiThang) { this.soNgayNghiThang = soNgayNghiThang; }

    public TrangThaiNhanVien getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiNhanVien trangThai) { this.trangThai = trangThai; }

    public DanhGiaHieuSuat getDanhGiaHieuSuat() { return danhGiaHieuSuat; }
    public void setDanhGiaHieuSuat(DanhGiaHieuSuat danhGiaHieuSuat) { this.danhGiaHieuSuat = danhGiaHieuSuat; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<GiaoDich> getGiaoDichList() { return giaoDichList; }
    public void setGiaoDichList(List<GiaoDich> giaoDichList) { this.giaoDichList = giaoDichList; }

    // =================== EQUALS & HASHCODE ===================
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NhanVien nhanVien = (NhanVien) obj;
        return Objects.equals(maNhanVien, nhanVien.maNhanVien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhanVien);
    }

    @Override
    public String toString() {
        return String.format("NhanVien{id=%d, ma='%s', ten='%s', phongBan=%s, chucVu=%s, luong=%.0f, trangThai=%s}", 
                           id, maNhanVien, hoTen, phongBan, chucVu, luongCoBan, trangThai);
    }

    public String getSummary() {
        return String.format("%s %s (%s - %s) - %,.0f₫", 
                           phongBan.getIcon(), hoTen, phongBan.getTen(), chucVu.getTen(), tinhTongLuongThang());
    }
}
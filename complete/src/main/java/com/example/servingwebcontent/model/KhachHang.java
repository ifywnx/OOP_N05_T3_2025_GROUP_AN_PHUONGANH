package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 👥 KhachHang Entity - Advanced Customer Management
 * 
 * Quản lý khách hàng với loyalty system phức tạp:
 * - Customer segmentation (Thường/Premium/VIP)
 * - Loyalty points và spending tracking
 * - Birthday promotions và special offers
 * - Customer lifecycle management
 * - Advanced analytics và behavior tracking
 */
@Entity
@Table(name = "khach_hang",
       indexes = {
           @Index(name = "idx_ma_khach_hang", columnList = "maKhachHang"),
           @Index(name = "idx_loai_khach_hang", columnList = "loaiKhachHang"),
           @Index(name = "idx_email", columnList = "email"),
           @Index(name = "idx_sdt", columnList = "soDienThoai"),
           @Index(name = "idx_created_at", columnList = "createdAt")
       })
public class KhachHang {

    // =================== PRIMARY KEY ===================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // =================== BASIC INFORMATION ===================
    
    @NotBlank(message = "Mã khách hàng không được để trống")
    @Size(min = 5, max = 20, message = "Mã khách hàng phải từ 5-20 ký tự")
    @Pattern(regexp = "^KH\\d{3,6}$", message = "Mã khách hàng phải theo format: KH001")
    @Column(name = "ma_khach_hang", unique = true, nullable = false, length = 20)
    private String maKhachHang;

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

    // =================== CONTACT INFORMATION ===================
    
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

    @Size(max = 50, message = "Khu vực không được quá 50 ký tự")
    @Column(name = "khu_vuc", length = 50)
    private String khuVuc;

    // =================== CUSTOMER TIER SYSTEM ===================
    
    @Enumerated(EnumType.STRING)
    @Column(name = "loai_khach_hang", nullable = false)
    private LoaiKhachHang loaiKhachHang;

    public enum LoaiKhachHang {
        THUONG("Thường", "👤", "#81C784", 0, 0.0, 2000000.0),
        PREMIUM("Premium", "⭐", "#BA68C8", 5, 2000000.0, 5000000.0),
        VIP("VIP", "👑", "#FFD700", 10, 5000000.0, Double.MAX_VALUE);

        private final String ten;
        private final String icon;
        private final String color;
        private final int phanTramGiamGia;
        private final double chiTieuToiThieu;
        private final double chiTieuToiDa;

        LoaiKhachHang(String ten, String icon, String color, int phanTramGiamGia, 
                      double chiTieuToiThieu, double chiTieuToiDa) {
            this.ten = ten;
            this.icon = icon;
            this.color = color;
            this.phanTramGiamGia = phanTramGiamGia;
            this.chiTieuToiThieu = chiTieuToiThieu;
            this.chiTieuToiDa = chiTieuToiDa;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public String getColor() { return color; }
        public int getPhanTramGiamGia() { return phanTramGiamGia; }
        public double getChiTieuToiThieu() { return chiTieuToiThieu; }
        public double getChiTieuToiDa() { return chiTieuToiDa; }

        public static LoaiKhachHang xacDinhLoaiKhachHang(double tongChiTieu) {
            for (LoaiKhachHang loai : values()) {
                if (tongChiTieu >= loai.chiTieuToiThieu && tongChiTieu < loai.chiTieuToiDa) {
                    return loai;
                }
            }
            return THUONG;
        }
    }

    // =================== LOYALTY & FINANCIAL TRACKING ===================
    
    @Min(value = 0, message = "Điểm tích lũy không được âm")
    @Column(name = "diem_tich_luy")
    private Integer diemTichLuy = 0;

    @DecimalMin(value = "0.0", message = "Tổng chi tiêu không được âm")
    @Column(name = "tong_chi_tieu", precision = 15, scale = 2)
    private Double tongChiTieu = 0.0;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "lan_mua_cuoi")
    private LocalDate lanMuaCuoi;

    @Min(value = 0, message = "Số lần mua hàng không được âm")
    @Column(name = "so_lan_mua_hang")
    private Integer soLanMuaHang = 0;

    @DecimalMin(value = "0.0", message = "Giá trị đơn hàng trung bình không được âm")
    @Column(name = "gia_tri_don_hang_tb", precision = 12, scale = 2)
    private Double giaTriDonHangTrungBinh = 0.0;

    // =================== PREFERENCES & BEHAVIOR ===================
    
    @Size(max = 100, message = "Sản phẩm yêu thích không được quá 100 ký tự")
    @Column(name = "san_pham_yeu_thich", length = 100)
    private String sanPhamYeuThich;

    @Column(name = "nhan_email_marketing")
    private Boolean nhanEmailMarketing = true;

    @Column(name = "nhan_sms_khuyen_mai")
    private Boolean nhanSmsKhuyenMai = true;

    @Size(max = 500, message = "Ghi chú không được quá 500 ký tự")
    @Column(name = "ghi_chu", length = 500)
    private String ghiChu;

    // =================== STATUS & LIFECYCLE ===================
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false)
    private TrangThaiKhachHang trangThai;

    public enum TrangThaiKhachHang {
        HOAT_DONG("Hoạt Động", "success", "✅"),
        TAM_KHOA("Tạm Khóa", "warning", "⚠️"),
        KHOA_VINH_VIEN("Khóa Vĩnh Viễn", "danger", "❌"),
        CHUA_KICH_HOAT("Chưa Kích Hoạt", "secondary", "⏳");

        private final String ten;
        private final String bootstrapClass;
        private final String icon;

        TrangThaiKhachHang(String ten, String bootstrapClass, String icon) {
            this.ten = ten;
            this.bootstrapClass = bootstrapClass;
            this.icon = icon;
        }

        public String getTen() { return ten; }
        public String getBootstrapClass() { return bootstrapClass; }
        public String getIcon() { return icon; }
    }

    // =================== AUDIT FIELDS ===================
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // =================== RELATIONSHIPS ===================
    
    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GiaoDich> giaoDichList = new ArrayList<>();

    // =================== CONSTRUCTORS ===================
    
    public KhachHang() {
        this.loaiKhachHang = LoaiKhachHang.THUONG;
        this.trangThai = TrangThaiKhachHang.HOAT_DONG;
        this.diemTichLuy = 0;
        this.tongChiTieu = 0.0;
        this.soLanMuaHang = 0;
        this.giaTriDonHangTrungBinh = 0.0;
        this.nhanEmailMarketing = true;
        this.nhanSmsKhuyenMai = true;
    }

    public KhachHang(String maKhachHang, String hoTen, String soDienThoai) {
        this();
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
    }

    // =================== BUSINESS METHODS ===================
    
    /**
     * 🎯 Cập nhật loại khách hàng dựa trên tổng chi tiêu
     */
    public void capNhatLoaiKhachHang() {
        LoaiKhachHang loaiMoi = LoaiKhachHang.xacDinhLoaiKhachHang(this.tongChiTieu);
        if (this.loaiKhachHang != loaiMoi) {
            this.loaiKhachHang = loaiMoi;
        }
    }

    /**
     * 💰 Xử lý mua hàng - cập nhật điểm, chi tiêu, statistics
     */
    public void capNhatThongTinMuaHang(Double giaTriDonHang, Integer soLuong) {
        if (giaTriDonHang == null || giaTriDonHang <= 0) return;
        
        // Cập nhật tổng chi tiêu
        this.tongChiTieu += giaTriDonHang;
        
        // Cập nhật số lần mua hàng
        this.soLanMuaHang++;
        
        // Cập nhật giá trị đơn hàng trung bình
        this.giaTriDonHangTrungBinh = this.tongChiTieu / this.soLanMuaHang;
        
        // Cập nhật điểm tích lũy (1 điểm = 10,000 VND)
        int diemMoi = (int) (giaTriDonHang / 10000);
        this.diemTichLuy += diemMoi;
        
        // Cập nhật lần mua cuối
        this.lanMuaCuoi = LocalDate.now();
        
        // Cập nhật loại khách hàng
        capNhatLoaiKhachHang();
    }

    /**
     * 🎁 Sử dụng điểm tích lũy (1 điểm = 1,000 VND)
     */
    public boolean suDungDiemTichLuy(int diemSuDung) {
        if (diemSuDung <= 0 || diemSuDung > this.diemTichLuy) return false;
        
        this.diemTichLuy -= diemSuDung;
        return true;
    }

    /**
     * 💎 Tính giá trị điểm tích lũy (VND)
     */
    public Double tinhGiaTriDiemTichLuy() {
        return this.diemTichLuy * 1000.0;
    }

    /**
     * 🎂 Kiểm tra có phải sinh nhật hôm nay không
     */
    public boolean isSinhNhatHomNay() {
        if (ngaySinh == null) return false;
        LocalDate today = LocalDate.now();
        return ngaySinh.getMonth() == today.getMonth() && 
               ngaySinh.getDayOfMonth() == today.getDayOfMonth();
    }

    /**
     * 📊 Tính % tiến độ lên hạng tiếp theo
     */
    public double tinhTienDoLenHang() {
        LoaiKhachHang hangTiepTheo = null;
        
        switch (this.loaiKhachHang) {
            case THUONG -> hangTiepTheo = LoaiKhachHang.PREMIUM;
            case PREMIUM -> hangTiepTheo = LoaiKhachHang.VIP;
            case VIP -> { return 100.0; } // Đã VIP rồi
        }
        
        if (hangTiepTheo == null) return 100.0;
        
        double chiTieuCanThiet = hangTiepTheo.getChiTieuToiThieu();
        double tieuDeCanThiet = chiTieuCanThiet - this.tongChiTieu;
        
        if (tieuDeCanThiet <= 0) return 100.0;
        
        return Math.min(100.0, (this.tongChiTieu / chiTieuCanThiet) * 100);
    }

    /**
     * 💰 Tính số tiền cần chi thêm để lên hạng
     */
    public Double tinhSoTienCanChiThemDeLenHang() {
        LoaiKhachHang hangTiepTheo = null;
        
        switch (this.loaiKhachHang) {
            case THUONG -> hangTiepTheo = LoaiKhachHang.PREMIUM;
            case PREMIUM -> hangTiepTheo = LoaiKhachHang.VIP;
            case VIP -> { return 0.0; }
        }
        
        if (hangTiepTheo == null) return 0.0;
        
        double soTienCanThem = hangTiepTheo.getChiTieuToiThieu() - this.tongChiTieu;
        return Math.max(0.0, soTienCanThem);
    }

    /**
     * 📈 Kiểm tra khách hàng có loyal không (mua trong 3 tháng gần đây)
     */
    public boolean isKhachHangLoyal() {
        if (lanMuaCuoi == null) return false;
        return lanMuaCuoi.isAfter(LocalDate.now().minusMonths(3));
    }

    /**
     * 🎨 Lấy avatar mặc định theo giới tính
     */
    public String getAvatarUrl() {
        return switch (gioiTinh != null ? gioiTinh : GioiTinh.KHAC) {
            case NAM -> "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&h=150&fit=crop&crop=face";
            case NU -> "https://images.unsplash.com/photo-1494790108755-2616b612b5c5?w=150&h=150&fit=crop&crop=face";
            case KHAC -> "https://images.unsplash.com/photo-1511367461989-f85a21fda167?w=150&h=150&fit=crop&crop=face";
        };
    }

    /**
     * 🏆 Lấy badge hiển thị cho customer tier
     */
    public String getTierBadge() {
        return String.format("<span class='badge' style='background-color: %s'>%s %s</span>", 
                           loaiKhachHang.getColor(), loaiKhachHang.getIcon(), loaiKhachHang.getTen());
    }

    // =================== GETTERS & SETTERS ===================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

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

    public String getKhuVuc() { return khuVuc; }
    public void setKhuVuc(String khuVuc) { this.khuVuc = khuVuc; }

    public LoaiKhachHang getLoaiKhachHang() { return loaiKhachHang; }
    public void setLoaiKhachHang(LoaiKhachHang loaiKhachHang) { this.loaiKhachHang = loaiKhachHang; }

    public Integer getDiemTichLuy() { return diemTichLuy; }
    public void setDiemTichLuy(Integer diemTichLuy) { this.diemTichLuy = diemTichLuy; }

    public Double getTongChiTieu() { return tongChiTieu; }
    public void setTongChiTieu(Double tongChiTieu) { 
        this.tongChiTieu = tongChiTieu; 
        capNhatLoaiKhachHang();
    }

    public LocalDate getLanMuaCuoi() { return lanMuaCuoi; }
    public void setLanMuaCuoi(LocalDate lanMuaCuoi) { this.lanMuaCuoi = lanMuaCuoi; }

    public Integer getSoLanMuaHang() { return soLanMuaHang; }
    public void setSoLanMuaHang(Integer soLanMuaHang) { this.soLanMuaHang = soLanMuaHang; }

    public Double getGiaTriDonHangTrungBinh() { return giaTriDonHangTrungBinh; }
    public void setGiaTriDonHangTrungBinh(Double giaTriDonHangTrungBinh) { 
        this.giaTriDonHangTrungBinh = giaTriDonHangTrungBinh; 
    }

    public String getSanPhamYeuThich() { return sanPhamYeuThich; }
    public void setSanPhamYeuThich(String sanPhamYeuThich) { this.sanPhamYeuThich = sanPhamYeuThich; }

    public Boolean getNhanEmailMarketing() { return nhanEmailMarketing; }
    public void setNhanEmailMarketing(Boolean nhanEmailMarketing) { this.nhanEmailMarketing = nhanEmailMarketing; }

    public Boolean getNhanSmsKhuyenMai() { return nhanSmsKhuyenMai; }
    public void setNhanSmsKhuyenMai(Boolean nhanSmsKhuyenMai) { this.nhanSmsKhuyenMai = nhanSmsKhuyenMai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public TrangThaiKhachHang getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiKhachHang trangThai) { this.trangThai = trangThai; }

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
        KhachHang khachHang = (KhachHang) obj;
        return Objects.equals(maKhachHang, khachHang.maKhachHang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKhachHang);
    }

    // =================== TO STRING ===================
    
    @Override
    public String toString() {
        return String.format("KhachHang{id=%d, ma='%s', ten='%s', loai=%s, tongChiTieu=%.0f, trangThai=%s}", 
                           id, maKhachHang, hoTen, loaiKhachHang, tongChiTieu, trangThai);
    }

    /**
     * 📊 Lấy thông tin tóm tắt cho dashboard
     */
    public String getSummary() {
        return String.format("%s %s (%s) - %,.0f₫", 
                           loaiKhachHang.getIcon(), hoTen, loaiKhachHang.getTen(), tongChiTieu);
    }
}
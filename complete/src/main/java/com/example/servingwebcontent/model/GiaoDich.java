package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 🧾 GiaoDich Entity - Advanced Transaction Management
 */
@Entity
@Table(name = "giao_dich",
       indexes = {
           @Index(name = "idx_ma_giao_dich", columnList = "maGiaoDich"),
           @Index(name = "idx_loai_giao_dich", columnList = "loaiGiaoDich"),
           @Index(name = "idx_trang_thai", columnList = "trangThai"),
           @Index(name = "idx_created_at", columnList = "createdAt"),
           @Index(name = "idx_khach_hang_id", columnList = "khachHang_id"),
           @Index(name = "idx_nhan_vien_id", columnList = "nhanVien_id"),
           @Index(name = "idx_san_pham_id", columnList = "sanPham_id")
       })
public class GiaoDich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Mã giao dịch không được để trống")
    @Size(min = 8, max = 30, message = "Mã giao dịch phải từ 8-30 ký tự")
    @Pattern(regexp = "^GD\\d{6,12}$", message = "Mã giao dịch phải theo format: GD202412001")
    @Column(name = "ma_giao_dich", unique = true, nullable = false, length = 30)
    private String maGiaoDich;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_giao_dich", nullable = false)
    private LoaiGiaoDich loaiGiaoDich;

    public enum LoaiGiaoDich {
        BAN_HANG("Bán Hàng", "🛒", "#4CAF50", 1.0),
        TRA_HANG("Trả Hàng", "↩️", "#FF5722", -1.0),
        DOI_HANG("Đổi Hàng", "🔄", "#FF9800", 0.0),
        KHUYEN_MAI("Khuyến Mãi", "🎁", "#9C27B0", 1.0),
        HOA_DON_DIEN_TU("Hóa Đơn Điện Tử", "📧", "#2196F3", 1.0);

        private final String ten;
        private final String icon;
        private final String color;
        private final double heSoDoanhThu;

        LoaiGiaoDich(String ten, String icon, String color, double heSoDoanhThu) {
            this.ten = ten;
            this.icon = icon;
            this.color = color;
            this.heSoDoanhThu = heSoDoanhThu;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public String getColor() { return color; }
        public double getHeSoDoanhThu() { return heSoDoanhThu; }
    }

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải ít nhất 1")
    @Max(value = 9999, message = "Số lượng không được quá 9,999")
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "0.0", message = "Đơn giá không được âm")
    @Column(name = "don_gia", nullable = false)
    private Double donGia;

    @Column(name = "thanh_tien")
    private Double thanhTien;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_giam_gia")
    private LoaiGiamGia loaiGiamGia;

    public enum LoaiGiamGia {
        KHONG_GIAM_GIA("Không Giảm Giá", "💰", 0.0),
        VIP_DISCOUNT("Giảm Giá VIP", "👑", 10.0),
        PREMIUM_DISCOUNT("Giảm Giá Premium", "⭐", 5.0),
        SEASONAL_DISCOUNT("Giảm Giá Theo Mùa", "🌸", 15.0),
        BIRTHDAY_DISCOUNT("Giảm Giá Sinh Nhật", "🎂", 20.0),
        BULK_DISCOUNT("Giảm Giá Số Lượng", "📦", 8.0),
        FIRST_TIME_DISCOUNT("Giảm Giá Khách Mới", "🎯", 12.0);

        private final String ten;
        private final String icon;
        private final double phanTramGiamGia;

        LoaiGiamGia(String ten, String icon, double phanTramGiamGia) {
            this.ten = ten;
            this.icon = icon;
            this.phanTramGiamGia = phanTramGiamGia;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public double getPhanTramGiamGia() { return phanTramGiamGia; }
    }

    @DecimalMin(value = "0.0", message = "Phần trăm giảm giá không được âm")
    @DecimalMax(value = "100.0", message = "Phần trăm giảm giá không được quá 100%")
    @Column(name = "phan_tram_giam_gia")
    private Double phanTramGiamGia = 0.0;

    @DecimalMin(value = "0.0", message = "Số tiền giảm giá không được âm")
    @Column(name = "so_tien_giam_gia")
    private Double soTienGiamGia = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "phuong_thuc_thanh_toan", nullable = false)
    private PhuongThucThanhToan phuongThucThanhToan;

    public enum PhuongThucThanhToan {
        TIEN_MAT("Tiền Mặt", "💵", "#4CAF50"),
        THE_ATM("Thẻ ATM", "💳", "#2196F3"),
        THE_TIN_DUNG("Thẻ Tín Dụng", "💎", "#9C27B0"),
        VI_DIEN_TU("Ví Điện Tử", "📱", "#FF9800"),
        CHUYEN_KHOAN("Chuyển Khoản", "🏦", "#607D8B"),
        QR_CODE("QR Code", "📱", "#E91E63");

        private final String ten;
        private final String icon;
        private final String color;

        PhuongThucThanhToan(String ten, String icon, String color) {
            this.ten = ten;
            this.icon = icon;
            this.color = color;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public String getColor() { return color; }
    }

    @DecimalMin(value = "0.0", message = "Tổng tiền không được âm")
    @Column(name = "tong_tien")
    private Double tongTien;

    @DecimalMin(value = "0.0", message = "Tiền khách đưa không được âm")
    @Column(name = "tien_khach_dua")
    private Double tienKhachDua = 0.0;

    @Column(name = "tien_thua")
    private Double tienThua = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false)
    private TrangThaiGiaoDich trangThai;

    public enum TrangThaiGiaoDich {
        CHO_XU_LY("Chờ Xử Lý", "warning", "⏳"),
        DANG_XU_LY("Đang Xử Lý", "info", "⚡"),
        HOAN_THANH("Hoàn Thành", "success", "✅"),
        HUY_BO("Hủy Bỏ", "danger", "❌"),
        HOAN_TIEN("Hoàn Tiền", "secondary", "💸");

        private final String ten;
        private final String bootstrapClass;
        private final String icon;

        TrangThaiGiaoDich(String ten, String bootstrapClass, String icon) {
            this.ten = ten;
            this.bootstrapClass = bootstrapClass;
            this.icon = icon;
        }

        public String getTen() { return ten; }
        public String getBootstrapClass() { return bootstrapClass; }
        public String getIcon() { return icon; }
    }

    @Size(max = 500, message = "Ghi chú không được quá 500 ký tự")
    @Column(name = "ghi_chu", length = 500)
    private String ghiChu;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "thoi_gian_hoan_thanh")
    private LocalDateTime thoiGianHoanThanh;

    @Size(max = 100, message = "Mã khuyến mãi không được quá 100 ký tự")
    @Column(name = "ma_khuyen_mai", length = 100)
    private String maKhuyenMai;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // =================== RELATIONSHIPS ===================
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhan_vien_id")
    private NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id")
    private SanPham sanPham;

    // =================== CONSTRUCTORS ===================
    
    public GiaoDich() {
        this.trangThai = TrangThaiGiaoDich.CHO_XU_LY;
        this.loaiGiamGia = LoaiGiamGia.KHONG_GIAM_GIA;
        this.phanTramGiamGia = 0.0;
        this.soTienGiamGia = 0.0;
        this.tienKhachDua = 0.0;
        this.tienThua = 0.0;
    }

    public GiaoDich(String maGiaoDich, LoaiGiaoDich loaiGiaoDich, SanPham sanPham, 
                    KhachHang khachHang, NhanVien nhanVien, Integer soLuong) {
        this();
        this.maGiaoDich = maGiaoDich;
        this.loaiGiaoDich = loaiGiaoDich;
        this.sanPham = sanPham;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.soLuong = soLuong;
        this.donGia = sanPham != null ? sanPham.getGiaBan() : 0.0;
        tinhToanGiaoDich();
    }

    // =================== BUSINESS METHODS ===================
    
    /**
     * 💰 Tính toán tự động tất cả giá trị giao dịch
     */
    public void tinhToanGiaoDich() {
        if (soLuong == null || donGia == null) return;
        
        // Tính thành tiền
        this.thanhTien = soLuong * donGia;
        
        // Áp dụng giảm giá từ loại khách hàng nếu có
        apDungGiamGiaKhachHang();
        
        // Tính số tiền giảm giá
        if (phanTramGiamGia != null && phanTramGiamGia > 0) {
            this.soTienGiamGia = (thanhTien * phanTramGiamGia) / 100.0;
        } else {
            this.soTienGiamGia = 0.0;
        }
        
        // Tính tổng tiền cuối cùng
        this.tongTien = thanhTien - soTienGiamGia;
        
        // Tính tiền thừa nếu có
        if (tienKhachDua != null && tienKhachDua > tongTien) {
            this.tienThua = tienKhachDua - tongTien;
        } else {
            this.tienThua = 0.0;
        }
    }

    /**
     * 🎯 Áp dụng giảm giá dựa trên loại khách hàng
     */
    private void apDungGiamGiaKhachHang() {
        if (khachHang == null) {
            this.loaiGiamGia = LoaiGiamGia.KHONG_GIAM_GIA;
            this.phanTramGiamGia = 0.0;
            return;
        }
        
        switch (khachHang.getLoaiKhachHang()) {
            case VIP -> {
                this.loaiGiamGia = LoaiGiamGia.VIP_DISCOUNT;
                this.phanTramGiamGia = 10.0;
            }
            case PREMIUM -> {
                this.loaiGiamGia = LoaiGiamGia.PREMIUM_DISCOUNT;
                this.phanTramGiamGia = 5.0;
            }
            default -> {
                this.loaiGiamGia = LoaiGiamGia.KHONG_GIAM_GIA;
                this.phanTramGiamGia = 0.0;
            }
        }
    }

    /**
     * ✅ Hoàn thành giao dịch
     */
    public void hoanThanhGiaoDich() {
        if (trangThai == TrangThaiGiaoDich.HOAN_THANH) return;
        
        this.trangThai = TrangThaiGiaoDich.HOAN_THANH;
        this.thoiGianHoanThanh = LocalDateTime.now();
        
        // Cập nhật inventory cho sản phẩm
        if (sanPham != null && loaiGiaoDich == LoaiGiaoDich.BAN_HANG) {
            sanPham.banHang(soLuong);
        }
        
        // Cập nhật thông tin khách hàng
        if (khachHang != null) {
            khachHang.capNhatThongTinMuaHang(tongTien, soLuong);
        }
        
        // Cập nhật doanh số nhân viên
        if (nhanVien != null) {
            nhanVien.xuLyBanHang(tongTien);
        }
    }

    /**
     * ❌ Hủy bỏ giao dịch
     */
    public void huyBoGiaoDich(String lyDo) {
        this.trangThai = TrangThaiGiaoDich.HUY_BO;
        this.ghiChu = (ghiChu != null ? ghiChu + ". " : "") + "Hủy bỏ: " + lyDo;
    }

    /**
     * 📊 Tính lợi nhuận từ giao dịch
     */
    public Double tinhLoiNhuan() {
        if (sanPham == null || tongTien == null || trangThai != TrangThaiGiaoDich.HOAN_THANH) {
            return 0.0;
        }
        
        Double giaVon = sanPham.getGiaVon();
        if (giaVon == null || soLuong == null) return 0.0;
        
        double tongGiaVon = giaVon * soLuong;
        return tongTien - tongGiaVon;
    }

    /**
     * 📈 Tính tỷ lệ lợi nhuận (%)
     */
    public Double tinhTyLeLoiNhuan() {
        if (sanPham == null || tongTien == null || tongTien == 0) return 0.0;
        
        Double loiNhuan = tinhLoiNhuan();
        return (loiNhuan / tongTien) * 100;
    }

    /**
     * 🎁 Áp dụng mã khuyến mãi
     */
    public boolean apDungMaKhuyenMai(String ma, Double phanTramGiam) {
        if (ma == null || phanTramGiam == null || phanTramGiam < 0 || phanTramGiam > 100) {
            return false;
        }
        
        this.maKhuyenMai = ma;
        this.loaiGiamGia = LoaiGiamGia.SEASONAL_DISCOUNT;
        this.phanTramGiamGia = phanTramGiam;
        tinhToanGiaoDich();
        return true;
    }

    /**
     * 💳 Xử lý thanh toán
     */
    public boolean xuLyThanhToan(Double soTienThanhToan, PhuongThucThanhToan phuongThuc) {
        if (soTienThanhToan == null || soTienThanhToan < tongTien) return false;
        
        this.tienKhachDua = soTienThanhToan;
        this.phuongThucThanhToan = phuongThuc;
        this.trangThai = TrangThaiGiaoDich.DANG_XU_LY;
        tinhToanGiaoDich();
        return true;
    }

    // =================== GETTERS & SETTERS ===================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaGiaoDich() { return maGiaoDich; }
    public void setMaGiaoDich(String maGiaoDich) { this.maGiaoDich = maGiaoDich; }

    public LoaiGiaoDich getLoaiGiaoDich() { return loaiGiaoDich; }
    public void setLoaiGiaoDich(LoaiGiaoDich loaiGiaoDich) { this.loaiGiaoDich = loaiGiaoDich; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { 
        this.soLuong = soLuong; 
        tinhToanGiaoDich();
    }

    public Double getDonGia() { return donGia; }
    public void setDonGia(Double donGia) { 
        this.donGia = donGia; 
        tinhToanGiaoDich();
    }

    public Double getThanhTien() { return thanhTien; }
    public void setThanhTien(Double thanhTien) { this.thanhTien = thanhTien; }

    public LoaiGiamGia getLoaiGiamGia() { return loaiGiamGia; }
    public void setLoaiGiamGia(LoaiGiamGia loaiGiamGia) { 
        this.loaiGiamGia = loaiGiamGia;
        if (loaiGiamGia != null) {
            this.phanTramGiamGia = loaiGiamGia.getPhanTramGiamGia();
        }
        tinhToanGiaoDich();
    }

    public Double getPhanTramGiamGia() { return phanTramGiamGia; }
    public void setPhanTramGiamGia(Double phanTramGiamGia) { 
        this.phanTramGiamGia = phanTramGiamGia; 
        tinhToanGiaoDich();
    }

    public Double getSoTienGiamGia() { return soTienGiamGia; }
    public void setSoTienGiamGia(Double soTienGiamGia) { this.soTienGiamGia = soTienGiamGia; }

    public PhuongThucThanhToan getPhuongThucThanhToan() { return phuongThucThanhToan; }
    public void setPhuongThucThanhToan(PhuongThucThanhToan phuongThucThanhToan) { 
        this.phuongThucThanhToan = phuongThucThanhToan; 
    }

    public Double getTongTien() { return tongTien; }
    public void setTongTien(Double tongTien) { this.tongTien = tongTien; }

    public Double getTienKhachDua() { return tienKhachDua; }
    public void setTienKhachDua(Double tienKhachDua) { 
        this.tienKhachDua = tienKhachDua; 
        tinhToanGiaoDich();
    }

    public Double getTienThua() { return tienThua; }
    public void setTienThua(Double tienThua) { this.tienThua = tienThua; }

    public TrangThaiGiaoDich getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiGiaoDich trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public LocalDateTime getThoiGianHoanThanh() { return thoiGianHoanThanh; }
    public void setThoiGianHoanThanh(LocalDateTime thoiGianHoanThanh) { 
        this.thoiGianHoanThanh = thoiGianHoanThanh; 
    }

    public String getMaKhuyenMai() { return maKhuyenMai; }
    public void setMaKhuyenMai(String maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
        apDungGiamGiaKhachHang();
        tinhToanGiaoDich();
    }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) { 
        this.sanPham = sanPham; 
        if (sanPham != null) {
            this.donGia = sanPham.getGiaBan();
        }
        tinhToanGiaoDich();
    }

    // =================== EQUALS & HASHCODE ===================
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GiaoDich giaoDich = (GiaoDich) obj;
        return Objects.equals(maGiaoDich, giaoDich.maGiaoDich);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maGiaoDich);
    }

    @Override
    public String toString() {
        return String.format("GiaoDich{id=%d, ma='%s', loai=%s, sanPham='%s', soLuong=%d, tongTien=%.0f, trangThai=%s}", 
                           id, maGiaoDich, loaiGiaoDich, 
                           sanPham != null ? sanPham.getTenSanPham() : "N/A", 
                           soLuong, tongTien, trangThai);
    }

    public String getSummary() {
        return String.format("%s %s - %s x%d = %,.0f₫ (%s)", 
                           loaiGiaoDich.getIcon(), 
                           sanPham != null ? sanPham.getTenSanPham() : "N/A",
                           khachHang != null ? khachHang.getHoTen() : "Khách lẻ",
                           soLuong, tongTien, trangThai.getTen());
    }
}
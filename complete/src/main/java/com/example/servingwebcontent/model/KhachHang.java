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
import java.util.Map;
import java.util.HashMap;

/**
 * 🎂 SanPham Entity - Enhanced Product Management với Custom Images
 */
@Entity
@Table(name = "san_pham", 
       indexes = {
           @Index(name = "idx_ma_san_pham", columnList = "maSanPham"),
           @Index(name = "idx_danh_muc", columnList = "danhMuc"),
           @Index(name = "idx_trang_thai", columnList = "trangThai"),
           @Index(name = "idx_created_at", columnList = "createdAt")
       })
public class SanPham {

    // =================== PRIMARY KEY ===================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // =================== BASIC INFORMATION ===================
    
    @NotBlank(message = "Mã sản phẩm không được để trống")
    @Size(min = 3, max = 20, message = "Mã sản phẩm phải từ 3-20 ký tự")
    @Pattern(regexp = "^[A-Z]{2}\\d{3,6}$", message = "Mã sản phẩm phải theo format: ABC123")
    @Column(name = "ma_san_pham", unique = true, nullable = false, length = 20)
    private String maSanPham;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 100, message = "Tên sản phẩm phải từ 2-100 ký tự")
    @Column(name = "ten_san_pham", nullable = false, length = 100)
    private String tenSanPham;

    @Size(max = 500, message = "Mô tả không được quá 500 ký tự")
    @Column(name = "mo_ta", length = 500)
    private String moTa;

    // =================== CATEGORY ENUM ===================
    @Enumerated(EnumType.STRING)
    @Column(name = "danh_muc", nullable = false)
    private DanhMucSanPham danhMuc;

    public enum DanhMucSanPham {
        BANH_KEM("Bánh Kem", "🎂", "#FFB6C1"),
        BANH_MI("Bánh Mì", "🥖", "#DEB887"), 
        BANH_NGOT("Bánh Ngọt", "🧁", "#F0E68C"),
        DO_UONG("Đồ Uống", "☕", "#87CEEB"),
        BANH_QUY("Bánh Quy", "🍪", "#D2B48C");

        private final String ten;
        private final String icon;
        private final String color;

        DanhMucSanPham(String ten, String icon, String color) {
            this.ten = ten;
            this.icon = icon;
            this.color = color;
        }

        public String getTen() { return ten; }
        public String getIcon() { return icon; }
        public String getColor() { return color; }
    }

    // =================== PRICING & FINANCIAL ===================
    
    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "1000.0", message = "Giá bán phải ít nhất 1,000 VND")
    @DecimalMax(value = "10000000.0", message = "Giá bán không được quá 10,000,000 VND")
    @Column(name = "gia_ban", nullable = false, precision = 12, scale = 2)
    private Double giaBan;

    @NotNull(message = "Giá vốn không được để trống")
    @DecimalMin(value = "500.0", message = "Giá vốn phải ít nhất 500 VND")
    @Column(name = "gia_von", nullable = false, precision = 12, scale = 2)
    private Double giaVon;

    // =================== INVENTORY MANAGEMENT ===================
    
    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng không được âm")
    @Max(value = 99999, message = "Số lượng không được quá 99,999")
    @Column(name = "so_luong_ton_kho", nullable = false)
    private Integer soLuongTonKho;

    @Min(value = 0, message = "Số lượng tối thiểu không được âm")
    @Column(name = "so_luong_toi_thieu")
    private Integer soLuongToiThieu = 5;

    @Min(value = 0, message = "Số lượng đã bán không được âm")
    @Column(name = "so_luong_da_ban")
    private Integer soLuongDaBan = 0;

    // =================== PRODUCT LIFECYCLE ===================
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "han_su_dung")
    private LocalDate hanSuDung;

    @Size(max = 100, message = "Nhà sản xuất không được quá 100 ký tự")
    @Column(name = "nha_san_xuat", length = 100)
    private String nhaSanXuat;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false)
    private TrangThaiSanPham trangThai;

    public enum TrangThaiSanPham {
        CON_HANG("Còn Hàng", "success", "✅"),
        SAP_HET_HANG("Sắp Hết Hàng", "warning", "⚠️"),
        HET_HANG("Hết Hàng", "danger", "❌"),
        HET_HAN("Hết Hạn", "secondary", "⏰"),
        NGUNG_KINH_DOANH("Ngừng Kinh Doanh", "dark", "🚫");

        private final String ten;
        private final String bootstrapClass;
        private final String icon;

        TrangThaiSanPham(String ten, String bootstrapClass, String icon) {
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
    
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GiaoDich> giaoDichList = new ArrayList<>();

    // =================== CONSTRUCTORS ===================
    
    public SanPham() {
        this.trangThai = TrangThaiSanPham.CON_HANG;
        this.soLuongToiThieu = 5;
        this.soLuongDaBan = 0;
    }

    public SanPham(String maSanPham, String tenSanPham, DanhMucSanPham danhMuc, 
                   Double giaBan, Double giaVon, Integer soLuongTonKho) {
        this();
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.danhMuc = danhMuc;
        this.giaBan = giaBan;
        this.giaVon = giaVon;
        this.soLuongTonKho = soLuongTonKho;
        updateTrangThai();
    }

    // =================== BUSINESS METHODS ===================
    
    /**
     * 💰 Tính lợi nhuận trên 1 sản phẩm
     */
    public Double tinhLoiNhuan() {
        if (giaBan == null || giaVon == null) return 0.0;
        return giaBan - giaVon;
    }

    /**
     * 📊 Tính tỷ lệ lợi nhuận (%)
     */
    public Double tinhTyLeLoiNhuan() {
        if (giaVon == null || giaVon == 0) return 0.0;
        return (tinhLoiNhuan() / giaVon) * 100;
    }

    /**
     * 💎 Tính tổng giá trị tồn kho
     */
    public Double tinhGiaTriTonKho() {
        if (soLuongTonKho == null || giaVon == null) return 0.0;
        return soLuongTonKho * giaVon;
    }

    /**
     * 💰 Tính tổng doanh thu từ sản phẩm này
     */
    public Double tinhTongDoanhThu() {
        if (soLuongDaBan == null || giaBan == null) return 0.0;
        return soLuongDaBan * giaBan;
    }

    /**
     * 📈 Tính tổng lợi nhuận đã thực hiện
     */
    public Double tinhTongLoiNhuanThucHien() {
        if (soLuongDaBan == null) return 0.0;
        return soLuongDaBan * tinhLoiNhuan();
    }

    /**
     * ⚠️ Kiểm tra sản phẩm có sắp hết hàng không
     */
    public boolean isSapHetHang() {
        return soLuongTonKho != null && soLuongToiThieu != null && 
               soLuongTonKho <= soLuongToiThieu && soLuongTonKho > 0;
    }

    /**
     * ❌ Kiểm tra sản phẩm có hết hàng không
     */
    public boolean isHetHang() {
        return soLuongTonKho == null || soLuongTonKho <= 0;
    }

    /**
     * ⏰ Kiểm tra sản phẩm có hết hạn không
     */
    public boolean isHetHan() {
        return hanSuDung != null && hanSuDung.isBefore(LocalDate.now());
    }

    /**
     * 📦 Cập nhật trạng thái tự động dựa trên business rules
     */
    public void updateTrangThai() {
        if (isHetHan()) {
            this.trangThai = TrangThaiSanPham.HET_HAN;
        } else if (isHetHang()) {
            this.trangThai = TrangThaiSanPham.HET_HANG;
        } else if (isSapHetHang()) {
            this.trangThai = TrangThaiSanPham.SAP_HET_HANG;
        } else {
            this.trangThai = TrangThaiSanPham.CON_HANG;
        }
    }

    /**
     * 🛒 Xử lý bán hàng (giảm tồn kho, tăng đã bán)
     */
    public boolean banHang(Integer soLuong) {
        if (soLuong == null || soLuong <= 0) return false;
        if (isHetHang() || soLuongTonKho < soLuong) return false;
        
        this.soLuongTonKho -= soLuong;
        this.soLuongDaBan += soLuong;
        updateTrangThai();
        return true;
    }

    /**
     * 📦 Nhập hàng (tăng tồn kho)
     */
    public void nhapHang(Integer soLuong) {
        if (soLuong != null && soLuong > 0) {
            this.soLuongTonKho += soLuong;
            updateTrangThai();
        }
    }

    /**
     * 🎨 Lấy hình ảnh theo mã sản phẩm với Custom URLs
     */
    public String getImageUrl() {
        // Custom image mapping cho từng sản phẩm cụ thể
        Map<String, String> customImages = new HashMap<>();
        
        // Bánh Kem
        customImages.put("BK001", "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&h=300&fit=crop"); // Tiramisu
        customImages.put("BK002", "https://images.unsplash.com/photo-1586040140378-b1f84ca17d24?w=400&h=300&fit=crop"); // Red Velvet
        customImages.put("BK003", "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop"); // Chocolate
        customImages.put("BK004", "https://images.unsplash.com/photo-1567958147117-4d93e0eca942?w=400&h=300&fit=crop"); // Cheesecake
        customImages.put("BK005", "https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?w=400&h=300&fit=crop"); // Matcha
        customImages.put("BK006", "https://images.unsplash.com/photo-1464349095431-e9a21285b5f3?w=400&h=300&fit=crop"); // Strawberry
        
        // Bánh Ngọt
        customImages.put("BN001", "https://images.unsplash.com/photo-1549931319-a545dcf3bc73?w=400&h=300&fit=crop"); // Croissant
        customImages.put("BN002", "https://images.unsplash.com/photo-1569864358642-9d1684040f43?w=400&h=300&fit=crop"); // Macaron
        customImages.put("BN003", "https://images.unsplash.com/photo-1551024601-bec78aea704b?w=400&h=300&fit=crop"); // Éclair
        customImages.put("BN004", "https://images.unsplash.com/photo-1550617931-e17a7b70daa2?w=400&h=300&fit=crop"); // Muffin
        customImages.put("BN005", "https://images.unsplash.com/photo-1519869325930-281384150729?w=400&h=300&fit=crop"); // Cupcake
        
        // Bánh Mì
        customImages.put("BM001", "https://images.unsplash.com/photo-1506084868230-bb9d95c24759?w=400&h=300&fit=crop"); // Croissant Jambon
        customImages.put("BM002", "https://images.unsplash.com/photo-1545462461-4d1ef874a5e1?w=400&h=300&fit=crop"); // Bagel
        customImages.put("BM003", "https://images.unsplash.com/photo-1553909489-cd47e0ef937f?w=400&h=300&fit=crop"); // Sandwich
        customImages.put("BM004", "https://images.unsplash.com/photo-1508736793122-f516e3ba5569?w=400&h=300&fit=crop"); // Bánh Mì VN
        
        // Đồ Uống
        customImages.put("DU001", "https://images.unsplash.com/photo-1545665277-5937750b5a96?w=400&h=300&fit=crop"); // Espresso
        customImages.put("DU002", "https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=400&h=300&fit=crop"); // Matcha
        customImages.put("DU003", "https://images.unsplash.com/photo-1517701550927-30cf4ba1f938?w=400&h=300&fit=crop"); // Hot Chocolate
        
        // Bánh Quy
        customImages.put("BQ001", "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400&h=300&fit=crop"); // Chocolate Chip
        customImages.put("BQ002", "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400&h=300&fit=crop"); // Danish Butter
        
        // Trả về custom image nếu có, nếu không thì dùng default theo category
        if (maSanPham != null && customImages.containsKey(maSanPham)) {
            return customImages.get(maSanPham);
        }
        
        // Fallback to category-based images
        return switch (danhMuc) {
            case BANH_KEM -> "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop";
            case BANH_MI -> "https://images.unsplash.com/photo-1549931319-a545dcf3bc73?w=400&h=300&fit=crop";
            case BANH_NGOT -> "https://images.unsplash.com/photo-1550617931-e17a7b70daa2?w=400&h=300&fit=crop";
            case DO_UONG -> "https://images.unsplash.com/photo-1545665277-5937750b5a96?w=400&h=300&fit=crop";
            case BANH_QUY -> "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400&h=300&fit=crop";
        };
    }

    // =================== GETTERS & SETTERS ===================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public DanhMucSanPham getDanhMuc() { return danhMuc; }
    public void setDanhMuc(DanhMucSanPham danhMuc) { 
        this.danhMuc = danhMuc; 
        updateTrangThai();
    }

    public Double getGiaBan() { return giaBan; }
    public void setGiaBan(Double giaBan) { 
        this.giaBan = giaBan; 
        updateTrangThai();
    }

    public Double getGiaVon() { return giaVon; }
    public void setGiaVon(Double giaVon) { 
        this.giaVon = giaVon; 
        updateTrangThai();
    }

    public Integer getSoLuongTonKho() { return soLuongTonKho; }
    public void setSoLuongTonKho(Integer soLuongTonKho) { 
        this.soLuongTonKho = soLuongTonKho; 
        updateTrangThai();
    }

    public Integer getSoLuongToiThieu() { return soLuongToiThieu; }
    public void setSoLuongToiThieu(Integer soLuongToiThieu) { 
        this.soLuongToiThieu = soLuongToiThieu; 
        updateTrangThai();
    }

    public Integer getSoLuongDaBan() { return soLuongDaBan; }
    public void setSoLuongDaBan(Integer soLuongDaBan) { this.soLuongDaBan = soLuongDaBan; }

    public LocalDate getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(LocalDate hanSuDung) { 
        this.hanSuDung = hanSuDung; 
        updateTrangThai();
    }

    public String getNhaSanXuat() { return nhaSanXuat; }
    public void setNhaSanXuat(String nhaSanXuat) { this.nhaSanXuat = nhaSanXuat; }

    public TrangThaiSanPham getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiSanPham trangThai) { this.trangThai = trangThai; }

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
        SanPham sanPham = (SanPham) obj;
        return Objects.equals(maSanPham, sanPham.maSanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSanPham);
    }

    // =================== TO STRING ===================
    
    @Override
    public String toString() {
        return String.format("SanPham{id=%d, ma='%s', ten='%s', danhMuc=%s, gia=%.0f, tonKho=%d, trangThai=%s}", 
                           id, maSanPham, tenSanPham, danhMuc, giaBan, soLuongTonKho, trangThai);
    }

    /**
     * 📊 Lấy thông tin tóm tắt cho dashboard
     */
    public String getSummary() {
        return String.format("%s %s - %,.0f₫ (Còn: %d)", 
                           danhMuc.getIcon(), tenSanPham, giaBan, soLuongTonKho);
    }
}
images.unsplash.com
An
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
    public void xuLyMuaHang(Double giaTriDonHang) {
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


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
    @Column(name = "gia_ban", nullable = false)
    private Double giaBan;

    @NotNull(message = "Giá vốn không được để trống")
    @DecimalMin(value = "500.0", message = "Giá vốn phải ít nhất 500 VND")
    @Column(name = "gia_von", nullable = false)
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
    
    public Double tinhLoiNhuan() {
        if (giaBan == null || giaVon == null) return 0.0;
        return giaBan - giaVon;
    }

    public Double tinhTyLeLoiNhuan() {
        if (giaVon == null || giaVon == 0) return 0.0;
        return (tinhLoiNhuan() / giaVon) * 100;
    }

    public Double tinhGiaTriTonKho() {
        if (soLuongTonKho == null || giaVon == null) return 0.0;
        return soLuongTonKho * giaVon;
    }

    public Double tinhTongDoanhThu() {
        if (soLuongDaBan == null || giaBan == null) return 0.0;
        return soLuongDaBan * giaBan;
    }

    public Double tinhTongLoiNhuanThucHien() {
        if (soLuongDaBan == null) return 0.0;
        return soLuongDaBan * tinhLoiNhuan();
    }

    public boolean isSapHetHang() {
        return soLuongTonKho != null && soLuongToiThieu != null && 
               soLuongTonKho <= soLuongToiThieu && soLuongTonKho > 0;
    }

    public boolean isHetHang() {
        return soLuongTonKho == null || soLuongTonKho <= 0;
    }

    public boolean isHetHan() {
        return hanSuDung != null && hanSuDung.isBefore(LocalDate.now());
    }

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

    public boolean banHang(Integer soLuong) {
        if (soLuong == null || soLuong <= 0) return false;
        if (isHetHang() || soLuongTonKho < soLuong) return false;
        this.soLuongTonKho -= soLuong;
        this.soLuongDaBan += soLuong;
        updateTrangThai();
        return true;
    }

    public void nhapHang(Integer soLuong) {
        if (soLuong != null && soLuong > 0) {
            this.soLuongTonKho += soLuong;
            updateTrangThai();
        }
    }

    public String getImageUrl() {
        Map<String, String> customImages = new HashMap<>();
        customImages.put("BK001", "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&h=300&fit=crop");
        customImages.put("BK002", "https://images.unsplash.com/photo-1586040140378-b1f84ca17d24?w=400&h=300&fit=crop");
        customImages.put("BK003", "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop");
        customImages.put("BK004", "https://images.unsplash.com/photo-1567958147117-4d93e0eca942?w=400&h=300&fit=crop");
        customImages.put("BK005", "https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?w=400&h=300&fit=crop");
        customImages.put("BK006", "https://images.unsplash.com/photo-1464349095431-e9a21285b5f3?w=400&h=300&fit=crop");
        customImages.put("BN001", "https://images.unsplash.com/photo-1549931319-a545dcf3bc73?w=400&h=300&fit=crop");
        customImages.put("BN002", "https://images.unsplash.com/photo-1569864358642-9d1684040f43?w=400&h=300&fit=crop");
        customImages.put("BN003", "https://images.unsplash.com/photo-1551024601-bec78aea704b?w=400&h=300&fit=crop");
        customImages.put("BN004", "https://images.unsplash.com/photo-1550617931-e17a7b70daa2?w=400&h=300&fit=crop");
        customImages.put("BN005", "https://images.unsplash.com/photo-1519869325930-281384150729?w=400&h=300&fit=crop");
        customImages.put("BM001", "https://images.unsplash.com/photo-1506084868230-bb9d95c24759?w=400&h=300&fit=crop");
        customImages.put("BM002", "https://images.unsplash.com/photo-1545462461-4d1ef874a5e1?w=400&h=300&fit=crop");
        customImages.put("BM003", "https://images.unsplash.com/photo-1553909489-cd47e0ef937f?w=400&h=300&fit=crop");
        customImages.put("BM004", "https://images.unsplash.com/photo-1508736793122-f516e3ba5569?w=400&h=300&fit=crop");
        customImages.put("DU001", "https://images.unsplash.com/photo-1545665277-5937750b5a96?w=400&h=300&fit=crop");
        customImages.put("DU002", "https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=400&h=300&fit=crop");
        customImages.put("DU003", "https://images.unsplash.com/photo-1517701550927-30cf4ba1f938?w=400&h=300&fit=crop");
        customImages.put("BQ001", "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400&h=300&fit=crop");
        customImages.put("BQ002", "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400&h=300&fit=crop");

        if (maSanPham != null && customImages.containsKey(maSanPham)) {
            return customImages.get(maSanPham);
        }
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

    @Override
    public String toString() {
        return String.format("SanPham{id=%d, ma='%s', ten='%s', danhMuc=%s, gia=%.0f, tonKho=%d, trangThai=%s}", 
                           id, maSanPham, tenSanPham, danhMuc, giaBan, soLuongTonKho, trangThai);
    }

    public String getSummary() {
        return String.format("%s %s - %,.0f₫ (Còn: %d)", 
                           danhMuc.getIcon(), tenSanPham, giaBan, soLuongTonKho);
    }
}

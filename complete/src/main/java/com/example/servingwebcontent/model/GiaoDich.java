package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "giao_dich")
public class GiaoDich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã giao dịch không được để trống")
    @Column(name = "ma_giao_dich", length = 50, nullable = false, unique = true)
    private String maGiaoDich;

    @NotNull(message = "Ngày giờ giao dịch bắt buộc")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // để Spring bind input datetime-local
    @Column(name = "ngay_gio_giao_dich", nullable = false)
    private LocalDateTime ngayGioGiaoDich;

    @PositiveOrZero(message = "Tổng tiền phải >= 0")
    @Column(name = "tong_tien", nullable = false)
    private double tongTien;

    // Quan hệ nhiều - một tới NhanVien
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nhanvien_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_gd_nhanvien"))
    private NhanVien nhanVien;

    // Quan hệ nhiều - một tới KhachHang
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "khachhang_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_gd_khachhang"))
    private KhachHang khachHang;

    // Quan hệ nhiều - một tới SanPham
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sanpham_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_gd_sanpham"))
    private SanPham sanPham;

    public GiaoDich() {}

    public GiaoDich(String maGiaoDich,
                    LocalDateTime ngayGioGiaoDich,
                    double tongTien,
                    NhanVien nhanVien,
                    KhachHang khachHang,
                    SanPham sanPham) {
        this.maGiaoDich = maGiaoDich;
        this.ngayGioGiaoDich = ngayGioGiaoDich;
        this.tongTien = tongTien;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.sanPham = sanPham;
    }

    /* --------- Getter & Setter --------- */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaGiaoDich() { return maGiaoDich; }
    public void setMaGiaoDich(String maGiaoDich) { this.maGiaoDich = maGiaoDich; }

    public LocalDateTime getNgayGioGiaoDich() { return ngayGioGiaoDich; }
    public void setNgayGioGiaoDich(LocalDateTime ngayGioGiaoDich) { this.ngayGioGiaoDich = ngayGioGiaoDich; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) { this.sanPham = sanPham; }

    @PrePersist
    public void prePersist() {
        // Nếu chưa set thời gian từ form thì mặc định là "now"
        if (this.ngayGioGiaoDich == null) {
            this.ngayGioGiaoDich = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "GiaoDich{" +
                "id=" + id +
                ", maGiaoDich='" + maGiaoDich + '\'' +
                ", ngayGioGiaoDich=" + ngayGioGiaoDich +
                ", tongTien=" + tongTien +
                '}';
    }
}

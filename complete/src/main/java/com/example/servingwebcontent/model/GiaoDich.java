package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GiaoDich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maGiaoDich;
    private LocalDateTime ngayGioGiaoDich;
    private double tongTien;

    // Quan hệ nhiều - một tới NhanVien
    @ManyToOne
    @JoinColumn(name = "nhanvien_id")
    private NhanVien nhanVien;

    // Quan hệ nhiều - một tới KhachHang
    @ManyToOne
    @JoinColumn(name = "khachhang_id")
    private KhachHang khachHang;

    // Quan hệ nhiều - một tới SanPham
    @ManyToOne
    @JoinColumn(name = "sanpham_id")
    private SanPham sanPham;

    public GiaoDich() {}

    public GiaoDich(String maGiaoDich, LocalDateTime ngayGioGiaoDich, double tongTien,
                    NhanVien nhanVien, KhachHang khachHang, SanPham sanPham) {
        this.maGiaoDich = maGiaoDich;
        this.ngayGioGiaoDich = ngayGioGiaoDich;
        this.tongTien = tongTien;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.sanPham = sanPham;
    }

    // Getter & Setter

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
}

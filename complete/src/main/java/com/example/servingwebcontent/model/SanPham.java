package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    @Column(nullable = false, unique = true, length = 50)
    private String maSanPham;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Column(nullable = false, length = 150)
    private String tenSanPham;

    @PositiveOrZero(message = "Số lượng phải >= 0")
    @Column(nullable = false)
    private int soLuong;

    @NotBlank(message = "Nhà sản xuất không được để trống")
    @Column(nullable = false, length = 150)
    private String nhaSanXuat;

    @NotNull(message = "Hạn sử dụng bắt buộc")
    private LocalDate hanSuDung;

    @NotBlank(message = "Nguyên liệu không được để trống")
    @Lob
    private String nguyenLieu;

    public SanPham() {}

    public SanPham(String maSanPham, String tenSanPham, int soLuong,
                   String nhaSanXuat, LocalDate hanSuDung, String nguyenLieu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.nhaSanXuat = nhaSanXuat;
        this.hanSuDung = hanSuDung;
        this.nguyenLieu = nguyenLieu;
    }

    public Long getId() { return id; }
    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public String getNhaSanXuat() { return nhaSanXuat; }
    public void setNhaSanXuat(String nhaSanXuat) { this.nhaSanXuat = nhaSanXuat; }
    public LocalDate getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(LocalDate hanSuDung) { this.hanSuDung = hanSuDung; }
    public String getNguyenLieu() { return nguyenLieu; }
    public void setNguyenLieu(String nguyenLieu) { this.nguyenLieu = nguyenLieu; }
}

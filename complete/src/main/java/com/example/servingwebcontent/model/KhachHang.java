package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên khách hàng không được để trống")
    @Column(nullable = false, length = 150)
    private String tenKhachHang;

    @NotBlank(message = "Mã khách hàng không được để trống")
    @Column(nullable = false, unique = true, length = 50)
    private String maKhachHang;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(nullable = false, length = 255)
    private String diaChi;

    @Pattern(regexp = "^(0|\\+84)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    @Column(length = 15)
    private String soDienThoai;

    public KhachHang() {}

    public KhachHang(String tenKhachHang, String maKhachHang, String diaChi, String soDienThoai) {
        this.tenKhachHang = tenKhachHang;
        this.maKhachHang = maKhachHang;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public Long getId() { return id; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
}

package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "nhan_vien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã nhân viên không được để trống")
    @Column(nullable = false, unique = true, length = 50)
    private String maNhanVien;

    @NotBlank(message = "Tên nhân viên không được để trống")
    @Column(nullable = false, length = 150)
    private String tenNhanVien;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(nullable = false, length = 255)
    private String diaChi;

    @Pattern(regexp = "^(0|\\+84)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    @Column(length = 15)
    private String soDienThoai;

    @PositiveOrZero(message = "Tiền lương phải >= 0")
    @Column(nullable = false)
    private double tienLuong;

    public NhanVien() {}

    public NhanVien(String maNhanVien, String tenNhanVien, String diaChi,
                    String soDienThoai, double tienLuong) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.tienLuong = tienLuong;
    }

    public Long getId() { return id; }
    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }
    public String getTenNhanVien() { return tenNhanVien; }
    public void setTenNhanVien(String tenNhanVien) { this.tenNhanVien = tenNhanVien; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public double getTienLuong() { return tienLuong; }
    public void setTienLuong(double tienLuong) { this.tienLuong = tienLuong; }
}

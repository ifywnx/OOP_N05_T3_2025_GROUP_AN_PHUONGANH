package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maNhanVien;
    private String tenNhanVien;
    private String diaChi;
    private String soDienThoai;
    private double tienLuong;

    // Quan hệ 1-nhiều với GiaoDich (nếu cần)
    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    private Set<GiaoDich> danhSachGiaoDich;

    public NhanVien() {}

    public NhanVien(String maNhanVien, String tenNhanVien, String diaChi, String soDienThoai, double tienLuong) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.tienLuong = tienLuong;
    }

    // Getter & Setter

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Set<GiaoDich> getDanhSachGiaoDich() { return danhSachGiaoDich; }
    public void setDanhSachGiaoDich(Set<GiaoDich> danhSachGiaoDich) { this.danhSachGiaoDich = danhSachGiaoDich; }
}

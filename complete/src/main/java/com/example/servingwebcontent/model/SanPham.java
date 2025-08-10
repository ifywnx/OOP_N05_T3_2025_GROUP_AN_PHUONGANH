package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maSanPham;
    private String tenSanPham;
    private int soLuong;
    private String nhaSanXuat;
    private String hanSuDung;
    private String nguyenLieu;

    // Quan hệ 1-nhiều với GiaoDich
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    private Set<GiaoDich> danhSachGiaoDich;

    public SanPham() {}

    public SanPham(String maSanPham, String tenSanPham, int soLuong, String nhaSanXuat, String hanSuDung, String nguyenLieu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.nhaSanXuat = nhaSanXuat;
        this.hanSuDung = hanSuDung;
        this.nguyenLieu = nguyenLieu;
    }

    // Getter & Setter

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getNhaSanXuat() { return nhaSanXuat; }
    public void setNhaSanXuat(String nhaSanXuat) { this.nhaSanXuat = nhaSanXuat; }

    public String getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(String hanSuDung) { this.hanSuDung = hanSuDung; }

    public String getNguyenLieu() { return nguyenLieu; }
    public void setNguyenLieu(String nguyenLieu) { this.nguyenLieu = nguyenLieu; }

    public Set<GiaoDich> getDanhSachGiaoDich() { return danhSachGiaoDich; }
    public void setDanhSachGiaoDich(Set<GiaoDich> danhSachGiaoDich) { this.danhSachGiaoDich = danhSachGiaoDich; }
}

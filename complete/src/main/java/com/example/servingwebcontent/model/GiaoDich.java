package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "giao_dich")
public class GiaoDich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã giao dịch không được để trống")
    @Column(nullable = false, unique = true, length = 50)
    private String maGiaoDich;

    @NotNull(message = "Ngày giao dịch bắt buộc")
    private LocalDate ngayGiaoDich;     // thay cho NgaythangNamGiaoDich

    @NotNull(message = "Giờ giao dịch bắt buộc")
    private LocalTime gioGiaoDich;      // thay cho GioGiaoDich

    @PositiveOrZero(message = "Tổng tiền phải >= 0")
    @Column(nullable = false)
    private double tongTien;

    public GiaoDich() {}

    public GiaoDich(String maGiaoDich, LocalDate ngayGiaoDich, LocalTime gioGiaoDich, double tongTien) {
        this.maGiaoDich = maGiaoDich;
        this.ngayGiaoDich = ngayGiaoDich;
        this.gioGiaoDich = gioGiaoDich;
        this.tongTien = tongTien;
    }

    public Long getId() { return id; }
    public String getMaGiaoDich() { return maGiaoDich; }
    public void setMaGiaoDich(String maGiaoDich) { this.maGiaoDich = maGiaoDich; }
    public LocalDate getNgayGiaoDich() { return ngayGiaoDich; }
    public void setNgayGiaoDich(LocalDate ngayGiaoDich) { this.ngayGiaoDich = ngayGiaoDich; }
    public LocalTime getGioGiaoDich() { return gioGiaoDich; }
    public void setGioGiaoDich(LocalTime gioGiaoDich) { this.gioGiaoDich = gioGiaoDich; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}

package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
    
    Optional<NhanVien> findByMaNhanVien(String maNhanVien);
    Optional<NhanVien> findBySoDienThoai(String soDienThoai);
    Optional<NhanVien> findByEmail(String email);
    List<NhanVien> findByHoTenContainingIgnoreCase(String hoTen);
    List<NhanVien> findByPhongBan(NhanVien.PhongBan phongBan);
    List<NhanVien> findByChucVu(NhanVien.ChucVu chucVu);
    List<NhanVien> findByTrangThai(NhanVien.TrangThaiNhanVien trangThai);
    List<NhanVien> findByDanhGiaHieuSuat(NhanVien.DanhGiaHieuSuat danhGiaHieuSuat);
    
    @Query("SELECT n FROM NhanVien n WHERE n.trangThai = 'DANG_LAM_VIEC'")
    List<NhanVien> findActiveEmployees();
    
    @Query("SELECT n FROM NhanVien n ORDER BY n.doanhSoThang DESC")
    List<NhanVien> findTopPerformers();
    
    @Query("SELECT n FROM NhanVien n ORDER BY n.hieuSuatPhanTram DESC")
    List<NhanVien> findByPerformanceRating();
    
    @Query("SELECT COUNT(n) FROM NhanVien n WHERE n.trangThai = 'DANG_LAM_VIEC'")
    Long countActiveEmployees();
    
    @Query("SELECT AVG(n.luongCoBan) FROM NhanVien n WHERE n.trangThai = 'DANG_LAM_VIEC'")
    Double calculateAverageSalary();
    
    @Query("SELECT SUM(n.doanhSoThang) FROM NhanVien n WHERE n.phongBan = 'BAN_HANG'")
    Double calculateTotalSalesRevenue();
    
    @Query("SELECT n FROM NhanVien n WHERE n.ngayVaoLam BETWEEN :startDate AND :endDate")
    List<NhanVien> findByHireDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
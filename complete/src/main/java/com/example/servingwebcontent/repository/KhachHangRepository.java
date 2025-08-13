package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    
    Optional<KhachHang> findByMaKhachHang(String maKhachHang);
    Optional<KhachHang> findBySoDienThoai(String soDienThoai);
    Optional<KhachHang> findByEmail(String email);
    List<KhachHang> findByHoTenContainingIgnoreCase(String hoTen);
    List<KhachHang> findByLoaiKhachHang(KhachHang.LoaiKhachHang loaiKhachHang);
    
    @Query("SELECT COUNT(k) FROM KhachHang k WHERE k.loaiKhachHang = 'VIP'")
    Long countVipCustomers();
    
    @Query("SELECT COUNT(k) FROM KhachHang k WHERE k.loaiKhachHang = 'PREMIUM'")
    Long countPremiumCustomers();
    
    @Query("SELECT k FROM KhachHang k ORDER BY k.diemTichLuy DESC")
    List<KhachHang> findTopCustomersByPoints();
    
    @Query("SELECT k FROM KhachHang k ORDER BY k.tongChiTieu DESC")
    List<KhachHang> findTopCustomersBySpending();
    
    @Query("SELECT SUM(k.tongChiTieu) FROM KhachHang k")
    Double calculateTotalCustomerSpending();
    
    @Query("SELECT k FROM KhachHang k WHERE MONTH(k.ngaySinh) = MONTH(:date) AND DAY(k.ngaySinh) = DAY(:date)")
    List<KhachHang> findCustomersWithBirthdayToday(@Param("date") LocalDate date);
}
package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.GiaoDich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiaoDichRepository extends JpaRepository<GiaoDich, Long> {
    
    Optional<GiaoDich> findByMaGiaoDich(String maGiaoDich);
    List<GiaoDich> findByLoaiGiaoDich(GiaoDich.LoaiGiaoDich loaiGiaoDich);
    List<GiaoDich> findByTrangThai(GiaoDich.TrangThaiGiaoDich trangThai);
    List<GiaoDich> findByPhuongThucThanhToan(GiaoDich.PhuongThucThanhToan phuongThucThanhToan);
    List<GiaoDich> findByKhachHangId(Long khachHangId);
    List<GiaoDich> findByNhanVienId(Long nhanVienId);
    List<GiaoDich> findBySanPhamId(Long sanPhamId);
    
    @Query("SELECT g FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH'")
    List<GiaoDich> findCompletedTransactions();
    
    @Query("SELECT g FROM GiaoDich g WHERE DATE(g.createdAt) = :date")
    List<GiaoDich> findTransactionsByDate(@Param("date") LocalDate date);
    
    @Query("SELECT g FROM GiaoDich g WHERE g.createdAt BETWEEN :startDate AND :endDate")
    List<GiaoDich> findTransactionsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(g.tongTien) FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH'")
    Double calculateTotalRevenue();
    
    @Query("SELECT SUM(g.tongTien) FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH' AND DATE(g.createdAt) = :date")
    Double calculateDailyRevenue(@Param("date") LocalDate date);
    
    @Query("SELECT SUM(g.tongTien) FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH' AND MONTH(g.createdAt) = MONTH(:date) AND YEAR(g.createdAt) = YEAR(:date)")
    Double calculateMonthlyRevenue(@Param("date") LocalDate date);
    
    @Query("SELECT COUNT(g) FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH' AND DATE(g.createdAt) = :date")
    Long countDailyTransactions(@Param("date") LocalDate date);
    
    @Query("SELECT g.phuongThucThanhToan, COUNT(g) FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH' GROUP BY g.phuongThucThanhToan")
    List<Object[]> getPaymentMethodStatistics();
    
    @Query("SELECT g FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH' ORDER BY g.tongTien DESC")
    List<GiaoDich> findTopTransactionsByValue();
    
    @Query("SELECT AVG(g.tongTien) FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH'")
    Double calculateAverageTransactionValue();
    
    @Query("SELECT g FROM GiaoDich g WHERE g.trangThai = 'HOAN_THANH' ORDER BY g.createdAt DESC")
    List<GiaoDich> findRecentTransactions();
}

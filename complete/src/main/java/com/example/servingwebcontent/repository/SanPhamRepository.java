// src/main/java/com/example/servingwebcontent/repository/SanPhamRepository.java
package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
// removed: import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    
    Optional<SanPham> findByMaSanPham(String maSanPham);
    List<SanPham> findByDanhMuc(SanPham.DanhMucSanPham danhMuc);
    List<SanPham> findByTrangThai(SanPham.TrangThaiSanPham trangThai);
    List<SanPham> findByTenSanPhamContainingIgnoreCase(String tenSanPham);
    
    @Query("SELECT s FROM SanPham s WHERE s.soLuongTonKho <= s.soLuongToiThieu AND s.soLuongTonKho > 0")
    List<SanPham> findSanPhamSapHetHang();
    
    @Query("SELECT s FROM SanPham s WHERE s.soLuongTonKho = 0")
    List<SanPham> findSanPhamHetHang();
    
    @Query("SELECT s FROM SanPham s ORDER BY s.soLuongDaBan DESC")
    List<SanPham> findTopSellingProducts();
    
    @Query("SELECT s FROM SanPham s WHERE s.giaBan BETWEEN :minPrice AND :maxPrice")
    List<SanPham> findByGiaBanBetween(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
    @Query("SELECT SUM(s.soLuongTonKho * s.giaVon) FROM SanPham s")
    Double calculateTotalInventoryValue();
    
    @Query("SELECT COUNT(s) FROM SanPham s WHERE s.trangThai = 'CON_HANG'")
    Long countProductsInStock();
}

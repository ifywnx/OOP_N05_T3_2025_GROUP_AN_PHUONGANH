package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    public Optional<SanPham> getSanPhamById(Long id) {
        return sanPhamRepository.findById(id);
    }

    public Optional<SanPham> getSanPhamByMa(String maSanPham) {
        return sanPhamRepository.findByMaSanPham(maSanPham);
    }

    public List<SanPham> getSanPhamByDanhMuc(SanPham.DanhMucSanPham danhMuc) {
        return sanPhamRepository.findByDanhMuc(danhMuc);
    }

    public List<SanPham> searchSanPham(String keyword) {
        return sanPhamRepository.findByTenSanPhamContainingIgnoreCase(keyword);
    }

    public SanPham saveSanPham(SanPham sanPham) {
        sanPham.updateTrangThai();
        return sanPhamRepository.save(sanPham);
    }

    public void deleteSanPham(Long id) {
        sanPhamRepository.deleteById(id);
    }

    public List<SanPham> getSanPhamSapHetHang() {
        return sanPhamRepository.findSanPhamSapHetHang();
    }

    public List<SanPham> getSanPhamHetHang() {
        return sanPhamRepository.findSanPhamHetHang();
    }

    public List<SanPham> getTopSellingProducts() {
        return sanPhamRepository.findTopSellingProducts();
    }

    public Double getTotalInventoryValue() {
        return sanPhamRepository.calculateTotalInventoryValue();
    }

    public Long getProductsInStockCount() {
        return sanPhamRepository.countProductsInStock();
    }

    public boolean banHang(String maSanPham, Integer soLuong) {
        Optional<SanPham> sanPhamOpt = sanPhamRepository.findByMaSanPham(maSanPham);
        if (sanPhamOpt.isPresent()) {
            SanPham sanPham = sanPhamOpt.get();
            if (sanPham.banHang(soLuong)) {
                sanPhamRepository.save(sanPham);
                return true;
            }
        }
        return false;
    }

    public void nhapHang(String maSanPham, Integer soLuong) {
        Optional<SanPham> sanPhamOpt = sanPhamRepository.findByMaSanPham(maSanPham);
        if (sanPhamOpt.isPresent()) {
            SanPham sanPham = sanPhamOpt.get();
            sanPham.nhapHang(soLuong);
            sanPhamRepository.save(sanPham);
        }
    }
}

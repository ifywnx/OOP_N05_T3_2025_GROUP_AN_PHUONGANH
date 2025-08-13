package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    public List<NhanVien> getAllNhanVien() {
        return nhanVienRepository.findAll();
    }

    public Optional<NhanVien> getNhanVienById(Long id) {
        return nhanVienRepository.findById(id);
    }

    public Optional<NhanVien> getNhanVienByMa(String maNhanVien) {
        return nhanVienRepository.findByMaNhanVien(maNhanVien);
    }

    public List<NhanVien> searchNhanVien(String keyword) {
        return nhanVienRepository.findByHoTenContainingIgnoreCase(keyword);
    }

    public NhanVien saveNhanVien(NhanVien nhanVien) {
        nhanVien.capNhatHieuSuat();
        return nhanVienRepository.save(nhanVien);
    }

    public void deleteNhanVien(Long id) {
        nhanVienRepository.deleteById(id);
    }

    public List<NhanVien> getNhanVienByPhongBan(NhanVien.PhongBan phongBan) {
        return nhanVienRepository.findByPhongBan(phongBan);
    }

    public List<NhanVien> getActiveEmployees() {
        return nhanVienRepository.findActiveEmployees();
    }

    public List<NhanVien> getTopPerformers() {
        return nhanVienRepository.findTopPerformers();
    }

    public Long getActiveEmployeeCount() {
        return nhanVienRepository.countActiveEmployees();
    }

    public Double getAverageSalary() {
        return nhanVienRepository.calculateAverageSalary();
    }

    public Double getTotalSalesRevenue() {
        return nhanVienRepository.calculateTotalSalesRevenue();
    }

    public void capNhatDoanhSo(String maNhanVien, Double giaTriDonHang) {
        Optional<NhanVien> nhanVienOpt = nhanVienRepository.findByMaNhanVien(maNhanVien);
        if (nhanVienOpt.isPresent()) {
            NhanVien nhanVien = nhanVienOpt.get();
            nhanVien.xuLyBanHang(giaTriDonHang);
            nhanVienRepository.save(nhanVien);
        }
    }
}

package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.KhachHang;
import com.example.servingwebcontent.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    public List<KhachHang> getAllKhachHang() {
        return khachHangRepository.findAll();
    }

    public Optional<KhachHang> getKhachHangById(Long id) {
        return khachHangRepository.findById(id);
    }

    public Optional<KhachHang> getKhachHangByMa(String maKhachHang) {
        return khachHangRepository.findByMaKhachHang(maKhachHang);
    }

    public Optional<KhachHang> getKhachHangBySoDienThoai(String soDienThoai) {
        return khachHangRepository.findBySoDienThoai(soDienThoai);
    }

    public List<KhachHang> searchKhachHang(String keyword) {
        return khachHangRepository.findByHoTenContainingIgnoreCase(keyword);
    }

    public KhachHang saveKhachHang(KhachHang khachHang) {
        khachHang.capNhatLoaiKhachHang();
        return khachHangRepository.save(khachHang);
    }

    public void deleteKhachHang(Long id) {
        khachHangRepository.deleteById(id);
    }

    public List<KhachHang> getKhachHangByLoai(KhachHang.LoaiKhachHang loaiKhachHang) {
        return khachHangRepository.findByLoaiKhachHang(loaiKhachHang);
    }

    public List<KhachHang> getTopCustomersByPoints() {
        return khachHangRepository.findTopCustomersByPoints();
    }

    public List<KhachHang> getTopCustomersBySpending() {
        return khachHangRepository.findTopCustomersBySpending();
    }

    public Long getVipCustomersCount() {
        return khachHangRepository.countVipCustomers();
    }

    public Long getPremiumCustomersCount() {
        return khachHangRepository.countPremiumCustomers();
    }

    public Double getTotalCustomerSpending() {
        return khachHangRepository.calculateTotalCustomerSpending();
    }

    public List<KhachHang> getCustomersWithBirthdayToday() {
        return khachHangRepository.findCustomersWithBirthdayToday(LocalDate.now());
    }

    public void capNhatThongTinMuaHang(String maKhachHang, Double giaTriDonHang, Integer soLuong) {
        Optional<KhachHang> khachHangOpt = khachHangRepository.findByMaKhachHang(maKhachHang);
        if (khachHangOpt.isPresent()) {
            KhachHang khachHang = khachHangOpt.get();
            khachHang.capNhatThongTinMuaHang(giaTriDonHang, soLuong);
            khachHangRepository.save(khachHang);
        }
    }
}
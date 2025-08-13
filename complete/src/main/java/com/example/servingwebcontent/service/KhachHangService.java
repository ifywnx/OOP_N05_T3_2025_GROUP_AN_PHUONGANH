package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.KhachHang;
import com.example.servingwebcontent.repository.KhachHangRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangService {
    private final KhachHangRepository repository;

    public KhachHangService(KhachHangRepository repository) {
        this.repository = repository;
    }

    public List<KhachHang> findAll() { return repository.findAll(); }

    /** Dùng cho controller: trả về entity hoặc null */
    public KhachHang findById(Long id) { return repository.findById(id).orElse(null); }

    /** Dùng khi muốn ném lỗi 404 rõ ràng */
    public KhachHang findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Không tìm thấy khách hàng #" + id));
    }

    public KhachHang save(KhachHang khachHang) { return repository.save(khachHang); }

    public void deleteById(Long id) { repository.deleteById(id); }
}

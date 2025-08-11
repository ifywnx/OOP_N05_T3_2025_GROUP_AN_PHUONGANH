package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.repository.SanPhamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamService {
    private final SanPhamRepository repository;

    public SanPhamService(SanPhamRepository repository) {
        this.repository = repository;
    }

    public List<SanPham> findAll() { return repository.findAll(); }

    public SanPham findById(Long id) { return repository.findById(id).orElse(null); }

    public SanPham findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Không tìm thấy sản phẩm #" + id));
    }

    public SanPham save(SanPham sanPham) { return repository.save(sanPham); }

    public void deleteById(Long id) { repository.deleteById(id); }
}

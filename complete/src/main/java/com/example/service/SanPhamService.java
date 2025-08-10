package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.repository.SanPhamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {

    private final SanPhamRepository repository;

    public SanPhamService(SanPhamRepository repository) {
        this.repository = repository;
    }

    public List<SanPham> findAll() {
        return repository.findAll();
    }

    public Optional<SanPham> findById(Long id) {
        return repository.findById(id);
    }

    public SanPham save(SanPham sanPham) {
        return repository.save(sanPham);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.KhachHang;
import com.example.servingwebcontent.repository.KhachHangRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService {

    private final KhachHangRepository repository;

    public KhachHangService(KhachHangRepository repository) {
        this.repository = repository;
    }

    public List<KhachHang> findAll() {
        return repository.findAll();
    }

    public Optional<KhachHang> findById(Long id) {
        return repository.findById(id);
    }

    public KhachHang save(KhachHang khachHang) {
        return repository.save(khachHang);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

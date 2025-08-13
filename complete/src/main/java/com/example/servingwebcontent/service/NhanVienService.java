package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.repository.NhanVienRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienService {
    private final NhanVienRepository repository;

    public NhanVienService(NhanVienRepository repository) {
        this.repository = repository;
    }

    public List<NhanVien> findAll() { return repository.findAll(); }

    public NhanVien findById(Long id) { return repository.findById(id).orElse(null); }

    public NhanVien findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Không tìm thấy nhân viên #" + id));
    }

    public NhanVien save(NhanVien nhanVien) { return repository.save(nhanVien); }

    public void deleteById(Long id) { repository.deleteById(id); }
}


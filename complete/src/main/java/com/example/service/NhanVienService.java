package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.repository.NhanVienRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienService {

    private final NhanVienRepository repository;

    public NhanVienService(NhanVienRepository repository) {
        this.repository = repository;
    }

    public List<NhanVien> findAll() {
        return repository.findAll();
    }

    public Optional<NhanVien> findById(Long id) {
        return repository.findById(id);
    }

    public NhanVien save(NhanVien nhanVien) {
        return repository.save(nhanVien);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

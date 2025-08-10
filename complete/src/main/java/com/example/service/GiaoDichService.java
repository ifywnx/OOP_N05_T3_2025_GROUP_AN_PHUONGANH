package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.GiaoDich;
import com.example.servingwebcontent.repository.GiaoDichRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiaoDichService {

    private final GiaoDichRepository repository;

    public GiaoDichService(GiaoDichRepository repository) {
        this.repository = repository;
    }

    public List<GiaoDich> findAll() {
        return repository.findAll();
    }

    public Optional<GiaoDich> findById(Long id) {
        return repository.findById(id);
    }

    public GiaoDich save(GiaoDich giaoDich) {
        return repository.save(giaoDich);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

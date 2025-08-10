package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.service.NhanVienService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {
    private final NhanVienService service;

    public NhanVienController(NhanVienService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("dsNhanVien", service.findAll());
        return "nhanvien/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
        return "nhanvien/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("nhanVien") NhanVien nv) {
        service.save(nv);
        return "redirect:/nhanvien";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("nhanVien", service.findById(id));
        return "nhanvien/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/nhanvien";
    }
}

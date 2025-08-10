package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.service.SanPhamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sanpham")
public class SanPhamController {
    private final SanPhamService service;

    public SanPhamController(SanPhamService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("dsSanPham", service.findAll());
        return "sanpham/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("sanPham", new SanPham());
        return "sanpham/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("sanPham") SanPham sp) {
        service.save(sp);
        return "redirect:/sanpham";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("sanPham", service.findById(id));
        return "sanpham/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/sanpham";
    }
}

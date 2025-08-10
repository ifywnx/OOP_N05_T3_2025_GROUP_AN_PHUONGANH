package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.KhachHang;
import com.example.servingwebcontent.service.KhachHangService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khachhang")
public class KhachHangController {
    private final KhachHangService service;

    public KhachHangController(KhachHangService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("dsKhachHang", service.findAll());
        return "khachhang/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "khachhang/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("khachHang") KhachHang kh) {
        service.save(kh);
        return "redirect:/khachhang";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("khachHang", service.findById(id));
        return "khachhang/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/khachhang";
    }
}

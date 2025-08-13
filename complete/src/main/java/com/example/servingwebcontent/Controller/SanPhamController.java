package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String save(@Valid @ModelAttribute("sanPham") SanPham sp,
                       BindingResult br,
                       RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "sanpham/form";
        }
        service.save(sp);
        ra.addFlashAttribute("success", "Lưu sản phẩm thành công");
        return "redirect:/sanpham";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        SanPham sp = service.findById(id);
        if (sp == null) {
            ra.addFlashAttribute("error", "Không tìm thấy sản phẩm");
            return "redirect:/sanpham";
        }
        model.addAttribute("sanPham", sp);
        return "sanpham/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("success", "Xóa sản phẩm thành công");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Xóa thất bại: " + e.getMessage());
        }
        return "redirect:/sanpham";
    }
}

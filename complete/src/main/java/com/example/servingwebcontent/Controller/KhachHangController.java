package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.KhachHang;
import com.example.servingwebcontent.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String save(@Valid @ModelAttribute("khachHang") KhachHang kh,
                       BindingResult br,
                       RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "khachhang/form"; // quay lại form và hiển thị lỗi từ Bean Validation
        }
        service.save(kh);
        ra.addFlashAttribute("success", "Lưu khách hàng thành công");
        return "redirect:/khachhang";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        KhachHang kh = service.findById(id);
        if (kh == null) {
            ra.addFlashAttribute("error", "Không tìm thấy khách hàng");
            return "redirect:/khachhang";
        }
        model.addAttribute("khachHang", kh);
        return "khachhang/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("success", "Xóa khách hàng thành công");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Xóa thất bại: " + e.getMessage());
        }
        return "redirect:/khachhang";
    }
}

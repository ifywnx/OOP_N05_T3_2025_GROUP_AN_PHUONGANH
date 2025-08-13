package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String save(@Valid @ModelAttribute("nhanVien") NhanVien nv,
                       BindingResult br,
                       RedirectAttributes ra) {
        if (br.hasErrors()) return "nhanvien/form";
        service.save(nv);
        ra.addFlashAttribute("success", "Lưu nhân viên thành công");
        return "redirect:/nhanvien";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        NhanVien nv = service.findById(id);
        if (nv == null) {
            ra.addFlashAttribute("error", "Không tìm thấy nhân viên");
            return "redirect:/nhanvien";
        }
        model.addAttribute("nhanVien", nv);
        return "nhanvien/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("success", "Xóa nhân viên thành công");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Xóa thất bại: " + e.getMessage());
        }
        return "redirect:/nhanvien";
    }
}

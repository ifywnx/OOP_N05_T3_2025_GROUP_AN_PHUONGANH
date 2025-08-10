package com.cakeshop.app.controller;

import com.cakeshop.app.model.NhanVien;
import com.cakeshop.app.repo.NhanVienRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {

    private final NhanVienRepo repo;

    public NhanVienController(NhanVienRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(value="q", required=false) String q) {
        model.addAttribute("items",
                (q == null || q.isBlank()) ? repo.findAll() : repo.searchByKeyword(q));
        model.addAttribute("q", q);
        return "nhanvien-list";
    }

    @GetMapping("/tao")
    public String createForm(Model model) {
        model.addAttribute("nv", new NhanVien());
        model.addAttribute("mode", "create");
        return "nhanvien-form";
    }

    @PostMapping("/tao")
    public String create(@ModelAttribute("nv") NhanVien nv, Model model) {
        if (repo.exists(nv.getMaNhanVien())) {
            model.addAttribute("nv", nv);
            model.addAttribute("mode", "create");
            model.addAttribute("error", "Ma nhan vien da ton tai");
            return "nhanvien-form";
        }
        repo.save(nv);
        return "redirect:/nhan-vien";
    }

    @GetMapping("/sua/{ma}")
    public String editForm(@PathVariable String ma, Model model) {
        NhanVien nv = repo.findById(ma);
        if (nv == null) return "redirect:/nhan-vien";
        model.addAttribute("nv", nv);
        model.addAttribute("mode", "edit");
        return "nhanvien-form";
    }

    @PostMapping("/sua/{ma}")
    public String edit(@PathVariable String ma, @ModelAttribute("nv") NhanVien nv) {
        nv.setMaNhanVien(ma);
        repo.save(nv);
        return "redirect:/nhan-vien";
    }

    @PostMapping("/xoa/{ma}")
    public String delete(@PathVariable String ma) {
        repo.delete(ma);
        return "redirect:/nhan-vien";
    }
}

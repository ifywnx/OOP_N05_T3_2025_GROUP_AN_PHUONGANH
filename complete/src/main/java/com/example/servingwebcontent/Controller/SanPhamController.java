package com.cakeshop.app.controller;

import com.cakeshop.app.model.SanPham;
import com.cakeshop.app.repo.SanPhamRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/san-pham")
public class SanPhamController {
    private final SanPhamRepo repo;

    public SanPhamController(SanPhamRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(value="q", required=false) String q) {
        model.addAttribute("items",
                (q == null || q.isBlank()) ? repo.findAll() : repo.searchByKeyword(q));
        model.addAttribute("q", q);
        return "sanpham-list";
    }

    @GetMapping("/tao")
    public String createForm(Model model) {
        model.addAttribute("sp", new SanPham());
        model.addAttribute("mode", "create");
        return "sanpham-form";
    }

    @PostMapping("/tao")
    public String create(@ModelAttribute("sp") SanPham sp, Model model) {
        if (repo.exists(sp.getMaSanPham())) {
            model.addAttribute("sp", sp);
            model.addAttribute("mode", "create");
            model.addAttribute("error", "Ma san pham da ton tai");
            return "sanpham-form";
        }
        repo.save(sp);
        return "redirect:/san-pham";
    }

    @GetMapping("/sua/{ma}")
    public String editForm(@PathVariable String ma, Model model) {
        SanPham sp = repo.findById(ma);
        if (sp == null) return "redirect:/san-pham";
        model.addAttribute("sp", sp);
        model.addAttribute("mode", "edit");
        return "sanpham-form";
    }

    @PostMapping("/sua/{ma}")
    public String edit(@PathVariable String ma, @ModelAttribute("sp") SanPham sp) {
        sp.setMaSanPham(ma);
        repo.save(sp);
        return "redirect:/san-pham";
    }

    @PostMapping("/xoa/{ma}")
    public String delete(@PathVariable String ma) {
        repo.delete(ma);
        return "redirect:/san-pham";
    }
}

package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.GiaoDich;
import com.example.servingwebcontent.service.GiaoDichService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/giaodich")
public class GiaoDichController {
    private final GiaoDichService service;

    public GiaoDichController(GiaoDichService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("dsGiaoDich", service.findAll());
        return "giaodich/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("giaoDich", new GiaoDich());
        return "giaodich/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("giaoDich") GiaoDich gd) {
        service.save(gd);
        return "redirect:/giaodich";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("giaoDich", service.findById(id));
        return "giaodich/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/giaodich";
    }
}

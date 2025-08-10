package com.cakeshop.app.controller;

import com.cakeshop.app.repo.KhachHangRepo;
import com.cakeshop.app.repo.NhanVienRepo;
import com.cakeshop.app.repo.SanPhamRepo;
import com.cakeshop.app.repo.GiaoDichRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final SanPhamRepo spRepo;
    private final KhachHangRepo khRepo;
    private final NhanVienRepo nvRepo;
    private final GiaoDichRepo gdRepo;

    public DashboardController(SanPhamRepo spRepo, KhachHangRepo khRepo,
                               NhanVienRepo nvRepo, GiaoDichRepo gdRepo) {
        this.spRepo = spRepo;
        this.khRepo = khRepo;
        this.nvRepo = nvRepo;
        this.gdRepo = gdRepo;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("tongSanPham", spRepo.findAll().size());
        model.addAttribute("tongKhachHang", khRepo.findAll().size());
        model.addAttribute("tongNhanVien", nvRepo.findAll().size());
        model.addAttribute("tongGiaoDich", gdRepo.findAll().size());
        model.addAttribute("sapHet", spRepo.findSapHet(5)); // tuỳ repo bạn cài
        return "dashboard";
    }
}

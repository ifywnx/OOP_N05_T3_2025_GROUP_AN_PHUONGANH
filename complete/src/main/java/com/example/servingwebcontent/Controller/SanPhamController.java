package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/sanpham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping({"", "/"})
    public String listSanPham(@RequestParam(required = false) String category,
                              @RequestParam(required = false) String search,
                              Model model) {
        
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("sanPhams", sanPhamService.searchSanPham(search.trim()));
            model.addAttribute("searchKeyword", search.trim());
        } else if (category != null && !category.isEmpty()) {
            try {
                SanPham.DanhMucSanPham danhMuc = SanPham.DanhMucSanPham.valueOf(category.toUpperCase());
                model.addAttribute("sanPhams", sanPhamService.getSanPhamByDanhMuc(danhMuc));
                model.addAttribute("selectedCategory", category);
            } catch (IllegalArgumentException e) {
                model.addAttribute("sanPhams", sanPhamService.getAllSanPham());
            }
        } else {
            model.addAttribute("sanPhams", sanPhamService.getAllSanPham());
        }

        // Add categories for filter
        model.addAttribute("categories", SanPham.DanhMucSanPham.values());
        model.addAttribute("lowStockProducts", sanPhamService.getSanPhamSapHetHang());
        model.addAttribute("outOfStockProducts", sanPhamService.getSanPhamHetHang());
        model.addAttribute("pageTitle", "Quản Lý Sản Phẩm");
        
        return "sanpham/list";
    }

    @GetMapping("/add")
    public String addSanPhamForm(Model model) {
        model.addAttribute("sanPham", new SanPham());
        model.addAttribute("categories", SanPham.DanhMucSanPham.values());
        model.addAttribute("pageTitle", "Thêm Sản Phẩm Mới");
        return "sanpham/add";
    }

    @PostMapping("/add")
    public String addSanPham(@Valid @ModelAttribute SanPham sanPham, 
                            BindingResult result, 
                            RedirectAttributes redirectAttributes,
                            Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("categories", SanPham.DanhMucSanPham.values());
            model.addAttribute("pageTitle", "Thêm Sản Phẩm Mới");
            return "sanpham/add";
        }

        // Check if product code already exists
        if (sanPhamService.getSanPhamByMa(sanPham.getMaSanPham()).isPresent()) {
            result.rejectValue("maSanPham", "error.sanPham", "Mã sản phẩm đã tồn tại");
            model.addAttribute("categories", SanPham.DanhMucSanPham.values());
            model.addAttribute("pageTitle", "Thêm Sản Phẩm Mới");
            return "sanpham/add";
        }

        try {
            sanPhamService.saveSanPham(sanPham);
            redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công!");
            return "redirect:/sanpham";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/sanpham/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editSanPhamForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<SanPham> sanPhamOpt = sanPhamService.getSanPhamById(id);
        
        if (sanPhamOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm!");
            return "redirect:/sanpham";
        }

        model.addAttribute("sanPham", sanPhamOpt.get());
        model.addAttribute("categories", SanPham.DanhMucSanPham.values());
        model.addAttribute("pageTitle", "Chỉnh Sửa Sản Phẩm");
        return "sanpham/edit";
    }

    @PostMapping("/edit/{id}")
    public String editSanPham(@PathVariable Long id,
                             @Valid @ModelAttribute SanPham sanPham,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("categories", SanPham.DanhMucSanPham.values());
            model.addAttribute("pageTitle", "Chỉnh Sửa Sản Phẩm");
            return "sanpham/edit";
        }

        try {
            sanPham.setId(id);
            sanPhamService.saveSanPham(sanPham);
            redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công!");
            return "redirect:/sanpham";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/sanpham/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteSanPham(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sanPhamService.deleteSanPham(id);
            redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa sản phẩm: " + e.getMessage());
        }
        return "redirect:/sanpham";
    }

    @GetMapping("/view/{id}")
    public String viewSanPham(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<SanPham> sanPhamOpt = sanPhamService.getSanPhamById(id);
        
        if (sanPhamOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm!");
            return "redirect:/sanpham";
        }

        model.addAttribute("sanPham", sanPhamOpt.get());
        model.addAttribute("pageTitle", "Chi Tiết Sản Phẩm");
        return "sanpham/view";
    }

    @PostMapping("/restock/{id}")
    public String restockSanPham(@PathVariable Long id,
                                @RequestParam Integer soLuong,
                                RedirectAttributes redirectAttributes) {
        try {
            Optional<SanPham> sanPhamOpt = sanPhamService.getSanPhamById(id);
            if (sanPhamOpt.isPresent()) {
                sanPhamService.nhapHang(sanPhamOpt.get().getMaSanPham(), soLuong);
                redirectAttributes.addFlashAttribute("success", "Nhập hàng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/sanpham";
    }
}
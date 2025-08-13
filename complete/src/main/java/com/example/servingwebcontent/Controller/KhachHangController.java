package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.model.KhachHang;
import com.example.servingwebcontent.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @GetMapping({"", "/"})
    public String listKhachHang(@RequestParam(required = false) String search,
                               @RequestParam(required = false) String type,
                               Model model) {

        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("khachHangs", khachHangService.searchKhachHang(search.trim()));
            model.addAttribute("searchKeyword", search.trim());
        } else if (type != null && !type.isEmpty()) {
            try {
                KhachHang.LoaiKhachHang loaiKhachHang = KhachHang.LoaiKhachHang.valueOf(type.toUpperCase());
                model.addAttribute("khachHangs", khachHangService.getKhachHangByLoai(loaiKhachHang));
                model.addAttribute("selectedType", type);
            } catch (IllegalArgumentException e) {
                model.addAttribute("khachHangs", khachHangService.getAllKhachHang());
            }
        } else {
            model.addAttribute("khachHangs", khachHangService.getAllKhachHang());
        }

        // Analytics data - với null safety
        model.addAttribute("customerTypes", KhachHang.LoaiKhachHang.values());
        
        Long vipCount = khachHangService.getVipCustomersCount();
        model.addAttribute("vipCount", vipCount != null ? vipCount : 0);
        
        Long premiumCount = khachHangService.getPremiumCustomersCount();
        model.addAttribute("premiumCount", premiumCount != null ? premiumCount : 0);
        
        model.addAttribute("topCustomers", khachHangService.getTopCustomersBySpending());
        model.addAttribute("birthdayCustomers", khachHangService.getCustomersWithBirthdayToday());
        
        Double totalSpending = khachHangService.getTotalCustomerSpending();
        model.addAttribute("totalSpending", totalSpending != null ? totalSpending : 0.0);
        
        model.addAttribute("pageTitle", "Quản Lý Khách Hàng");

        return "khachhang/list";
    }

    @GetMapping("/add")
    public String addKhachHangForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        model.addAttribute("genders", KhachHang.GioiTinh.values());
        model.addAttribute("isEdit", false);
        model.addAttribute("pageTitle", "Thêm Khách Hàng Mới");
        return "khachhang/form";
    }

    @PostMapping("/add")
    public String addKhachHang(@Valid @ModelAttribute KhachHang khachHang,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (result.hasErrors()) {
            model.addAttribute("genders", KhachHang.GioiTinh.values());
            model.addAttribute("isEdit", false);
            model.addAttribute("pageTitle", "Thêm Khách Hàng Mới");
            return "khachhang/form";
        }

        // Check if customer code already exists
        if (khachHangService.getKhachHangByMa(khachHang.getMaKhachHang()).isPresent()) {
            result.rejectValue("maKhachHang", "error.khachHang", "Mã khách hàng đã tồn tại");
            model.addAttribute("genders", KhachHang.GioiTinh.values());
            model.addAttribute("isEdit", false);
            model.addAttribute("pageTitle", "Thêm Khách Hàng Mới");
            return "khachhang/form";
        }

        // Check if phone number already exists
        if (khachHangService.getKhachHangBySoDienThoai(khachHang.getSoDienThoai()).isPresent()) {
            result.rejectValue("soDienThoai", "error.khachHang", "Số điện thoại đã tồn tại");
            model.addAttribute("genders", KhachHang.GioiTinh.values());
            model.addAttribute("isEdit", false);
            model.addAttribute("pageTitle", "Thêm Khách Hàng Mới");
            return "khachhang/form";
        }

        try {
            khachHangService.saveKhachHang(khachHang);
            redirectAttributes.addFlashAttribute("success", "Thêm khách hàng thành công!");
            return "redirect:/khachhang";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/khachhang/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editKhachHangForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<KhachHang> khachHangOpt = khachHangService.getKhachHangById(id);

        if (khachHangOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy khách hàng!");
            return "redirect:/khachhang";
        }

        model.addAttribute("khachHang", khachHangOpt.get());
        model.addAttribute("genders", KhachHang.GioiTinh.values());
        model.addAttribute("isEdit", true);
        model.addAttribute("pageTitle", "Chỉnh Sửa Khách Hàng");
        return "khachhang/form";
    }

    @PostMapping("/edit/{id}")
    public String editKhachHang(@PathVariable Long id,
                               @Valid @ModelAttribute KhachHang khachHang,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("genders", KhachHang.GioiTinh.values());
            model.addAttribute("isEdit", true);
            model.addAttribute("pageTitle", "Chỉnh Sửa Khách Hàng");
            return "khachhang/form";
        }

        try {
            khachHang.setId(id);
            khachHangService.saveKhachHang(khachHang);
            redirectAttributes.addFlashAttribute("success", "Cập nhật khách hàng thành công!");
            return "redirect:/khachhang";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/khachhang/edit/" + id;
        }
    }

    @GetMapping("/view/{id}")
    public String viewKhachHang(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<KhachHang> khachHangOpt = khachHangService.getKhachHangById(id);

        if (khachHangOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy khách hàng!");
            return "redirect:/khachhang";
        }

        model.addAttribute("khachHang", khachHangOpt.get());
        model.addAttribute("pageTitle", "Chi Tiết Khách Hàng");
        return "khachhang/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteKhachHang(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            khachHangService.deleteKhachHang(id);
            redirectAttributes.addFlashAttribute("success", "Xóa khách hàng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa khách hàng: " + e.getMessage());
        }
        return "redirect:/khachhang";
    }

    @PostMapping("/update-loyalty/{id}")
    public String updateLoyaltyPoints(@PathVariable Long id,
                                     @RequestParam Integer diemTichLuy,
                                     RedirectAttributes redirectAttributes) {
        try {
            Optional<KhachHang> khachHangOpt = khachHangService.getKhachHangById(id);
            if (khachHangOpt.isPresent()) {
                KhachHang khachHang = khachHangOpt.get();
                khachHang.setDiemTichLuy(diemTichLuy);
                khachHang.capNhatLoaiKhachHang();
                khachHangService.saveKhachHang(khachHang);
                redirectAttributes.addFlashAttribute("success", "Cập nhật điểm tích lũy thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy khách hàng!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/khachhang";
    }

    // API endpoints for customer analytics
    @GetMapping("/api/loyalty-stats")
    @ResponseBody
    @SuppressWarnings("unused")
    public Object getLoyaltyStatistics() {
        return new Object() {
            public final Long vipCount = khachHangService.getVipCustomersCount();
            public final Long premiumCount = khachHangService.getPremiumCustomersCount();
            public final Long totalCount = (long) khachHangService.getAllKhachHang().size();
            public final Double totalSpending = khachHangService.getTotalCustomerSpending();
        };
    }
}
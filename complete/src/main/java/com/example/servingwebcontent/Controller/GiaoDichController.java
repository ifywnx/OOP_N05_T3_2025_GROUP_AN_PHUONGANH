package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.GiaoDich;
import com.example.servingwebcontent.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/giaodich")
public class GiaoDichController {

    @Autowired private GiaoDichService giaoDichService;
    @Autowired private SanPhamService sanPhamService;
    @Autowired private KhachHangService khachHangService;
    @Autowired private NhanVienService nhanVienService;

    @GetMapping({"", "/"})
    public String listGiaoDich(@RequestParam(required = false) String status,
                              @RequestParam(required = false) String date,
                              Model model) {
        
        if (status != null && !status.isEmpty()) {
            try {
                GiaoDich.TrangThaiGiaoDich trangThai = GiaoDich.TrangThaiGiaoDich.valueOf(status.toUpperCase());
                model.addAttribute("giaoDichs", giaoDichService.getAllGiaoDich().stream()
                    .filter(gd -> gd.getTrangThai() == trangThai).toList());
                model.addAttribute("selectedStatus", status);
            } catch (IllegalArgumentException e) {
                model.addAttribute("giaoDichs", giaoDichService.getAllGiaoDich());
            }
        } else if (date != null && !date.isEmpty()) {
            try {
                LocalDate filterDate = LocalDate.parse(date);
                model.addAttribute("giaoDichs", giaoDichService.getTransactionsByDate(filterDate));
                model.addAttribute("selectedDate", date);
            } catch (Exception e) {
                model.addAttribute("giaoDichs", giaoDichService.getAllGiaoDich());
            }
        } else {
            model.addAttribute("giaoDichs", giaoDichService.getAllGiaoDich());
        }

        // Analytics data - HOÀN THÀNH
        model.addAttribute("statuses", GiaoDich.TrangThaiGiaoDich.values());
        model.addAttribute("paymentMethods", GiaoDich.PhuongThucThanhToan.values());
        model.addAttribute("totalRevenue", giaoDichService.getTotalRevenue());
        model.addAttribute("dailyRevenue", giaoDichService.getDailyRevenue(LocalDate.now()));
        model.addAttribute("monthlyRevenue", giaoDichService.getMonthlyRevenue(LocalDate.now()));
        model.addAttribute("averageTransactionValue", giaoDichService.getAverageTransactionValue());
        model.addAttribute("recentTransactions", giaoDichService.getRecentTransactions());
        model.addAttribute("dailyTransactionCount", giaoDichService.getDailyTransactionCount(LocalDate.now()));
        model.addAttribute("pageTitle", "Quản Lý Giao Dịch");
        
        return "giaodich/list";
    }

    @GetMapping("/add")
    public String addGiaoDichForm(Model model) {
        model.addAttribute("giaoDich", new GiaoDich());
        model.addAttribute("sanPhams", sanPhamService.getAllSanPham());
        model.addAttribute("khachHangs", khachHangService.getAllKhachHang());
        model.addAttribute("nhanViens", nhanVienService.getActiveEmployees());
        model.addAttribute("transactionTypes", GiaoDich.LoaiGiaoDich.values());
        model.addAttribute("paymentMethods", GiaoDich.PhuongThucThanhToan.values());
        model.addAttribute("pageTitle", "Tạo Giao Dịch Mới");
        return "giaodich/add";
    }

    @PostMapping("/add")
    public String addGiaoDich(@RequestParam String maSanPham,
                             @RequestParam(required = false) String maKhachHang,
                             @RequestParam String maNhanVien,
                             @RequestParam Integer soLuong,
                             @RequestParam GiaoDich.PhuongThucThanhToan phuongThucThanhToan,
                             @RequestParam(required = false) Double tienKhachDua,
                             RedirectAttributes redirectAttributes) {
        
        try {
            GiaoDich giaoDich = giaoDichService.taoGiaoDichMoi(maSanPham, maKhachHang, maNhanVien, soLuong, phuongThucThanhToan);
            
            if (tienKhachDua != null && tienKhachDua >= giaoDich.getTongTien()) {
                giaoDichService.hoanThanhGiaoDich(giaoDich.getMaGiaoDich(), tienKhachDua);
                redirectAttributes.addFlashAttribute("success", "Tạo và hoàn thành giao dịch thành công!");
            } else {
                redirectAttributes.addFlashAttribute("success", "Tạo giao dịch thành công! Chờ thanh toán.");
            }
            
            return "redirect:/giaodich";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/giaodich/add";
        }
    }

    @GetMapping("/view/{id}")
    public String viewGiaoDich(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<GiaoDich> giaoDichOpt = giaoDichService.getGiaoDichById(id);
        
        if (giaoDichOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy giao dịch!");
            return "redirect:/giaodich";
        }

        model.addAttribute("giaoDich", giaoDichOpt.get());
        model.addAttribute("pageTitle", "Chi Tiết Giao Dịch");
        return "giaodich/view";
    }

    @PostMapping("/complete/{id}")
    public String completeGiaoDich(@PathVariable Long id,
                                  @RequestParam Double tienKhachDua,
                                  RedirectAttributes redirectAttributes) {
        try {
            Optional<GiaoDich> giaoDichOpt = giaoDichService.getGiaoDichById(id);
            if (giaoDichOpt.isPresent()) {
                boolean success = giaoDichService.hoanThanhGiaoDich(giaoDichOpt.get().getMaGiaoDich(), tienKhachDua);
                if (success) {
                    redirectAttributes.addFlashAttribute("success", "Hoàn thành giao dịch thành công!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Số tiền khách đưa không đủ!");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy giao dịch!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/giaodich";
    }

    @GetMapping("/cancel/{id}")
    public String cancelGiaoDich(@PathVariable Long id,
                                @RequestParam(required = false) String reason,
                                RedirectAttributes redirectAttributes) {
        try {
            Optional<GiaoDich> giaoDichOpt = giaoDichService.getGiaoDichById(id);
            if (giaoDichOpt.isPresent()) {
                GiaoDich giaoDich = giaoDichOpt.get();
                giaoDich.huyBoGiaoDich(reason != null ? reason : "Hủy bởi người dùng");
                giaoDichService.saveGiaoDich(giaoDich);
                redirectAttributes.addFlashAttribute("success", "Hủy giao dịch thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy giao dịch!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/giaodich";
    }

    @GetMapping("/receipt/{id}")
    public String printReceipt(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<GiaoDich> giaoDichOpt = giaoDichService.getGiaoDichById(id);
        
        if (giaoDichOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy giao dịch!");
            return "redirect:/giaodich";
        }

        GiaoDich giaoDich = giaoDichOpt.get();
        if (giaoDich.getTrangThai() != GiaoDich.TrangThaiGiaoDich.HOAN_THANH) {
            redirectAttributes.addFlashAttribute("error", "Chỉ có thể in hóa đơn cho giao dịch đã hoàn thành!");
            return "redirect:/giaodich";
        }

        model.addAttribute("giaoDich", giaoDich);
        model.addAttribute("pageTitle", "Hóa Đơn Bán Hàng");
        return "giaodich/receipt";
    }

    @GetMapping("/delete/{id}")
    public String deleteGiaoDich(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            giaoDichService.deleteGiaoDich(id);
            redirectAttributes.addFlashAttribute("success", "Xóa giao dịch thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa giao dịch: " + e.getMessage());
        }
        return "redirect:/giaodich";
    }

    // API endpoints for AJAX requests
    @GetMapping("/api/revenue/daily")
    @ResponseBody
    public Double getDailyRevenue(@RequestParam String date) {
        try {
            LocalDate filterDate = LocalDate.parse(date);
            return giaoDichService.getDailyRevenue(filterDate);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @GetMapping("/api/revenue/monthly")
    @ResponseBody
    public Double getMonthlyRevenue(@RequestParam String date) {
        try {
            LocalDate filterDate = LocalDate.parse(date);
            return giaoDichService.getMonthlyRevenue(filterDate);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @GetMapping("/api/transactions/count")
    @ResponseBody
    public Long getTransactionCount(@RequestParam String date) {
        try {
            LocalDate filterDate = LocalDate.parse(date);
            return giaoDichService.getDailyTransactionCount(filterDate);
        } catch (Exception e) {
            return 0L;
        }
    }
}
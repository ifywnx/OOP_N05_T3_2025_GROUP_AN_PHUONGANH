package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class DashboardController {

    @Autowired private SanPhamService sanPhamService;
    @Autowired private KhachHangService khachHangService;
    @Autowired private NhanVienService nhanVienService;
    @Autowired private GiaoDichService giaoDichService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        
        // KPI Metrics - Tổng quan hệ thống
        model.addAttribute("totalProducts", sanPhamService.getAllSanPham().size());
        model.addAttribute("totalCustomers", khachHangService.getAllKhachHang().size());
        model.addAttribute("totalEmployees", nhanVienService.getActiveEmployeeCount());
        
        // Revenue metrics với null safety
        Double dailyRev = giaoDichService.getDailyRevenue(LocalDate.now());
        model.addAttribute("dailyRevenue", dailyRev != null ? dailyRev : 0.0);
        
        Double monthlyRev = giaoDichService.getMonthlyRevenue(LocalDate.now());
        model.addAttribute("monthlyRevenue", monthlyRev != null ? monthlyRev : 0.0);
        
        Long dailyTrans = giaoDichService.getDailyTransactionCount(LocalDate.now());
        model.addAttribute("dailyTransactions", dailyTrans != null ? dailyTrans : 0L);

        // Product Analytics - Cảnh báo tồn kho
        model.addAttribute("lowStockProducts", sanPhamService.getSanPhamSapHetHang());
        model.addAttribute("outOfStockProducts", sanPhamService.getSanPhamHetHang());
        model.addAttribute("topSellingProducts", sanPhamService.getTopSellingProducts());
        
        Double inventoryVal = sanPhamService.getTotalInventoryValue();
        model.addAttribute("inventoryValue", inventoryVal != null ? inventoryVal : 0.0);

        // Customer Analytics - Sinh nhật & VIP
        model.addAttribute("vipCustomers", khachHangService.getVipCustomersCount());
        model.addAttribute("premiumCustomers", khachHangService.getPremiumCustomersCount());
        model.addAttribute("topCustomers", khachHangService.getTopCustomersBySpending());
        model.addAttribute("birthdayCustomers", khachHangService.getCustomersWithBirthdayToday());
        
        Double totalSpending = khachHangService.getTotalCustomerSpending();
        model.addAttribute("totalCustomerSpending", totalSpending != null ? totalSpending : 0.0);

        // Employee Analytics - Hiệu suất
        model.addAttribute("topPerformers", nhanVienService.getTopPerformers());
        
        Double avgSalary = nhanVienService.getAverageSalary();
        model.addAttribute("averageSalary", avgSalary != null ? avgSalary : 0.0);
        
        Double totalSalesRev = nhanVienService.getTotalSalesRevenue();
        model.addAttribute("totalSalesRevenue", totalSalesRev != null ? totalSalesRev : 0.0);

        // Recent Activity & Transaction Analytics - Hoạt động gần đây
        model.addAttribute("recentTransactions", giaoDichService.getRecentTransactions());
        model.addAttribute("completedTransactions", giaoDichService.getCompletedTransactions());
        
        Double avgTransValue = giaoDichService.getAverageTransactionValue();
        model.addAttribute("averageTransactionValue", avgTransValue != null ? avgTransValue : 0.0);

        model.addAttribute("pageTitle", "Dashboard - Tiệm Bánh Phenikaa");
        return "dashboard";
    }
}
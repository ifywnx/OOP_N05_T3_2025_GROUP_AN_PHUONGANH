package com.example.servingwebcontent.controller;

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
        
        // KPI Metrics
        model.addAttribute("totalProducts", sanPhamService.getAllSanPham().size());
        model.addAttribute("totalCustomers", khachHangService.getAllKhachHang().size());
        model.addAttribute("totalEmployees", nhanVienService.getActiveEmployeeCount());
        model.addAttribute("dailyRevenue", giaoDichService.getDailyRevenue(LocalDate.now()) != null ? 
                          giaoDichService.getDailyRevenue(LocalDate.now()) : 0.0);
        model.addAttribute("monthlyRevenue", giaoDichService.getMonthlyRevenue(LocalDate.now()) != null ? 
                          giaoDichService.getMonthlyRevenue(LocalDate.now()) : 0.0);
        model.addAttribute("dailyTransactions", giaoDichService.getDailyTransactionCount(LocalDate.now()));

        // Product Analytics
        model.addAttribute("lowStockProducts", sanPhamService.getSanPhamSapHetHang());
        model.addAttribute("outOfStockProducts", sanPhamService.getSanPhamHetHang());
        model.addAttribute("topSellingProducts", sanPhamService.getTopSellingProducts());
        model.addAttribute("inventoryValue", sanPhamService.getTotalInventoryValue() != null ? 
                          sanPhamService.getTotalInventoryValue() : 0.0);

        // Customer Analytics
        model.addAttribute("vipCustomers", khachHangService.getVipCustomersCount());
        model.addAttribute("premiumCustomers", khachHangService.getPremiumCustomersCount());
        model.addAttribute("topCustomers", khachHangService.getTopCustomersBySpending());
        model.addAttribute("birthdayCustomers", khachHangService.getCustomersWithBirthdayToday());
        model.addAttribute("totalCustomerSpending", khachHangService.getTotalCustomerSpending());

        // Employee Analytics
        model.addAttribute("topPerformers", nhanVienService.getTopPerformers());
        model.addAttribute("averageSalary", nhanVienService.getAverageSalary() != null ? 
                          nhanVienService.getAverageSalary() : 0.0);
        model.addAttribute("totalSalesRevenue", nhanVienService.getTotalSalesRevenue() != null ?
                          nhanVienService.getTotalSalesRevenue() : 0.0);

        // Recent Activity & Transaction Analytics
        model.addAttribute("recentTransactions", giaoDichService.getRecentTransactions());
        model.addAttribute("completedTransactions", giaoDichService.getCompletedTransactions());
        model.addAttribute("averageTransactionValue", giaoDichService.getAverageTransactionValue() != null ?
                          giaoDichService.getAverageTransactionValue() : 0.0);

        model.addAttribute("pageTitle", "Dashboard - Bakery Management");
        return "dashboard";
    }
}


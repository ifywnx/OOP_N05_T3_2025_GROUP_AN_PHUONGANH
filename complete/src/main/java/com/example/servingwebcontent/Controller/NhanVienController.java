package com.example.servingwebcontent.Controller;

import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping({"", "/"})
    public String listNhanVien(@RequestParam(required = false) String search,
                              @RequestParam(required = false) String department,
                              @RequestParam(required = false) String position,
                              Model model) {

        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("nhanViens", nhanVienService.searchNhanVien(search.trim()));
            model.addAttribute("searchKeyword", search.trim());
        } else if (department != null && !department.isEmpty()) {
            try {
                NhanVien.PhongBan phongBan = NhanVien.PhongBan.valueOf(department.toUpperCase());
                model.addAttribute("nhanViens", nhanVienService.getNhanVienByPhongBan(phongBan));
                model.addAttribute("selectedDepartment", department);
            } catch (IllegalArgumentException e) {
                model.addAttribute("nhanViens", nhanVienService.getAllNhanVien());
            }
        } else {
            model.addAttribute("nhanViens", nhanVienService.getAllNhanVien());
        }

        // Analytics data
        model.addAttribute("departments", NhanVien.PhongBan.values());
        model.addAttribute("positions", NhanVien.ChucVu.values());
        model.addAttribute("activeEmployeeCount", nhanVienService.getActiveEmployeeCount());
        model.addAttribute("averageSalary", nhanVienService.getAverageSalary());
        model.addAttribute("topPerformers", nhanVienService.getTopPerformers());
        model.addAttribute("totalSalesRevenue", nhanVienService.getTotalSalesRevenue());
        model.addAttribute("pageTitle", "Quản Lý Nhân Viên");

        return "nhanvien/list";
    }

    @GetMapping("/add")
    public String addNhanVienForm(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
        model.addAttribute("departments", NhanVien.PhongBan.values());
        model.addAttribute("positions", NhanVien.ChucVu.values());
        model.addAttribute("genders", NhanVien.GioiTinh.values());
        model.addAttribute("contractTypes", NhanVien.LoaiHopDong.values());
        model.addAttribute("pageTitle", "Thêm Nhân Viên Mới");
        return "nhanvien/add";
    }

    @PostMapping("/add")
    public String addNhanVien(@Valid @ModelAttribute NhanVien nhanVien,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("departments", NhanVien.PhongBan.values());
            model.addAttribute("positions", NhanVien.ChucVu.values());
            model.addAttribute("genders", NhanVien.GioiTinh.values());
            model.addAttribute("contractTypes", NhanVien.LoaiHopDong.values());
            model.addAttribute("pageTitle", "Thêm Nhân Viên Mới");
            return "nhanvien/add";
        }

        // Check if employee code already exists
        if (nhanVienService.getNhanVienByMa(nhanVien.getMaNhanVien()).isPresent()) {
            result.rejectValue("maNhanVien", "error.nhanVien", "Mã nhân viên đã tồn tại");
            model.addAttribute("departments", NhanVien.PhongBan.values());
            model.addAttribute("positions", NhanVien.ChucVu.values());
            model.addAttribute("genders", NhanVien.GioiTinh.values());
            model.addAttribute("contractTypes", NhanVien.LoaiHopDong.values());
            model.addAttribute("pageTitle", "Thêm Nhân Viên Mới");
            return "nhanvien/add";
        }

        try {
            nhanVienService.saveNhanVien(nhanVien);
            redirectAttributes.addFlashAttribute("success", "Thêm nhân viên thành công!");
            return "redirect:/nhanvien";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/nhanvien/add";
        }
    }

    @GetMapping("/view/{id}")
    public String viewNhanVien(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<NhanVien> nhanVienOpt = nhanVienService.getNhanVienById(id);

        if (nhanVienOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhân viên!");
            return "redirect:/nhanvien";
        }

        model.addAttribute("nhanVien", nhanVienOpt.get());
        model.addAttribute("pageTitle", "Chi Tiết Nhân Viên");
        return "nhanvien/view";
    }

    @GetMapping("/edit/{id}")
    public String editNhanVienForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<NhanVien> nhanVienOpt = nhanVienService.getNhanVienById(id);

        if (nhanVienOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhân viên!");
            return "redirect:/nhanvien";
        }

        model.addAttribute("nhanVien", nhanVienOpt.get());
        model.addAttribute("departments", NhanVien.PhongBan.values());
        model.addAttribute("positions", NhanVien.ChucVu.values());
        model.addAttribute("genders", NhanVien.GioiTinh.values());
        model.addAttribute("contractTypes", NhanVien.LoaiHopDong.values());
        model.addAttribute("pageTitle", "Chỉnh Sửa Nhân Viên");
        return "nhanvien/edit";
    }

    @PostMapping("/edit/{id}")
    public String editNhanVien(@PathVariable Long id,
                              @Valid @ModelAttribute NhanVien nhanVien,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (result.hasErrors()) {
            model.addAttribute("departments", NhanVien.PhongBan.values());
            model.addAttribute("positions", NhanVien.ChucVu.values());
            model.addAttribute("genders", NhanVien.GioiTinh.values());
            model.addAttribute("contractTypes", NhanVien.LoaiHopDong.values());
            model.addAttribute("pageTitle", "Chỉnh Sửa Nhân Viên");
            return "nhanvien/edit";
        }

        try {
            nhanVien.setId(id);
            nhanVienService.saveNhanVien(nhanVien);
            redirectAttributes.addFlashAttribute("success", "Cập nhật nhân viên thành công!");
            return "redirect:/nhanvien";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/nhanvien/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteNhanVien(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            nhanVienService.deleteNhanVien(id);
            redirectAttributes.addFlashAttribute("success", "Xóa nhân viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa nhân viên: " + e.getMessage());
        }
        return "redirect:/nhanvien";
    }

    @PostMapping("/update-performance/{id}")
    public String updatePerformance(@PathVariable Long id,
                                   @RequestParam Double doanhSoThang,
                                   @RequestParam Integer soNgayLamViec,
                                   RedirectAttributes redirectAttributes) {
        try {
            Optional<NhanVien> nhanVienOpt = nhanVienService.getNhanVienById(id);
            if (nhanVienOpt.isPresent()) {
                NhanVien nhanVien = nhanVienOpt.get();
                nhanVien.setDoanhSoThang(doanhSoThang);
                nhanVien.setSoNgayLamViecThang(soNgayLamViec);
                nhanVien.capNhatHieuSuat();
                nhanVienService.saveNhanVien(nhanVien);
                redirectAttributes.addFlashAttribute("success", "Cập nhật hiệu suất thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhân viên!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/nhanvien";
    }

    @PostMapping("/promote/{id}")
    public String promoteEmployee(@PathVariable Long id,
                                 @RequestParam NhanVien.ChucVu newPosition,
                                 @RequestParam Double newSalary,
                                 RedirectAttributes redirectAttributes) {
        try {
            Optional<NhanVien> nhanVienOpt = nhanVienService.getNhanVienById(id);
            if (nhanVienOpt.isPresent()) {
                NhanVien nhanVien = nhanVienOpt.get();

                if (nhanVien.coDieuKienThangChuc()) {
                    nhanVien.setChucVu(newPosition);
                    nhanVien.setLuongCoBan(newSalary);
                    nhanVienService.saveNhanVien(nhanVien);
                    redirectAttributes.addFlashAttribute("success", "Thăng chức nhân viên thành công!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Nhân viên chưa đủ điều kiện thăng chức!");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhân viên!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/nhanvien";
    }

    // API endpoints for HR analytics
    @GetMapping("/api/department-stats")
    @ResponseBody
    @SuppressWarnings("unused") // ẩn cảnh báo các field của anonymous object không được dùng trực tiếp
    public Object getDepartmentStatistics() {
        return new Object() {
            public final Long activeEmployees = nhanVienService.getActiveEmployeeCount();
            public final Double averageSalary = nhanVienService.getAverageSalary();
            public final Double totalSalesRevenue = nhanVienService.getTotalSalesRevenue();
        };
    }

    @GetMapping("/api/performance-ranking")
    @ResponseBody
    public Object getPerformanceRanking() {
        return nhanVienService.getTopPerformers();
    }
}

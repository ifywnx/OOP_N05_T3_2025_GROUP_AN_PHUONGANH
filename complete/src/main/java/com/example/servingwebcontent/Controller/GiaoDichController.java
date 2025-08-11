package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.GiaoDich;
import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.service.GiaoDichService;
import com.example.servingwebcontent.service.SanPhamService;
import com.example.servingwebcontent.service.discount.DiscountPolicy;
import com.example.servingwebcontent.service.discount.NoDiscount;
import com.example.servingwebcontent.service.discount.SeasonalDiscount;
import com.example.servingwebcontent.service.discount.VipDiscount;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/giaodich")
public class GiaoDichController {

    private final GiaoDichService giaoDichService;
    private final SanPhamService sanPhamService;

    public GiaoDichController(GiaoDichService giaoDichService, SanPhamService sanPhamService) {
        this.giaoDichService = giaoDichService;
        this.sanPhamService = sanPhamService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("dsGiaoDich", giaoDichService.findAll());
        return "giaodich/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("giaoDich", new GiaoDich());
        model.addAttribute("dsSanPham", sanPhamService.findAll());
        return "giaodich/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("giaoDich") GiaoDich gd,
                       BindingResult br,
                       @RequestParam(name = "sanPhamIds", required = false) Long[] sanPhamIds,
                       @RequestParam(name = "soLuongs",   required = false) Integer[] soLuongs,
                       @RequestParam(name = "donGias",    required = false) Double[] donGias,
                       @RequestParam(name = "policy",     required = false, defaultValue = "NONE") String policyName,
                       Model model,
                       RedirectAttributes ra) {

        if (br.hasErrors()) {
            model.addAttribute("dsSanPham", sanPhamService.findAll());
            return "giaodich/form";
        }

        // build line items
        List<GiaoDichService.Item> items = new ArrayList<>();
        if (sanPhamIds == null || soLuongs == null || donGias == null ||
            sanPhamIds.length == 0 || soLuongs.length == 0 || donGias.length == 0 ||
            !(sanPhamIds.length == soLuongs.length && soLuongs.length == donGias.length)) {
            model.addAttribute("dsSanPham", sanPhamService.findAll());
            model.addAttribute("lineItemError", "Vui lòng nhập sản phẩm, số lượng và đơn giá hợp lệ.");
            return "giaodich/form";
        }

        for (int i = 0; i < sanPhamIds.length; i++) {
            Long spId = sanPhamIds[i];
            Integer sl = soLuongs[i] == null ? 0 : soLuongs[i];
            Double dg = donGias[i] == null ? 0.0 : donGias[i];
            if (spId == null || sl <= 0 || dg < 0) continue;

            SanPham sp = sanPhamService.findById(spId);
            if (sp == null) {
                model.addAttribute("dsSanPham", sanPhamService.findAll());
                model.addAttribute("lineItemError", "Sản phẩm không tồn tại (ID: " + spId + ").");
                return "giaodich/form";
            }
            items.add(new GiaoDichService.Item(sp, sl, dg));
        }

        if (items.isEmpty()) {
            model.addAttribute("dsSanPham", sanPhamService.findAll());
            model.addAttribute("lineItemError", "Vui lòng chọn ít nhất 1 dòng với số lượng > 0 và đơn giá >= 0.");
            return "giaodich/form";
        }

        DiscountPolicy policy = switch (policyName.toUpperCase()) {
            case "VIP" -> new VipDiscount();
            case "SEASONAL" -> new SeasonalDiscount();
            default -> new NoDiscount();
        };

        try {
            giaoDichService.taoGiaoDich(gd, items, policy);
            ra.addFlashAttribute("success", "Tạo giao dịch thành công");
            return "redirect:/giaodich";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("dsSanPham", sanPhamService.findAll());
            model.addAttribute("lineItemError", ex.getMessage());
            return "giaodich/form";
        } catch (Exception ex) {
            model.addAttribute("dsSanPham", sanPhamService.findAll());
            model.addAttribute("lineItemError", "Có lỗi khi lưu giao dịch: " + ex.getMessage());
            return "giaodich/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        GiaoDich gd = giaoDichService.findById(id);
        if (gd == null) {
            ra.addFlashAttribute("error", "Không tìm thấy giao dịch");
            return "redirect:/giaodich";
        }
        model.addAttribute("giaoDich", gd);
        model.addAttribute("dsSanPham", sanPhamService.findAll());
        return "giaodich/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            giaoDichService.deleteById(id);
            ra.addFlashAttribute("success", "Xóa giao dịch thành công");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Xóa thất bại: " + e.getMessage());
        }
        return "redirect:/giaodich";
    }
}

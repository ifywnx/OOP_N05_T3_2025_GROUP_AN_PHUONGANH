package com.cakeshop.app.controller;

import com.cakeshop.app.model.GiaoDich;
import com.cakeshop.app.model.KhachHang;
import com.cakeshop.app.model.NhanVien;
import com.cakeshop.app.model.SanPham;
import com.cakeshop.app.repo.GiaoDichRepo;
import com.cakeshop.app.repo.KhachHangRepo;
import com.cakeshop.app.repo.NhanVienRepo;
import com.cakeshop.app.repo.SanPhamRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/giao-dich")
public class GiaoDichController {

    private final GiaoDichRepo gdRepo;
    private final KhachHangRepo khRepo;
    private final NhanVienRepo nvRepo;
    private final SanPhamRepo spRepo;

    public GiaoDichController(GiaoDichRepo gdRepo, KhachHangRepo khRepo,
                              NhanVienRepo nvRepo, SanPhamRepo spRepo) {
        this.gdRepo = gdRepo;
        this.khRepo = khRepo;
        this.nvRepo = nvRepo;
        this.spRepo = spRepo;
    }

    // LIST + filter theo ngày: /giao-dich?ngay=YYYY-MM-DD
    @GetMapping
    public String list(@RequestParam(value="ngay", required=false) String ngay, Model model) {
        if (ngay == null || ngay.isBlank()) {
            model.addAttribute("items", gdRepo.findAll());
        } else {
            model.addAttribute("items", gdRepo.findByNgay(ngay));
        }
        model.addAttribute("ngay", ngay);
        return "giaodich-list";
    }

    // Xem chi tiet
    @GetMapping("/{ma}")
    public String detail(@PathVariable String ma, Model model) {
        GiaoDich gd = gdRepo.findById(ma);
        if (gd == null) return "redirect:/giao-dich";
        model.addAttribute("gd", gd);
        model.addAttribute("kh", khRepo.findById(gd.getMaKhachHang()));
        model.addAttribute("nv", nvRepo.findById(gd.getMaNhanVien()));
        return "giaodich-detail";
    }

    // Form tao giao dich
    @GetMapping("/tao")
    public String createForm(Model model) {
        model.addAttribute("dsKh", khRepo.findAll());
        model.addAttribute("dsNv", nvRepo.findAll());
        model.addAttribute("dsSp", spRepo.findAll());
        // form trống
        return "giaodich-form";
    }

    // Submit form tao giao dich
    @PostMapping("/tao")
    public String create(
            @RequestParam String maGiaoDich,
            @RequestParam String ngay,
            @RequestParam String gio,
            @RequestParam String maKhachHang,
            @RequestParam String maNhanVien,
            @RequestParam(name = "maSp") List<String> maSpList,
            @RequestParam(name = "soLuong") List<Integer> soLuongList,
            Model model
    ) {
        // kiểm tra đầu vào tối thiểu
        if (gdRepo.exists(maGiaoDich)) {
            model.addAttribute("error", "Ma giao dich da ton tai");
            return reloadForm(model);
        }
        if (khRepo.findById(maKhachHang) == null) {
            model.addAttribute("error", "Khach hang khong ton tai");
            return reloadForm(model);
        }
        if (nvRepo.findById(maNhanVien) == null) {
            model.addAttribute("error", "Nhan vien khong ton tai");
            return reloadForm(model);
        }
        // build gio hang & tru kho
        Map<String,Integer> gioHang = new LinkedHashMap<>();
        double tongTien = 0.0;

        for (int i = 0; i < maSpList.size(); i++) {
            String maSp = maSpList.get(i);
            int slMua = soLuongList.get(i);
            if (slMua <= 0) continue;

            SanPham sp = spRepo.findById(maSp);
            if (sp == null) {
                model.addAttribute("error", "San pham " + maSp + " khong ton tai");
                return reloadForm(model);
            }
            if (sp.getSoLuong() < slMua) {
                model.addAttribute("error", "San pham " + maSp + " khong du hang");
                return reloadForm(model);
            }
            // trừ kho
            sp.setSoLuong(sp.getSoLuong() - slMua);
            spRepo.save(sp);

            gioHang.put(maSp, slMua);

            // nếu có đơn giá, bạn tính ở đây; tạm thời demo: 10k/sp
            tongTien += slMua * 10000;
        }

        GiaoDich gd = new GiaoDich(maGiaoDich, ngay, gio, maKhachHang, maNhanVien, gioHang, tongTien);
        gdRepo.save(gd);

        return "redirect:/giao-dich/" + maGiaoDich;
    }

    // helper nạp lại form với dropdown
    private String reloadForm(Model model) {
        model.addAttribute("dsKh", khRepo.findAll());
        model.addAttribute("dsNv", nvRepo.findAll());
        model.addAttribute("dsSp", spRepo.findAll());
        return "giaodich-form";
    }
}

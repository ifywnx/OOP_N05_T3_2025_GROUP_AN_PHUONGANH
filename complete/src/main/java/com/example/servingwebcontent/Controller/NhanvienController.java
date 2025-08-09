package com.example.demo.controller;

import com.example.demo.model.Nhanvien;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class NhanvienController {

    @GetMapping("/nhanvien")
    public String hienThiNhanVien(Model model) {
        Nhanvien nv1 = new Nhanvien("NV01", "Nguyễn Văn An", "Quảng Ninh", "0901234567");
        Nhanvien nv2 = new Nhanvien("NV02", "Lê Thị Phương Anh", "Hải Phòng", "0987654321");

        ArrayList<Nhanvien> danhSach = new ArrayList<>();
        danhSach.add(nv1);
        danhSach.add(nv2);

        model.addAttribute("listNV", danhSach);

        return "nhanvien"; // Trả về nhanvien.html
    }
}

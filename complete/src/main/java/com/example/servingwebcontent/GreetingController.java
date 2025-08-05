package com.example.servingwebcontent;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@GetMapping("/nhanvien")
	public String myNhanvien(Model model) {

		
		Nhanvien nv1 = new Nhanvien("NV01", "Nguyễn Văn An");
        Nhanvien nv2 = new Nhanvien("NV02", "Lê Thị Phương Anh");

        ArrayList<Nhanvien> danhSach = new ArrayList<>();
        danhSach.add(nv1);
        danhSach.add(nv2);

		System.out.println(danhSach.get(0).getTenNhanVien());
		
		model.addAttribute("ds", danhSach);
		return "nhanvien";
	}

}

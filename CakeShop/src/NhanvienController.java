@Controller
public class NhanvienController {

    @GetMapping("/nhanvien")
    public String hienThiDanhSach(Model model) {
        ArrayList<Nhanvien> dsNhanVien = new ArrayList<>();
        dsNhanVien.add(new Nhanvien("NV01", "Nguyen Van A"));
        dsNhanVien.add(new Nhanvien("NV02", "Tran Thi B"));

        model.addAttribute("danhSachNhanVien", dsNhanVien);

        return "trangchinh";
    }
}

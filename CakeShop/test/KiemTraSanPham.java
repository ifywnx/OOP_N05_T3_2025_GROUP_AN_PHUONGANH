import java.util.ArrayList;

public class KiemTraSanPham {
    public static void test() {
        ArrayList<Sanpham> danhSach = new ArrayList<>();

        // Tạo sản phẩm và dùng setter
        Sanpham sp1 = new Sanpham();
        sp1.setMaSanPham("SP01");
        sp1.setTenSanPham("Bánh kem");
        sp1.setSoLuong(10);
        sp1.setNhaSanXuat("ABC");
        sp1.setHanSuDung("25/07/2025");
        sp1.setNguyenLieu("Bột, sữa, trứng");

        Sanpham sp2 = new Sanpham();
        sp2.setMaSanPham("SP02");
        sp2.setTenSanPham("Bánh mì");
        sp2.setSoLuong(0); // hết hàng
        sp2.setNhaSanXuat("XYZ");
        sp2.setHanSuDung("30/07/2025");
        sp2.setNguyenLieu("Bột mì, muối, men");

        danhSach.add(sp1);
        danhSach.add(sp2);

        // Gọi phương thức kiểm tra còn hàng
        QuanLySanPham ql = new QuanLySanPham();
        ql.hienThiSanPhamConHang(danhSach);
    }
}
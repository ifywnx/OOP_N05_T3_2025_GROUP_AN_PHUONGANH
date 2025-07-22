import java.util.ArrayList;

public class KiemTraSanPham {
    public static void test() {
        ArrayList<Sanpham> danhSach = new ArrayList<>();

        Sanpham sp1 = new Sanpham();
        sp1.setMaSanPham("SP01");
        sp1.setTenSanPham("Bánh kem");
        sp1.setSoLuong(10);
        sp1.setNhaSanXuat("Tràng An");
        sp1.setHanSuDung("30/07/2025");
        sp1.setNguyenLieu("Bột, sữa, trứng");

        Sanpham sp2 = new Sanpham();
        sp2.setMaSanPham("SP02");
        sp2.setTenSanPham("Bánh mì");
        sp2.setSoLuong(0); 
        sp2.setNhaSanXuat("XYZ");
        sp2.setHanSuDung("30/1/2025");
        sp2.setNguyenLieu("Bột mì, muối, men");

        Sanpham sp3 = new Sanpham();
        sp3.setMaSanPham("SP02");
        sp3.setTenSanPham("Bánh su kem ");
        sp3.setSoLuong(20); 
        sp3.setNhaSanXuat("Thu Hường Cake");
        sp3.setHanSuDung("30/08/2025");
        sp3.setNguyenLieu("bột mì, bơ, trứng, nước và một chút muối");

        Sanpham sp4 = new Sanpham();
        sp4.setMaSanPham("SP02");
        sp4.setTenSanPham("Bánh sinh nhật ");
        sp4.setSoLuong(15); 
        sp4.setNhaSanXuat("Thảo Ngoan Cake");
        sp4.setHanSuDung("30/07/2025");
        sp4.setNguyenLieu("Bánh bông lan và kem");

        Sanpham sp5 = new Sanpham();
        sp5.setMaSanPham("SP02");
        sp5.setTenSanPham("Bánh bông lan ");
        sp5.setSoLuong(30); 
        sp5.setNhaSanXuat("Hura");
        sp5.setHanSuDung("15/08/2025");
        sp5.setNguyenLieu("bột mì, đường, trứng, sữa, bơ hoặc dầu ăn");


        danhSach.add(sp1);
        danhSach.add(sp2);
        danhSach.add(sp3);
        danhSach.add(sp4);
        danhSach.add(sp5);

        QuanLySanPham ql = new QuanLySanPham();
        ql.hienThiSanPhamConHang(danhSach);
    }
}
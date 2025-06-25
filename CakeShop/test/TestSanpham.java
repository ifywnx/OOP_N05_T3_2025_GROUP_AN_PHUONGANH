public class TestSanpham {

    public static void test() {
        Sanpham sp = new Sanpham();
        sp.setMaSanPham("SP01");
        sp.setTenSanPham("Bánh donut");
        sp.setSoLuong(100);
        sp.setNhaSanXuat("Công ty Nhàn Hương");
        sp.setHanSuDung("24/12/2025");
        sp.setNguyenLieu("Bột mì, đường, trứng");
        sp.getMaSanPham();
        sp.getTenSanPham();
        sp.getSoLuong();
        sp.getNhaSanXuat();
        sp.getHanSuDung();
        sp.getNguyenLieu();
    }

}

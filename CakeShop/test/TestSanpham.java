public class TestSanpham {

    public static void test() {

        System.out.println("\nCREATE");
        Sanpham sp = new Sanpham();
        sp.setMaSanPham("SP01");
        sp.setTenSanPham("Bánh Donut");
        sp.setSoLuong(100);
        sp.setNhaSanXuat("Công ty Nhàn Hương");
        sp.setHanSuDung("24/12/2025");
        sp.setNguyenLieu("Bột mì, đường, trứng");
        System.out.println("Đã tạo sản phẩm.");

        System.out.println("\nREAD");
        sp.getMaSanPham();
        sp.getTenSanPham();
        sp.getSoLuong();
        sp.getNhaSanXuat();
        sp.getHanSuDung();
        sp.getNguyenLieu();

        System.out.println("\nUPDATE");
        sp.setSoLuong(120); // sửa số lượng còn lại
        System.out.println("Đã cập nhật số lượng mới:");
        sp.getSoLuong();

        System.out.println("\nDELETE");
        sp = null;
        System.out.println("Đã xoá sản phẩm (gán null).");
    }
}

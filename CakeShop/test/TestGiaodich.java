public class TestGiaodich {

    public static void test() {
        Giaodich gd = new Giaodich();
        //Create
        gd.setMaGiaoDich("GD01");
        gd.setNgayThangNamGiaoDich("25/06/2025");
        gd.setGioGiaoDich("14:30");
        gd.setTongTien(2000000.0);
        //Read
        gd.getMaGiaoDich();
        gd.getNgayThangNamGiaoDich();
        gd.getGioGiaoDich();
        gd.getTongTien();
        //Update
        gd.setTongTien(2500000.0);
        System.out.println("Cap nhat tong tien:");
        gd.getTongTien();
        //Delete
        gd = null;
        System.out.println("Xoa giao dich:");

    }
}

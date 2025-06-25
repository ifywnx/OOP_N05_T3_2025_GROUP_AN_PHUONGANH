public class TestGiaodich {

    public static void test() {
        Giaodich gd = new Giaodich();
        gd.setMaGiaoDich("GD01");
        gd.setNgayThangNamGiaoDich("25/06/2025");
        gd.setGioGiaoDich("14:30");
        gd.setTongTien(2000000.0);
        gd.getMaGiaoDich();
        gd.getNgayThangNamGiaoDich();
        gd.getGioGiaoDich();
        gd.getTongTien();
    }
}

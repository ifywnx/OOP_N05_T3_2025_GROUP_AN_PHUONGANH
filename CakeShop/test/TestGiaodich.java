public class TestGiaodich {

    public static void test() {
        // Tạo đối tượng giao dịch mới (CREATE)
        Giaodich gd = new Giaodich();
        gd.setMaGiaoDich("GD01");
        gd.setNgayThangNamGiaoDich("25/06/2025");
        gd.setGioGiaoDich("14:30");
        gd.setTongTien(2000000.0);

        // Hiển thị thông tin giao dịch (READ)
        System.out.print("Mã giao dịch: ");
        gd.getMaGiaoDich();
        System.out.print("Ngày giao dịch: ");
        gd.getNgayThangNamGiaoDich();
        System.out.print("Giờ giao dịch: ");
        gd.getGioGiaoDich();
        System.out.print("Tổng tiền: ");
        gd.getTongTien();
        System.out.println();

        // Cập nhật tổng tiền (UPDATE)
        gd.setTongTien(2500000.0);
        System.out.println("Thông tin giao dịch sau khi cập nhật tổng tiền");
        System.out.print("Tổng tiền mới: ");
        gd.getTongTien();
        System.out.println();

        // Xóa giao dịch (DELETE)
        gd = null;
        System.out.println("Giao dịch đã bị xóa (đặt biến về null).");
    }
}

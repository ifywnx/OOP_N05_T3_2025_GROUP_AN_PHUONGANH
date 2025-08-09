import java.util.ArrayList;
import java.util.List;

public class TestGiaoDich {

    private static final List<GiaoDich> ds = new ArrayList<>();

    // CREATE
    public static void create(GiaoDich gd) {
        if (gd == null || empty(gd.getMaGiaoDich())) {
            System.out.println("CREATE GD thất bại: dữ liệu không hợp lệ"); return;
        }
        if (findById(gd.getMaGiaoDich()) != null) {
            System.out.println("CREATE GD thất bại: mã đã tồn tại"); return;
        }
        if (gd.getTongTien() < 0) {
            System.out.println("CREATE GD thất bại: tổng tiền âm"); return;
        }
        ds.add(gd);
        System.out.println("Đã thêm GD: " + gd.getMaGiaoDich());
    }

    // READ
    public static GiaoDich findById(String ma) {
        if (empty(ma)) return null;
        for (GiaoDich gd : ds) if (gd.getMaGiaoDich().equals(ma)) return gd;
        return null;
    }

    public static void listAll() {
        System.out.println("\n=== DANH SÁCH GIAO DỊCH ===");
        if (ds.isEmpty()) { System.out.println("(trống)"); return; }
        for (GiaoDich gd : ds) { gd.hienThi(); System.out.println("-----------------"); }
    }

    // UPDATE
    public static void update(GiaoDich gdMoi) {
        if (gdMoi == null || empty(gdMoi.getMaGiaoDich())) {
            System.out.println("UPDATE GD thất bại: dữ liệu không hợp lệ"); return;
        }
        GiaoDich cu = findById(gdMoi.getMaGiaoDich());
        if (cu == null) { System.out.println("UPDATE GD thất bại: không tìm thấy " + gdMoi.getMaGiaoDich()); return; }
        if (gdMoi.getTongTien() < 0) { System.out.println("UPDATE GD thất bại: tổng tiền âm"); return; }

        cu.setNgayGiaoDich(gdMoi.getNgayGiaoDich());
        cu.setGioGiaoDich(gdMoi.getGioGiaoDich());
        cu.setTongTien(gdMoi.getTongTien());
        System.out.println("Đã cập nhật GD: " + gdMoi.getMaGiaoDich());
    }

    // DELETE
    public static void delete(String ma) {
        GiaoDich gd = findById(ma);
        if (gd == null) { System.out.println("DELETE GD thất bại: không tìm thấy " + ma); return; }
        ds.remove(gd);
        System.out.println("Đã xóa GD: " + ma);
    }

    // DEMO
    public static void test() {
        GiaoDich g1 = new GiaoDich();
        g1.setMaGiaoDich("GD001");
        g1.setNgayGiaoDich("2025-08-09");
        g1.setGioGiaoDich("10:30");
        g1.setTongTien(150000);

        GiaoDich g2 = new GiaoDich();
        g2.setMaGiaoDich("GD002");
        g2.setNgayGiaoDich("2025-08-09");
        g2.setGioGiaoDich("14:00");
        g2.setTongTien(250000);

        create(g1);
        create(g2);
        listAll();

        GiaoDich g2Moi = new GiaoDich();
        g2Moi.setMaGiaoDich("GD002");
        g2Moi.setNgayGiaoDich("2025-08-10");
        g2Moi.setGioGiaoDich("09:00");
        g2Moi.setTongTien(90000);
        update(g2Moi);

        listAll();

        delete("GD001");
        listAll();
    }

    private static boolean empty(String s) { return s == null || s.isEmpty(); }
}

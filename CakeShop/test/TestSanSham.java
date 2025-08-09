import java.util.ArrayList;
import java.util.List;

public class TestSanPham {

    private static final List<SanPham> ds = new ArrayList<>();

    // CREATE
    public static void create(SanPham sp) {
        if (sp == null || empty(sp.getMaSanPham())) {
            System.out.println("CREATE SP thất bại: dữ liệu không hợp lệ"); return;
        }
        if (findById(sp.getMaSanPham()) != null) {
            System.out.println("CREATE SP thất bại: mã đã tồn tại"); return;
        }
        if (sp.getSoLuong() < 0) {
            System.out.println("CREATE SP thất bại: số lượng âm"); return;
        }
        ds.add(sp);
        System.out.println("Đã thêm SP: " + sp.getMaSanPham());
    }

    // READ
    public static SanPham findById(String ma) {
        if (empty(ma)) return null;
        for (SanPham sp : ds) if (sp.getMaSanPham().equals(ma)) return sp;
        return null;
    }

    public static void listAll() {
        System.out.println("\n=== DANH SÁCH SẢN PHẨM ===");
        if (ds.isEmpty()) { System.out.println("(trống)"); return; }
        for (SanPham sp : ds) { sp.hienThi(); System.out.println("-----------------"); }
    }

    // UPDATE
    public static void update(SanPham spMoi) {
        if (spMoi == null || empty(spMoi.getMaSanPham())) {
            System.out.println("UPDATE SP thất bại: dữ liệu không hợp lệ"); return;
        }
        SanPham cu = findById(spMoi.getMaSanPham());
        if (cu == null) { System.out.println("UPDATE SP thất bại: không tìm thấy " + spMoi.getMaSanPham()); return; }
        if (spMoi.getSoLuong() < 0) { System.out.println("UPDATE SP thất bại: số lượng âm"); return; }

        cu.setTenSanPham(spMoi.getTenSanPham());
        cu.setSoLuong(spMoi.getSoLuong());
        cu.setNhaSanXuat(spMoi.getNhaSanXuat());
        cu.setHanSuDung(spMoi.getHanSuDung());
        cu.setNguyenLieu(spMoi.getNguyenLieu());
        System.out.println("Đã cập nhật SP: " + spMoi.getMaSanPham());
    }

    // DELETE
    public static void delete(String ma) {
        SanPham sp = findById(ma);
        if (sp == null) { System.out.println("DELETE SP thất bại: không tìm thấy " + ma); return; }
        ds.remove(sp);
        System.out.println("Đã xóa SP: " + ma);
    }

    // DEMO
    public static void test() {
        SanPham a = new SanPham();
        a.setMaSanPham("SP001");
        a.setTenSanPham("Bánh mì");
        a.setSoLuong(10);
        a.setNhaSanXuat("Tiệm ABC");
        a.setHanSuDung("2025-12-31");
        a.setNguyenLieu("Bột mì, men, đường");

        SanPham b = new SanPham();
        b.setMaSanPham("SP002");
        b.setTenSanPham("Bánh kem");
        b.setSoLuong(3);
        b.setNhaSanXuat("Tiệm XYZ");
        b.setHanSuDung("2025-08-20");
        b.setNguyenLieu("Bột, trứng, kem");

        create(a);
        create(b);
        listAll();

        SanPham bMoi = new SanPham();
        bMoi.setMaSanPham("SP002");
        bMoi.setTenSanPham("Bánh kem dâu");
        bMoi.setSoLuong(5);
        bMoi.setNhaSanXuat("Tiệm XYZ");
        bMoi.setHanSuDung("2025-09-01");
        bMoi.setNguyenLieu("Bột, trứng, kem, dâu");
        update(bMoi);

        listAll();

        delete("SP001");
        listAll();
    }

    private static boolean empty(String s) { return s == null || s.isEmpty(); }
}

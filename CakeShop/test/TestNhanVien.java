import java.util.ArrayList;
import java.util.List;

public class TestNhanVien {

    private static final List<NhanVien> ds = new ArrayList<>();

    // CREATE
    public static void create(NhanVien nv) {
        if (nv == null || empty(nv.getMaNhanVien())) {
            System.out.println("CREATE NV thất bại: dữ liệu không hợp lệ"); return;
        }
        if (findById(nv.getMaNhanVien()) != null) {
            System.out.println("CREATE NV thất bại: mã đã tồn tại"); return;
        }
        if (nv.getTienLuong() < 0) {
            System.out.println("CREATE NV thất bại: lương âm"); return;
        }
        ds.add(nv);
        System.out.println("Đã thêm NV: " + nv.getMaNhanVien());
    }

    // READ
    public static NhanVien findById(String ma) {
        if (empty(ma)) return null;
        for (NhanVien nv : ds) if (nv.getMaNhanVien().equals(ma)) return nv;
        return null;
    }

    public static void listAll() {
        System.out.println("\n=== DANH SÁCH NHÂN VIÊN ===");
        if (ds.isEmpty()) { System.out.println("(trống)"); return; }
        for (NhanVien nv : ds) { nv.hienThi(); System.out.println("-----------------"); }
    }

    // UPDATE
    public static void update(NhanVien nvMoi) {
        if (nvMoi == null || empty(nvMoi.getMaNhanVien())) {
            System.out.println("UPDATE NV thất bại: dữ liệu không hợp lệ"); return;
        }
        NhanVien cu = findById(nvMoi.getMaNhanVien());
        if (cu == null) { System.out.println("UPDATE NV thất bại: không tìm thấy " + nvMoi.getMaNhanVien()); return; }
        if (nvMoi.getTienLuong() < 0) { System.out.println("UPDATE NV thất bại: lương âm"); return; }

        cu.setTenNhanVien(nvMoi.getTenNhanVien());
        cu.setDiaChi(nvMoi.getDiaChi());
        cu.setSoDienThoai(nvMoi.getSoDienThoai());
        cu.setTienLuong(nvMoi.getTienLuong());
        System.out.println("Đã cập nhật NV: " + nvMoi.getMaNhanVien());
    }

    // DELETE
    public static void delete(String ma) {
        NhanVien nv = findById(ma);
        if (nv == null) { System.out.println("DELETE NV thất bại: không tìm thấy " + ma); return; }
        ds.remove(nv);
        System.out.println("Đã xóa NV: " + ma);
    }

    // DEMO
    public static void test() {
        NhanVien a = new NhanVien();
        a.setMaNhanVien("NV001");
        a.setTenNhanVien("Nguyễn Văn C");
        a.setDiaChi("Hà Nội");
        a.setSoDienThoai("0111111111");
        a.setTienLuong(8_000_000);

        NhanVien b = new NhanVien();
        b.setMaNhanVien("NV002");
        b.setTenNhanVien("Lê Thị D");
        b.setDiaChi("TP.HCM");
        b.setSoDienThoai("0222222222");
        b.setTienLuong(9_000_000);

        create(a);
        create(b);
        listAll();

        NhanVien bMoi = new NhanVien();
        bMoi.setMaNhanVien("NV002");
        bMoi.setTenNhanVien("Lê Thị Diệu");
        bMoi.setDiaChi("Quận 1");
        bMoi.setSoDienThoai("0333333333");
        bMoi.setTienLuong(9_500_000);
        update(bMoi);

        listAll();

        delete("NV001");
        listAll();
    }

    private static boolean empty(String s) { return s == null || s.isEmpty(); }
}

import java.util.ArrayList;
import java.util.List;

public class TestKhacHang {

    private static final List<KhacHang> ds = new ArrayList<>();

    // CREATE
    public static void create(KhacHang kh) {
        if (kh == null || empty(kh.getMaKhacHang())) {
            System.out.println("CREATE KH thất bại: dữ liệu không hợp lệ");
            return;
        }
        if (findById(kh.getMaKhacHang()) != null) {
            System.out.println("CREATE KH thất bại: mã đã tồn tại");
            return;
        }
        ds.add(kh);
        System.out.println("Đã thêm KH: " + kh.getMaKhacHang());
    }

    // READ
    public static KhacHang findById(String ma) {
        if (empty(ma)) return null;
        for (KhacHang kh : ds) if (kh.getMaKhacHang().equals(ma)) return kh;
        return null;
    }

    public static void listAll() {
        System.out.println("\n=== DANH SÁCH KHÁCH HÀNG ===");
        if (ds.isEmpty()) { System.out.println("(trống)"); return; }
        for (KhacHang kh : ds) { kh.hienThi(); System.out.println("-----------------"); }
    }

    // UPDATE
    public static void update(KhacHang khMoi) {
        if (khMoi == null || empty(khMoi.getMaKhacHang())) {
            System.out.println("UPDATE KH thất bại: dữ liệu không hợp lệ"); return;
        }
        KhacHang cu = findById(khMoi.getMaKhacHang());
        if (cu == null) {
            System.out.println("UPDATE KH thất bại: không tìm thấy " + khMoi.getMaKhacHang()); return;
        }
        cu.setTenKhacHang(khMoi.getTenKhacHang());
        cu.setDiaChi(khMoi.getDiaChi());
        cu.setSoDienThoai(khMoi.getSoDienThoai());
        System.out.println("Đã cập nhật KH: " + khMoi.getMaKhacHang());
    }

    // DELETE
    public static void delete(String ma) {
        KhacHang kh = findById(ma);
        if (kh == null) { System.out.println("DELETE KH thất bại: không tìm thấy " + ma); return; }
        ds.remove(kh);
        System.out.println("Đã xóa KH: " + ma);
    }

    // DEMO
    public static void test() {
        KhacHang a = new KhacHang();
        a.setMaKhacHang("KH001");
        a.setTenKhacHang("Nguyễn Văn A");
        a.setDiaChi("Hà Nội");
        a.setSoDienThoai("0123456789");

        KhacHang b = new KhacHang();
        b.setMaKhacHang("KH002");
        b.setTenKhacHang("Trần Thị B");
        b.setDiaChi("TP.HCM");
        b.setSoDienThoai("0987654321");

        create(a);
        create(b);
        create(a); // trùng mã -> báo lỗi

        listAll();

        KhacHang aMoi = new KhacHang();
        aMoi.setMaKhacHang("KH001");
        aMoi.setTenKhacHang("Nguyễn Văn An");
        aMoi.setDiaChi("Cầu Giấy - Hà Nội");
        aMoi.setSoDienThoai("0111111111");
        update(aMoi);

        listAll();

        delete("KH002");
        listAll();
    }

    private static boolean empty(String s) { return s == null || s.isEmpty(); }
}

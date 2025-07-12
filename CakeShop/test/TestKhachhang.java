public class TestKhachhang {

    public static void test() {

        System.out.println("\nCREATE");
        Khachhang kh = new Khachhang();
        kh.setMaKhachHang("KH01");
        kh.setTenKhachHang("Nguyễn Văn An");
        kh.setDiaChi("Quảng Ninh");
        kh.setSoDienThoai("093475497");
        System.out.println("Đã tạo khách hàng.");

        System.out.println("\nREAD");
        kh.getMaKhachHang();
        kh.getTenKhachHang();
        kh.getDiaChi();
        kh.getSoDienThoai();

        System.out.println("\nUPDATE");
        kh.setDiaChi("Hà Nội"); // ví dụ sửa địa chỉ
        System.out.println("Đã cập nhật địa chỉ mới:");
        kh.getDiaChi();

        System.out.println("\nDELETE");
        kh = null;
        System.out.println("Đã xoá khách hàng (gán null).");
    }
}

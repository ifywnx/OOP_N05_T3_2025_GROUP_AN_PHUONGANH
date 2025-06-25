public class TestNhanvien {

    public static void test() {
        Nhanvien nv = new Nhanvien();
        nv.setMaNhanVien("NV01");
        nv.setTenNhanVien("Lê Thị Phương Anh");
        nv.setDiaChi("Hải Dương");
        nv.setSoDienThoai("0546989833");
        nv.setTienLuong("15.000.000 VNĐ");
        nv.getMaNhanVien();
        nv.getTenNhanVien();
        nv.getDiaChi();
        nv.getSoDienThoai();
        nv.getTienLuong();
    }
}

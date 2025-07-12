public class TestNhanvien {

    public static void test() {
        Nhanvien nv = new Nhanvien();
        //Create
        nv.setMaNhanVien("NV01");
        nv.setTenNhanVien("Lê Thị Phương Anh");
        nv.setDiaChi("Hải Dương");
        nv.setSoDienThoai("0546989833");
        nv.setTienLuong("15000000 VNĐ");
        //Read
        nv.getMaNhanVien();
        nv.getTenNhanVien();
        nv.getDiaChi();
        nv.getSoDienThoai();
        nv.getTienLuong();
        //Update
        System.out.println("Update");
        nv.setTienLuong("20000000");
        System.out.println("Cap nhat Tien luong moi:");
        nv.getTienLuong();
        //Delete
        System.out.println("Delete");
        nv = null;
        System.out.println("Xoa nhan vien:");

    }
}

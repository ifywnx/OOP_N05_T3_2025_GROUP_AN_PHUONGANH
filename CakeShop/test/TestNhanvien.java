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
        System.out.println("Thông tin nhân viên");
        System.out.print("Mã nhân viên: ");
        nv.getMaNhanVien();
        System.out.print("Tên nhân viên: ");
        nv.getTenNhanVien();
        System.out.print("Địa chỉ: ");
        nv.getDiaChi();
        System.out.print("Số điện thoại: ");
        nv.getSoDienThoai();
        System.out.print("Tiền lương: ");
        nv.getTienLuong();
        System.out.println();
        //Update
        nv.setTienLuong("18,000,000 VND");
        System.out.println("Thông tin nhân viên sau khi cập nhật tiền lương");
        System.out.print("Tiền lương mới: ");
        nv.getTienLuong();
        System.out.println();
        //Delete
        System.out.println("Delete");
        nv = null;
        System.out.println("Xoa nhan vien:");

    }
}

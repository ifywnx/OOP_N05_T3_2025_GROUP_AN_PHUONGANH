public class KhachhangMain {
    
    public static void main(String[] args) {
        KhachhangModel model = new KhachhangModel();
        KhachhangView view = new KhachhangView();
        KhachhangController controller = new KhachhangController(model, view);

        controller.setMaKhachHang("KH01");
        controller.setTenKhachHang("Nguyễn Văn A");
        controller.setDiaChi("Hà Nội");
        controller.setSoDienThoai("0912345678");

        controller.hienThiThongTin();
    }
}



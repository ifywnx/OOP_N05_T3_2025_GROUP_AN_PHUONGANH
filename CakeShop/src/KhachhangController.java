public class KhachhangController {
    private KhachhangModel model;
    private KhachhangView view;

    public KhachhangController(KhachhangModel model, KhachhangView view) {
        this.model = model;
        this.view = view;
    }

    public void setMaKhachHang(String ma) {
        model.setMaKhachHang(ma);
    }

    public void setTenKhachHang(String ten) {
        model.setTenKhachHang(ten);
    }

    public void setDiaChi(String dc) {
        model.setDiaChi(dc);
    }

    public void setSoDienThoai(String sdt) {
        model.setSoDienThoai(sdt);
    }

    public void hienThiThongTin() {
        view.hienThi(model);
    }
}



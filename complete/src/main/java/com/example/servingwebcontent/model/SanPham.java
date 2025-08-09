public class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private int soLuong;
    private String nhaSanXuat;
    private String hanSuDung;
    private String nguyenLieu;

    public SanPham() {}

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(String nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    public void hienThi() {
        System.out.println("Mã SP: " + maSanPham);
        System.out.println("Tên SP: " + tenSanPham);
        System.out.println("Số lượng: " + soLuong);
        System.out.println("Nhà SX: " + nhaSanXuat);
        System.out.println("HSD: " + hanSuDung);
        System.out.println("Nguyên liệu: " + nguyenLieu);
    }
}
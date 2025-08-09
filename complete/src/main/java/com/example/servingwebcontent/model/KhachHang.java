public class KhachHang {
    private String tenKhachHang;
    private String maKhachHang;
    private String diaChi;
    private String soDienThoai;

    public KhachHang() {}

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public void hienThi() {
        System.out.println("Tên KH: " + tenKhachHang);
        System.out.println("Mã KH: " + maKhachHang);
        System.out.println("Địa chỉ: " + diaChi);
        System.out.println("SĐT: " + soDienThoai);
    }
}

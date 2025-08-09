public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private String diaChi;
    private String soDienThoai;
    private double tienLuong;

    public NhanVien() {}

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public double getTienLuong() {
        return tienLuong;
    }

    public void setTienLuong(double tienLuong) {
        this.tienLuong = tienLuong;
    }

    public void hienThi() {
        System.out.println("Mã NV: " + maNhanVien);
        System.out.println("Tên NV: " + tenNhanVien);
        System.out.println("Địa chỉ: " + diaChi);
        System.out.println("SĐT: " + soDienThoai);
        System.out.println("Lương: " + tienLuong);
    }
}

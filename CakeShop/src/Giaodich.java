public class GiaoDich {
    private String maGiaoDich;
    private String ngayGiaoDich;
    private String gioGiaoDich;
    private double tongTien;

    public GiaoDich() {}

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(String ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getGioGiaoDich() {
        return gioGiaoDich;
    }

    public void setGioGiaoDich(String gioGiaoDich) {
        this.gioGiaoDich = gioGiaoDich;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public void hienThi() {
        System.out.println("Mã GD: " + maGiaoDich);
        System.out.println("Ngày GD: " + ngayGiaoDich);
        System.out.println("Giờ GD: " + gioGiaoDich);
        System.out.println("Tổng tiền: " + tongTien);
    }
}

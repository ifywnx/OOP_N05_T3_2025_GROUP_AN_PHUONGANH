public class Giaodich {

    //bien mieu ta cho giao dich
    private String MaGiaoDich;
    String NgayThangNamGiaoDich;
    String GioGiaoDich;
    double TongTien;
    //contructor
    public Giaodich(){
    }

    //phuong thuc hoat dong
    public String setMaGiaoDich(String mgd){
        MaGiaoDich = mgd;
        return MaGiaoDich;
    }
    public void getMaGiaoDich(){
        System.out.println("Ma giao dich" + MaGiaoDich);
    }

    public String setNgayThangNamGiaoDich(String ntngd){
        NgayThangNamGiaoDich = ntngd;
        return NgayThangNamGiaoDich;
    }
    public void getNgayThangNamGiaoDich(){
        System.out.println("Ngay thang nam giao dich" + NgayThangNamGiaoDich);
    }

    public String setGioGiaoDich(String ggd){
        GioGiaoDich = ggd;
        return GioGiaoDich;
    }
    public void getGioGiaoDich(){
        System.out.println("Gio giao dich" + GioGiaoDich);
    }

    public double setTongTien(double tt){
        TongTien = tt;
        return TongTien;
    }
    public void getTongTien(){
        System.out.println("Tong tien" + TongTien);
    }
}

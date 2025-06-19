public class Giaodich {

    //bien mieu ta cho giao dich
    Private String MaGiaoDich;
    String NgayThangNamGiaoDich;
    String GioGiaoDich;
    double TongTien;


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
}
    
}

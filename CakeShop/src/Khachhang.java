public class Khachhang {

    // Biến miêu tả - Member variable
    private String MaKhachHang;
    private String TenKhachHang;
    private String DiaChi;
    private String SoDienThoai;

    // phuong thuc hoat dong - methods
    //shift-alt-F: định dang dữ liẹu 
//CakeShop/src
    public String setMaKhachHang(String mkh){
        MaKhachHang = mkh;
        return MaKhachHang;
    }
    public void getMaKhachHang(){
        System.out.println("Hien thi ma khach hang: " + MaKhachHang);
    }

    //Default constructor

    public Khachhang(){

    }
    public String setTenKhachHang(String tkh){
        TenKhachHang = tkh;
        return TenKhachHang;
    }
    public void getTenKhachHang(){
        System.out.println("Hien thi ten khach hang: " + TenKhachHang);
    }

    public String setDiaChi(String dc){
        DiaChi = dc;
        return DiaChi;
    }
    public void getDiaChi(){
        System.out.println("Dia chi: " + DiaChi);
    }

    public String setSoDienThoai(String sdt){
        SoDienThoai = sdt;
        return SoDienThoai;
    }

    public void getSoDienThoai(){
        System.out.println("So dien thoai: " + SoDienThoai);
    }
}
public class Khachhang {

    // Biến miêu tả - Member variable
    String MaKhachHang;
    String TenKhachHang;
    String DiaChi;
    String SoDienThoai;

    // phuong thuc hoat dong - methods
    //shift-alt-F: định dang dữ liẹu 
CakeShop/src
    public String setMaKhachHang(String ma){
        MaKhachHang = ma;

        return MaKhachHang;
    }

    public void getMaKhachHang(){
        System.out.println("Hien thi ma khach hang" + MaKhachHang);
    }

    //Default constructor

   public Khachhang(){

    }
    public class String setTenKhachHang(String name){
        TenKhachHang = name;

        return TenKhachHang;
    }

    public void getTenKhachHang(){
        System.out.println("Hien thi ten khach hang" + TenKhachHang);
    }

    private class String setDiaChi(String diachi){
        DiaChi = diachi;

        return DiaChi;
    }
    private void getDiaChi(){
        System.out.println("An dia chi" + DiaChi);
    }

    private class String setSoDienThoai(String sdt){
        SoDienThoai = sdt;

        return sdt;
    }

    private void getSoDienThoai(){
        System.out.println("An so dien thoai" + SoDienThoai);
    }
}
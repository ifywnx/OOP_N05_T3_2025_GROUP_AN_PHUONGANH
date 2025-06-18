public class Khachhang {

    // Biến miêu tả - Member variable
    String MaKhachHang;
    String TenKhachHang;
    String DiaChi;
    String SoDienThoai;

    // phuong thuc hoat dong - methods
    //shift-alt-F: định dang dữ liẹu 

    public String setMaKhachHang(String name){
        MaKhachHang = name;

        return MaKhachHang;
    }

    public void getMaKhachHang(){
        System.out.println("Hien thi ma khach hang" + MaKhachHang);
    }

    //Default constructor

   public Khachhang(){

    }
}
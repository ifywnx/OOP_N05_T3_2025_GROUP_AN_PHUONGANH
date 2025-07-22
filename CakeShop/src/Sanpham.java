public class Sanpham {

    // Biến miêu tả - Member variable
   private String MaSanPham;
   String TenSanPham;
   int SoLuong;
   String NhaSanXuat;
   String HanSuDung;
   String NguyenLieu;
//contructor
   public Sanpham(){

   }
   public String setMaSanPham(String msp){
    MaSanPham = msp;
    return MaSanPham;
   }
   public void getMaSanPham(){
    System.out.println("Hien thi ma san pham: " + MaSanPham);
   }

   public String setTenSanPham(String tsp){
    TenSanPham = tsp;
    return TenSanPham;
   }
   public void getTenSanPham(){
    System.out.println("Hien thi ten san pham: " + TenSanPham);
   }

   public int setSoLuong(int sl){
    SoLuong = sl;
    return SoLuong;
   }
   public void getSoLuong(){
    System.out.println("Hien thi so luong: " + SoLuong);
   }

   public String setNhaSanXuat(String nsx){
    NhaSanXuat = nsx;
    return NhaSanXuat;
   }
   public void getNhaSanXuat(){
    System.out.println("Hien thi nha san xuat: " + NhaSanXuat);
   }

   public String setHanSuDung(String hsd){
    HanSuDung = hsd;
    return HanSuDung;
   }
   public void getHanSuDung(){
    System.out.println("Hien thi han su dung: " + HanSuDung);
   }

   public String setNguyenLieu(String nl){
    NguyenLieu = nl;
    return NguyenLieu;
   }
   public void getNguyenLieu(){
    System.out.println("Hien thi nguyen lieu: " + NguyenLieu);
   }
   public String getTenSanPham() {
    return TenSanPham;
}

public int getSoLuong() {
    return SoLuong;
}
}
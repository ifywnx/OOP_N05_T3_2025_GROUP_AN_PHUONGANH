public class Nhanvien {
    // Biến miêu tả - Member variable
    String MaNhanVien;
    String TenNhanVien;
    String DiaChi;
    String SoDienThoai;
    String TienLuong;

    // Methods
    public String setMaNhanVien(String mnv){
        MaNhanVien = mnv;
        return MaNhanVien;

    }
    public void getMaNhanVien(){
        System.out.println("Hien thi ma nhan vien" + MaNhanVien);

    }

    public String setTenNhanVien(String tennv){
        TenNhanVien = tennv;
        return TenNhanVien;

    }
    public void getTenNhanVien(){
        System.out.println("Hien thi ten nhan vien" + TenNhanVien);

    }

    private String setDiaChi(String dc){
        DiaChi = dc;
        return DiaChi;

    }
    private void getDiaChi(){
        System.out.println("Khong tiet lo");

    }

    private String setSoDienThoai(String sdt){
        SoDienThoai = sdt;
        return SoDienThoai;

    }
    private void getSoDienThoai(){
        System.out.println("Khong tiet lo");

    }

    public String setTienLuong(String luong){
        TienLuong = luong;
        return TienLuong;

    }
    public void getTienLuong(){
        System.out.println("Hien thi tien luong" + TienLuong);
    }
}

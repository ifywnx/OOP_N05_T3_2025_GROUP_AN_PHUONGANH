public class Nhanvien {
    // Biến miêu tả - Member variable
    private String MaNhanVien;
    private String TenNhanVien;
    private String DiaChi;
    private String SoDienThoai;
    private String TienLuong;

    //contructor
    public Nhanvien(){
        
    }

    // Methods
    public String setMaNhanVien(String mnv){
        MaNhanVien = mnv;
        return MaNhanVien;

    }
    public void getMaNhanVien(){
        System.out.println("Hien thi ma nhan vien: " + MaNhanVien);

    }

    public String setTenNhanVien(String tennv){
        TenNhanVien = tennv;
        return TenNhanVien;

    }
    public void getTenNhanVien(){
        System.out.println("Hien thi ten nhan vien: " + TenNhanVien);

    }

    public String setDiaChi(String dc){
        DiaChi = dc;
        return DiaChi;

    }
    public void getDiaChi(){
        System.out.println("DiaChi: " + DiaChi);

    }

    public String setSoDienThoai(String sdt){
        SoDienThoai = sdt;
        return SoDienThoai;

    }
    public void getSoDienThoai(){
        System.out.println("So dien thoai: " + SoDienThoai);

    }

    public String setTienLuong(String luong){
        TienLuong = luong;
        return TienLuong;

    }
    public void getTienLuong(){
        System.out.println("Hien thi tien luong: " + TienLuong);
    }
}

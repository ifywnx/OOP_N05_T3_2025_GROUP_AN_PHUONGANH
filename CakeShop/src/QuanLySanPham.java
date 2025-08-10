import java.util.ArrayList;

public class QuanLySanPham {
    public void hienThiSanPhamConHang(ArrayList<Sanpham> danhSach) {
        System.out.println("Danh sách sản phẩm còn hàng: ");
        for (Sanpham sp : danhSach) {
            if (sp.SoLuong > 0) {
                sp.getTenSanPham();
                sp.getSoLuong();
                sp.getNhaSanXuat();
                sp.getHanSuDung();
                sp.getNguyenLieu();
                System.out.println(); // dòng trống
            }
        }
    }
}


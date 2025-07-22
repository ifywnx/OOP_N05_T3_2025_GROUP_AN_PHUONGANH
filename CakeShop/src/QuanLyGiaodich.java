import java.util.ArrayList;
import java.util.List;

public class QuanLyGiaodich {
    private List<Giaodich> danhSach;

    public QuanLyGiaodich() {
        danhSach = new ArrayList<>();
    }

    // CREATE: Thêm mới giao dịch
    public void themGD(Giaodich gd) {
        danhSach.add(gd);
        System.out.println("Đã thêm giao dịch " + gd.getMaGiaoDich());
    }

    // READ: Hiển thị tất cả giao dịch
    public void hienThiTatCa() {
        for (Giaodich gd : danhSach) {
            gd.hienThi();
            System.out.println("-------------------");
        }
    }

    // UPDATE: Cập nhật tổng tiền theo mã giao dịch
    public void capNhatTongTien(String maGD, double tongTienMoi) {
        for (Giaodich gd : danhSach) {
            if (gd.getMaGiaoDich().equals(maGD)) {
                gd.setTongTien(tongTienMoi);
                System.out.println("Đã cập nhật tổng tiền cho GD " + maGD);
                return;
            }
        }
        System.out.println("Không tìm thấy GD có mã " + maGD);
    }

    // DELETE: Xóa giao dịch theo mã
    public void xoaGD(String maGD) {
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaGiaoDich().equals(maGD)) {
                danhSach.remove(i);
                System.out.println("Đã xóa giao dịch " + maGD);
                return;
            }
        }
        System.out.println("Không tìm thấy GD có mã " + maGD);
    }
}

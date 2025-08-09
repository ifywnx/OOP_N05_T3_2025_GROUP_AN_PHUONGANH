import java.util.ArrayList;

public class QuanLyGiaoDich {
    public void TimGiaoDichTheoNgay(ArrayList<Giaodich> DanhSach, String NgayCanTim) {
        System.out.println("Danh sách giao dịch trong ngày: " + NgayCanTim);
        boolean TimThay = false;

        for (Giaodich gd : DanhSach) {
            if (gd.NgayThangNamGiaoDich.equals(NgayCanTim)) {
                gd.getMaGiaoDich();
                gd.getGioGiaoDich();
                gd.getTongTien();
                TimThay = true;
            }
        }

        if (!TimThay) {
            System.out.println("Không tìm thấy giao dịch nào trong ngày này.");
        }
    }
}

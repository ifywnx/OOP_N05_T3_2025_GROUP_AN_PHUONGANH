import java.util.ArrayList;
import java.util.Scanner;

public class TestQuanLyGiaoDich {
    public static void test() {
        ArrayList<Giaodich> DanhSach = new ArrayList<>();

        Giaodich gd1 = new Giaodich();
        gd1.setMaGiaoDich("GD01");
        gd1.setNgayThangNamGiaoDich("22/07/2025");
        gd1.setGioGiaoDich("08:30");
        gd1.setTongTien(100000);
        DanhSach.add(gd1);

        Giaodich gd2 = new Giaodich();
        gd2.setMaGiaoDich("GD02");
        gd2.setNgayThangNamGiaoDich("21/07/2025");
        gd2.setGioGiaoDich("14:15");
        gd2.setTongTien(200000);
        DanhSach.add(gd2);

        Giaodich gd3 = new Giaodich();
        gd3.setMaGiaoDich("GD03");
        gd3.setNgayThangNamGiaoDich("22/07/2025");
        gd3.setGioGiaoDich("16:45");
        gd3.setTongTien(150000);
        DanhSach.add(gd3);

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập ngày cần tìm (dd/MM/yyyy): ");
        String NgayCanTim = sc.nextLine();

        QuanLyGiaoDich qlgd = new QuanLyGiaoDich();
        qlgd.TimGiaoDichTheoNgay(DanhSach, NgayCanTim);
    }
}

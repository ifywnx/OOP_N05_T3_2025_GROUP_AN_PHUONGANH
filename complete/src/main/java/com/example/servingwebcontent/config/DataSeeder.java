package com.example.servingwebcontent.config;

import com.example.servingwebcontent.model.GiaoDich;
import com.example.servingwebcontent.model.KhachHang;
import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.repository.GiaoDichRepository;
import com.example.servingwebcontent.repository.KhachHangRepository;
import com.example.servingwebcontent.repository.NhanVienRepository;
import com.example.servingwebcontent.repository.SanPhamRepository;
import com.example.servingwebcontent.service.GiaoDichService;
import com.example.servingwebcontent.service.discount.NoDiscount;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final NhanVienRepository nhanVienRepo;
    private final KhachHangRepository khachHangRepo;
    private final SanPhamRepository sanPhamRepo;
    private final GiaoDichRepository giaoDichRepo;
    private final GiaoDichService giaoDichService;

    public DataSeeder(NhanVienRepository nhanVienRepo,
                      KhachHangRepository khachHangRepo,
                      SanPhamRepository sanPhamRepo,
                      GiaoDichRepository giaoDichRepo,
                      GiaoDichService giaoDichService) {
        this.nhanVienRepo = nhanVienRepo;
        this.khachHangRepo = khachHangRepo;
        this.sanPhamRepo = sanPhamRepo;
        this.giaoDichRepo = giaoDichRepo;
        this.giaoDichService = giaoDichService;
    }

    @Override
    public void run(String... args) {
        // Seed NhanVien
        if (nhanVienRepo.count() == 0) {
            nhanVienRepo.save(new NhanVien("NV001", "Nguyễn Văn An", "Hà Nội", "0900000001", 12_000_000d));
            nhanVienRepo.save(new NhanVien("NV002", "Lê Thị Phương Anh", "Hà Nội", "0900000002", 15_000_000d));
        }

        // Seed KhachHang
        if (khachHangRepo.count() == 0) {
            khachHangRepo.save(new KhachHang("Trần Minh Tuấn", "KH001", "Đống Đa", "0911111111"));
            khachHangRepo.save(new KhachHang("Phạm Thu Hằng", "KH002", "Cầu Giấy", "0922222222"));
        }

        // Seed SanPham
        if (sanPhamRepo.count() == 0) {
            sanPhamRepo.save(new SanPham(
                    "SP001", "Bánh kem dâu", 25,
                    "CakeHouse", LocalDate.of(2025, 12, 31), "Bột mì; Dâu tây; Kem tươi"
            ));
            sanPhamRepo.save(new SanPham(
                    "SP002", "Cupcake chocolate", 60,
                    "SweetHome", LocalDate.of(2025, 10, 31), "Bột mì; Socola; Bơ"
            ));
            sanPhamRepo.save(new SanPham(
                    "SP003", "Bánh mì bơ tỏi", 50,
                    "Bakery House", LocalDate.now().plusDays(7), "Bột mì; Bơ; Tỏi"
            ));
        }

        // Seed GiaoDich qua Service (trừ kho + giảm giá)
        if (giaoDichRepo.count() == 0) {
            List<SanPham> spAll = sanPhamRepo.findAll();
            if (spAll.size() >= 2) {
                GiaoDich gd = new GiaoDich("GD001", LocalDate.now(), LocalTime.now(), 0);
                // items: sản phẩm + số lượng + đơn giá
                GiaoDichService.Item i1 = new GiaoDichService.Item(spAll.get(0), 2, 150_000); // 2 * 150k
                GiaoDichService.Item i2 = new GiaoDichService.Item(spAll.get(1), 1, 90_000);  // 1 * 90k
                giaoDichService.taoGiaoDich(gd, List.of(i1, i2), new NoDiscount());
            }
        }
    }
}

package com.example.servingwebcontent.config;

import com.example.servingwebcontent.model.*;
import com.example.servingwebcontent.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private SanPhamRepository sanPhamRepository;
    @Autowired private KhachHangRepository khachHangRepository;
    @Autowired private NhanVienRepository nhanVienRepository;
    @Autowired private GiaoDichRepository giaoDichRepository;

    private final Random random = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("\n🌱 Starting Enhanced Data Seeding...");
        
        if (sanPhamRepository.count() > 0) {
            System.out.println("📊 Data already exists. Skipping seeding.");
            return;
        }

        try {
            createSampleProducts();
            createSampleCustomers();
            createSampleEmployees();
            createSampleTransactions();
            
            System.out.println("✅ Enhanced data seeding completed successfully!");
            printSummary();
            
        } catch (Exception e) {
            System.err.println("❌ Error during data seeding: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createSampleProducts() {
        System.out.println("🎂 Creating sample products...");
        
        // Bánh Kem
        createProduct("BK001", "Bánh Kem Tiramisu Đặc Biệt", "Bánh kem Tiramisu authentic với cà phê Espresso Ý", 
                     SanPham.DanhMucSanPham.BANH_KEM, 180000.0, 120000.0, 15, "Italian Bakery");
        createProduct("BK002", "Bánh Kem Red Velvet", "Bánh kem Red Velvet với cream cheese frosting", 
                     SanPham.DanhMucSanPham.BANH_KEM, 220000.0, 150000.0, 12, "French Bakery");
        createProduct("BK003", "Bánh Kem Chocolate Truffle", "Bánh kem chocolate đậm đà với truffle ganache", 
                     SanPham.DanhMucSanPham.BANH_KEM, 250000.0, 170000.0, 8, "Swiss Chocolate");
        createProduct("BK004", "Bánh Kem Cheesecake Blueberry", "New York style cheesecake với blueberry", 
                     SanPham.DanhMucSanPham.BANH_KEM, 200000.0, 140000.0, 10, "American Bakery");
        createProduct("BK005", "Bánh Kem Matcha", "Bánh kem matcha authentic với bột trà xanh Uji", 
                     SanPham.DanhMucSanPham.BANH_KEM, 230000.0, 160000.0, 6, "Tokyo Sweets");

        // Bánh Ngọt
        createProduct("BN001", "Croissant Bơ Pháp", "Croissant truyền thống với 64 lớp bơ", 
                     SanPham.DanhMucSanPham.BANH_NGOT, 45000.0, 25000.0, 25, "Boulangerie Paris");
        createProduct("BN002", "Macaron Rainbow Set", "Set 12 macaron với 6 vị khác nhau", 
                     SanPham.DanhMucSanPham.BANH_NGOT, 320000.0, 200000.0, 8, "Maison Macaron");
        createProduct("BN003", "Éclair Chocolate", "Éclair với choux pastry và chocolate ganache", 
                     SanPham.DanhMucSanPham.BANH_NGOT, 85000.0, 50000.0, 15, "French Patisserie");
        createProduct("BN004", "Muffin Blueberry", "Muffin với blueberry tươi và streusel topping", 
                     SanPham.DanhMucSanPham.BANH_NGOT, 45000.0, 25000.0, 20, "Morning Bakery");

        // Bánh Mì
        createProduct("BM001", "Croissant Jambon Fromage", "Croissant với jambon Pháp và fromage", 
                     SanPham.DanhMucSanPham.BANH_MI, 65000.0, 35000.0, 20, "Deli Express");
        createProduct("BM002", "Bagel Cá Hồi", "Bagel với cá hồi Na Uy và cream cheese", 
                     SanPham.DanhMucSanPham.BANH_MI, 95000.0, 60000.0, 12, "Nordic Deli");
        createProduct("BM003", "Club Sandwich", "Sandwich 3 tầng với gà nướng và bacon", 
                     SanPham.DanhMucSanPham.BANH_MI, 85000.0, 50000.0, 18, "American Deli");

        // Đồ Uống
        createProduct("DU001", "Espresso Ý", "Espresso từ hạt Arabica rang medium", 
                     SanPham.DanhMucSanPham.DO_UONG, 35000.0, 15000.0, 50, "Italian Coffee");
        createProduct("DU002", "Trà Sữa Matcha", "Trà sữa matcha với bột trà xanh Uji", 
                     SanPham.DanhMucSanPham.DO_UONG, 65000.0, 35000.0, 30, "Tea House");
        createProduct("DU003", "Hot Chocolate Bỉ", "Chocolate nóng từ cocoa Bỉ", 
                     SanPham.DanhMucSanPham.DO_UONG, 55000.0, 30000.0, 25, "Belgian Chocolate");

        // Bánh Quy
        createProduct("BQ001", "Cookie Chocolate Chip", "Cookies với chocolate chips Bỉ", 
                     SanPham.DanhMucSanPham.BANH_QUY, 25000.0, 12000.0, 40, "American Cookies");
        createProduct("BQ002", "Bánh Quy Bơ Đan Mạch", "Danish butter cookies trong hộp thiếc", 
                     SanPham.DanhMucSanPham.BANH_QUY, 180000.0, 100000.0, 15, "Danish Bakery");
    }

    private void createProduct(String ma, String ten, String moTa, SanPham.DanhMucSanPham danhMuc, 
                              Double giaBan, Double giaVon, Integer tonKho, String nhaSX) {
        SanPham sanPham = new SanPham();
        sanPham.setMaSanPham(ma);
        sanPham.setTenSanPham(ten);
        sanPham.setMoTa(moTa);
        sanPham.setDanhMuc(danhMuc);
        sanPham.setGiaBan(giaBan);
        sanPham.setGiaVon(giaVon);
        sanPham.setSoLuongTonKho(tonKho);
        sanPham.setHanSuDung(LocalDate.now().plusDays(random.nextInt(30) + 5));
        sanPham.setNhaSanXuat(nhaSX);
        sanPham.setSoLuongToiThieu(5);
        sanPham.setSoLuongDaBan(random.nextInt(20));
        sanPham.updateTrangThai();
        
        sanPhamRepository.save(sanPham);
    }

    private void createSampleCustomers() {
        System.out.println("👥 Creating sample customers...");
        
        List<Object[]> customerData = Arrays.asList(
            new Object[]{"KH001", "Nguyễn Văn Minh", LocalDate.of(1985, 3, 15), KhachHang.GioiTinh.NAM, "0901234567", "minh.nguyen@email.com", "Đống Đa, Hà Nội", 5500000.0, 15},
            new Object[]{"KH002", "Trần Thị Hoa", LocalDate.of(1990, 7, 22), KhachHang.GioiTinh.NU, "0987654321", "hoa.tran@email.com", "Hai Bà Trưng, Hà Nội", 2800000.0, 8},
            new Object[]{"KH003", "Lê Đức Thành", LocalDate.of(1988, 12, 3), KhachHang.GioiTinh.NAM, "0912345678", "thanh.le@email.com", "Thanh Xuân, Hà Nội", 1650000.0, 5},
            new Object[]{"KH004", "Phạm Thị Lan", LocalDate.of(1992, 5, 18), KhachHang.GioiTinh.NU, "0923456789", "lan.pham@email.com", "Hoàng Mai, Hà Nội", 4750000.0, 12},
            new Object[]{"KH005", "Hoàng Văn Đức", LocalDate.of(1987, 9, 8), KhachHang.GioiTinh.NAM, "0934567890", "duc.hoang@email.com", "Cầu Giấy, Hà Nội", 920000.0, 3},
            new Object[]{"KH006", "Vũ Thị Mai", LocalDate.of(1995, 1, 25), KhachHang.GioiTinh.NU, "0945678901", "mai.vu@email.com", "Ba Đình, Hà Nội", 3200000.0, 9},
            new Object[]{"KH007", "Đỗ Văn Quang", LocalDate.of(1983, 11, 12), KhachHang.GioiTinh.NAM, "0956789012", "quang.do@email.com", "Tây Hồ, Hà Nội", 7800000.0, 18}
        );

        for (Object[] data : customerData) {
            KhachHang khachHang = new KhachHang();
            khachHang.setMaKhachHang((String) data[0]);
            khachHang.setHoTen((String) data[1]);
            khachHang.setNgaySinh((LocalDate) data[2]);
            khachHang.setGioiTinh((KhachHang.GioiTinh) data[3]);
            khachHang.setSoDienThoai((String) data[4]);
            khachHang.setEmail((String) data[5]);
            khachHang.setDiaChi((String) data[6]);
            khachHang.setKhuVuc(((String) data[6]).split(",")[0].trim());
            khachHang.setTongChiTieu((Double) data[7]);
            khachHang.setSoLanMuaHang((Integer) data[8]);
            khachHang.setDiemTichLuy((int) ((Double) data[7] / 10000));
            khachHang.setGiaTriDonHangTrungBinh((Double) data[7] / (Integer) data[8]);
            khachHang.setLanMuaCuoi(LocalDate.now().minusDays(random.nextInt(15)));
            khachHang.setSanPhamYeuThich("Bánh kem");
            khachHang.capNhatLoaiKhachHang();
            khachHang.setTrangThai(KhachHang.TrangThaiKhachHang.HOAT_DONG);
            
            khachHangRepository.save(khachHang);
        }
    }

    private void createSampleEmployees() {
        System.out.println("👨‍💼 Creating sample employees...");
        
        List<Object[]> employeeData = Arrays.asList(
            new Object[]{"NV001", "Nguyễn Thị Quỳnh", LocalDate.of(1985, 5, 20), NhanVien.GioiTinh.NU, "0901111111", "quynh.manager@bakery.com", NhanVien.PhongBan.QUAN_LY, NhanVien.ChucVu.GIAM_DOC, 25000000.0, 75000000.0, 22},
            new Object[]{"NV002", "Trần Văn Hải", LocalDate.of(1990, 3, 8), NhanVien.GioiTinh.NAM, "0902222222", "hai.sales@bakery.com", NhanVien.PhongBan.BAN_HANG, NhanVien.ChucVu.TRUONG_PHONG, 15000000.0, 35000000.0, 20},
            new Object[]{"NV003", "Lê Thị Hương", LocalDate.of(1992, 8, 12), NhanVien.GioiTinh.NU, "0903333333", "huong.sales@bakery.com", NhanVien.PhongBan.BAN_HANG, NhanVien.ChucVu.NHAN_VIEN_CHINH, 12000000.0, 28000000.0, 21},
            new Object[]{"NV004", "Phạm Văn Tuấn", LocalDate.of(1988, 6, 15), NhanVien.GioiTinh.NAM, "0904444444", "tuan.chef@bakery.com", NhanVien.PhongBan.KHO, NhanVien.ChucVu.NHAN_VIEN, 11000000.0, 0.0, 22},
            new Object[]{"NV005", "Bùi Thị Linh", LocalDate.of(1994, 2, 28), NhanVien.GioiTinh.NU, "0905555555", "linh.accounting@bakery.com", NhanVien.PhongBan.KE_TOAN, NhanVien.ChucVu.NHAN_VIEN_CHINH, 13000000.0, 0.0, 21}
        );

        for (Object[] data : employeeData) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNhanVien((String) data[0]);
            nhanVien.setHoTen((String) data[1]);
            nhanVien.setNgaySinh((LocalDate) data[2]);
            nhanVien.setGioiTinh((NhanVien.GioiTinh) data[3]);
            nhanVien.setSoDienThoai((String) data[4]);
            nhanVien.setEmail((String) data[5]);
            nhanVien.setDiaChi("Hà Nội");
            nhanVien.setPhongBan((NhanVien.PhongBan) data[6]);
            nhanVien.setChucVu((NhanVien.ChucVu) data[7]);
            nhanVien.setNgayVaoLam(LocalDate.now().minusYears(random.nextInt(3) + 1));
            nhanVien.setLuongCoBan((Double) data[8]);
            nhanVien.setDoanhSoThang((Double) data[9]);
            nhanVien.setSoNgayLamViecThang((Integer) data[10]);
            nhanVien.setLoaiHopDong(NhanVien.LoaiHopDong.KHONG_XAC_DINH_THOI_HAN);
            nhanVien.setSoDonHangThang(random.nextInt(30) + 10);
            nhanVien.setTrangThai(NhanVien.TrangThaiNhanVien.DANG_LAM_VIEC);
            nhanVien.capNhatHieuSuat();
            
            nhanVienRepository.save(nhanVien);
        }
    }

    private void createSampleTransactions() {
        System.out.println("🧾 Creating sample transactions...");
        
        List<SanPham> sanPhams = sanPhamRepository.findAll();
        List<KhachHang> khachHangs = khachHangRepository.findAll();
        List<NhanVien> nhanViens = nhanVienRepository.findAll();

        for (int i = 1; i <= 15; i++) {
            GiaoDich giaoDich = new GiaoDich();
            giaoDich.setMaGiaoDich(String.format("GD2024120%02d", i));
            giaoDich.setLoaiGiaoDich(GiaoDich.LoaiGiaoDich.BAN_HANG);
            
            SanPham sanPham = sanPhams.get(random.nextInt(sanPhams.size()));
            KhachHang khachHang = khachHangs.get(random.nextInt(khachHangs.size()));
            NhanVien nhanVien = nhanViens.get(random.nextInt(nhanViens.size()));
            
            giaoDich.setSanPham(sanPham);
            giaoDich.setKhachHang(khachHang);
            giaoDich.setNhanVien(nhanVien);
            
            int soLuong = random.nextInt(3) + 1;
            giaoDich.setSoLuong(soLuong);
            giaoDich.setDonGia(sanPham.getGiaBan());
            
            GiaoDich.PhuongThucThanhToan[] phuongThucArray = GiaoDich.PhuongThucThanhToan.values();
            giaoDich.setPhuongThucThanhToan(phuongThucArray[random.nextInt(phuongThucArray.length)]);
            
            giaoDich.tinhToanGiaoDich();
            
            if (i <= 12) {
                giaoDich.setTrangThai(GiaoDich.TrangThaiGiaoDich.HOAN_THANH);
                giaoDich.setThoiGianHoanThanh(LocalDateTime.now().minusDays(random.nextInt(10)));
                giaoDich.setTienKhachDua(giaoDich.getTongTien() + random.nextInt(50000));
                giaoDich.hoanThanhGiaoDich();
            } else {
                giaoDich.setTrangThai(GiaoDich.TrangThaiGiaoDich.DANG_XU_LY);
            }
            
            giaoDichRepository.save(giaoDich);
        }
    }

    private void printSummary() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎉 DATA SEEDING COMPLETED!");
        System.out.println("=".repeat(60));
        System.out.println("📦 Products: " + sanPhamRepository.count());
        System.out.println("👥 Customers: " + khachHangRepository.count());
        System.out.println("👨‍💼 Employees: " + nhanVienRepository.count());
        System.out.println("🧾 Transactions: " + giaoDichRepository.count());
        System.out.println("\n🌐 ACCESS URLs:");
        System.out.println("🏠 Dashboard: http://localhost:8080/dashboard");
        System.out.println("💾 H2 Console: http://localhost:8080/h2-console");
        System.out.println("=".repeat(60) + "\n");
    }
}
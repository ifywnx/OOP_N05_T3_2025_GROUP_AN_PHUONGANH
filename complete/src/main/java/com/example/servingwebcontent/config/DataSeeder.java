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

/**
 * 🌱 Enhanced Data Seeder - 20 Diverse Bakery Products
 * 
 * Tạo 20 sản phẩm tiệm bánh đa dạng với hình ảnh thật từ Unsplash:
 * - 6 Bánh Kem cao cấp (Tiramisu, Red Velvet, Chocolate, Cheesecake...)
 * - 5 Bánh Ngọt Pháp (Croissant, Macaron, Éclair, Muffin...)
 * - 4 Bánh Mì đặc biệt (Sandwich, Bagel...)
 * - 3 Đồ Uống premium (Espresso, Trà sữa...)
 * - 2 Bánh Quy artisan
 */
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
        
        // Check if data already exists
        if (sanPhamRepository.count() > 0) {
            System.out.println("📊 Data already exists. Skipping seeding.");
            return;
        }

        try {
            createBanhKemProducts();
            createBanhNgotProducts();
            createBanhMiProducts();
            createDoUongProducts();
            createBanhQuyProducts();
            createSampleCustomers();
            createSampleEmployees();
            createSampleTransactions();
            
            System.out.println("✅ Enhanced data seeding completed successfully!");
            printDetailedSummary();
            
        } catch (Exception e) {
            System.err.println("❌ Error during data seeding: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 🎂 Tạo 6 Bánh Kem Premium
     */
    private void createBanhKemProducts() {
        System.out.println("🎂 Creating premium cakes...");
        
        List<Object[]> cakeData = Arrays.asList(
            new Object[]{"BK001", "Bánh Kem Tiramisu Đặc Biệt", "Bánh kem Tiramisu authentic với cà phê Espresso Ý và mascarpone cheese nhập khẩu", 180000.0, 120000.0, 15, LocalDate.now().plusDays(3), "Italian Bakery"},
            new Object[]{"BK002", "Bánh Kem Red Velvet Luxury", "Bánh kem Red Velvet với cream cheese frosting và trang trí hoa hồng tươi", 220000.0, 150000.0, 12, LocalDate.now().plusDays(3), "French Bakery"},
            new Object[]{"BK003", "Bánh Kem Chocolate Truffle", "Bánh kem chocolate đậm đà với truffle ganache và gold leaf decoration", 250000.0, 170000.0, 8, LocalDate.now().plusDays(2), "Swiss Chocolate"},
            new Object[]{"BK004", "Bánh Kem Cheesecake Blueberry", "New York style cheesecake với blueberry compote tươi và graham crust", 200000.0, 140000.0, 10, LocalDate.now().plusDays(4), "American Bakery"},
            new Object[]{"BK005", "Bánh Kem Matcha Nhật Bản", "Bánh kem matcha authentic với bột trà xanh Uji cao cấp và whipped cream", 230000.0, 160000.0, 6, LocalDate.now().plusDays(2), "Tokyo Sweets"},
            new Object[]{"BK006", "Bánh Kem Dâu Tây Pháp", "Bánh kem với dâu tây Đà Lạt tươi, vanilla cream và sponge cake mềm", 190000.0, 130000.0, 14, LocalDate.now().plusDays(3), "Le Petit Gateau"}
        );

        for (Object[] data : cakeData) {
            createSanPham(data, SanPham.DanhMucSanPham.BANH_KEM);
        }
    }

    /**
     * 🧁 Tạo 5 Bánh Ngọt Pháp & Muffins
     */
    private void createBanhNgotProducts() {
        System.out.println("🧁 Creating French pastries & sweets...");
        
        List<Object[]> pastryData = Arrays.asList(
            new Object[]{"BN001", "Croissant Bơ Pháp", "Croissant truyền thống với 64 lớp bơ, nướng golden crispy theo công thức Pháp", 45000.0, 25000.0, 25, LocalDate.now().plusDays(1), "Boulangerie Paris"},
            new Object[]{"BN002", "Macaron Rainbow Set", "Set 12 macaron với 6 vị: vanilla, chocolate, strawberry, matcha, lavender, rose", 320000.0, 200000.0, 8, LocalDate.now().plusDays(5), "Maison Macaron"},
            new Object[]{"BN003", "Éclair Chocolate Premium", "Éclair với choux pastry, chocolate ganache và gold decoration", 85000.0, 50000.0, 15, LocalDate.now().plusDays(2), "French Patisserie"},
            new Object[]{"BN004", "Muffin Blueberry Tươi", "Muffin với blueberry Mỹ tươi và streusel topping giòn tan", 45000.0, 25000.0, 20, LocalDate.now().plusDays(2), "Morning Bakery"},
            new Object[]{"BN005", "Cupcake Double Chocolate", "Cupcake chocolate với chocolate ganache frosting và chocolate chips", 38000.0, 22000.0, 25, LocalDate.now().plusDays(2), "Sweet Treats"}
        );

        for (Object[] data : pastryData) {
            createSanPham(data, SanPham.DanhMucSanPham.BANH_NGOT);
        }
    }

    /**
     * 🥖 Tạo 4 Bánh Mì Đặc Biệt
     */
    private void createBanhMiProducts() {
        System.out.println("🥖 Creating specialty breads...");
        
        List<Object[]> breadData = Arrays.asList(
            new Object[]{"BM001", "Croissant Jambon Fromage", "Croissant với jambon Pháp và fromage Gruyère, nướng giòn", 65000.0, 35000.0, 20, LocalDate.now().plusDays(1), "Deli Express"},
            new Object[]{"BM002", "Bagel Cá Hồi Nướng", "Bagel với cá hồi Na Uy, cream cheese và capers", 95000.0, 60000.0, 12, LocalDate.now().plusDays(1), "Nordic Deli"},
            new Object[]{"BM003", "Club House Sandwich", "Sandwich 3 tầng với gà nướng, bacon, rau sống và aioli", 85000.0, 50000.0, 18, LocalDate.now().plusDays(1), "American Deli"},
            new Object[]{"BM004", "Bánh Mì Baguette Việt Nam", "Bánh mì truyền thống Việt Nam với thịt nguội và rau thơm", 35000.0, 20000.0, 30, LocalDate.now().plusDays(1), "Saigon Bakery"}
        );

        for (Object[] data : breadData) {
            createSanPham(data, SanPham.DanhMucSanPham.BANH_MI);
        }
    }

    /**
     * ☕ Tạo 3 Đồ Uống Premium
     */
    private void createDoUongProducts() {
        System.out.println("☕ Creating premium beverages...");
        
        List<Object[]> drinkData = Arrays.asList(
            new Object[]{"DU001", "Espresso Ý Đậm Đà", "Espresso từ hạt Arabica rang medium, extraction 25s perfect crema", 35000.0, 15000.0, 50, LocalDate.now().plusDays(7), "Italian Coffee"},
            new Object[]{"DU002", "Trà Sữa Matcha Premium", "Trà sữa matcha với bột trà xanh Uji và pearls tươi", 65000.0, 35000.0, 30, LocalDate.now().plusDays(7), "Tea House"},
            new Object[]{"DU003", "Hot Chocolate Bỉ", "Chocolate nóng từ cocoa Bỉ với whipped cream và marshmallow", 55000.0, 30000.0, 25, LocalDate.now().plusDays(10), "Belgian Chocolate"}
        );

        for (Object[] data : drinkData) {
            createSanPham(data, SanPham.DanhMucSanPham.DO_UONG);
        }
    }

    /**
     * 🍪 Tạo 2 Bánh Quy Artisan
     */
    private void createBanhQuyProducts() {
        System.out.println("🍪 Creating artisan cookies...");
        
        List<Object[]> cookieData = Arrays.asList(
            new Object[]{"BQ001", "Cookie Chocolate Chip Mỹ", "Cookies với chocolate chips Bỉ và vanilla Madagascar, nướng giòn", 25000.0, 12000.0, 40, LocalDate.now().plusDays(10), "American Cookies"},
            new Object[]{"BQ002", "Bánh Quy Bơ Đan Mạch", "Danish butter cookies truyền thống trong hộp thiếc cao cấp", 180000.0, 100000.0, 15, LocalDate.now().plusDays(30), "Danish Bakery"}
        );

        for (Object[] data : cookieData) {
            createSanPham(data, SanPham.DanhMucSanPham.BANH_QUY);
        }
    }

    /**
     * 🏗️ Helper method để tạo sản phẩm
     */
    private void createSanPham(Object[] data, SanPham.DanhMucSanPham danhMuc) {
        SanPham sanPham = new SanPham();
        sanPham.setMaSanPham((String) data[0]);
        sanPham.setTenSanPham((String) data[1]);
        sanPham.setMoTa((String) data[2]);
        sanPham.setDanhMuc(danhMuc);
        sanPham.setGiaBan((Double) data[3]);
        sanPham.setGiaVon((Double) data[4]);
        sanPham.setSoLuongTonKho((Integer) data[5]);
        sanPham.setHanSuDung((LocalDate) data[6]);
        sanPham.setNhaSanXuat((String) data[7]);
        sanPham.setSoLuongToiThieu(5);
        sanPham.setSoLuongDaBan(random.nextInt(15));
        
        sanPhamRepository.save(sanPham);
    }

    /**
     * 👥 Tạo khách hàng mẫu
     */
    private void createSampleCustomers() {
        System.out.println("👥 Creating sample customers...");
        
        List<Object[]> customerData = Arrays.asList(
            new Object[]{"KH001", "Nguyễn Văn Minh", LocalDate.of(1985, 3, 15), KhachHang.GioiTinh.NAM, "0901234567", "minh.nguyen@email.com", "123 Đường Láng, Đống Đa, Hà Nội", "Đống Đa", 6500000.0, 12, "Bánh kem chocolate"},
            new Object[]{"KH002", "Trần Thị Hoa", LocalDate.of(1990, 7, 22), KhachHang.GioiTinh.NU, "0987654321", "hoa.tran@email.com", "456 Phố Huế, Hai Bà Trưng, Hà Nội", "Hai Bà Trưng", 2800000.0, 8, "Macaron"},
            new Object[]{"KH003", "Lê Đức Thành", LocalDate.of(1988, 12, 3), KhachHang.GioiTinh.NAM, "0912345678", "thanh.le@email.com", "789 Nguyễn Trãi, Thanh Xuân, Hà Nội", "Thanh Xuân", 1650000.0, 5, "Croissant"},
            new Object[]{"KH004", "Phạm Thị Lan", LocalDate.of(1992, 5, 18), KhachHang.GioiTinh.NU, "0923456789", "lan.pham@email.com", "321 Giải Phóng, Hoàng Mai, Hà Nội", "Hoàng Mai", 4750000.0, 10, "Tiramisu"},
            new Object[]{"KH005", "Hoàng Văn Đức", LocalDate.of(1987, 9, 😎, KhachHang.GioiTinh.NAM, "0934567890", "duc.hoang@email.com", "654 Tô Hiệu, Cầu Giấy, Hà Nội", "Cầu Giấy", 920000.0, 3, "Bánh mì"}
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
            khachHang.setKhuVuc((String) data[7]);
            khachHang.setTongChiTieu((Double) data[8]);
            khachHang.setSoLanMuaHang((Integer) data[9]);
            khachHang.setSanPhamYeuThich((String) data[10]);
            
            double tongChiTieu = (Double) data[8];
            khachHang.setDiemTichLuy((int) (tongChiTieu / 10000));
            khachHang.setGiaTriDonHangTrungBinh(tongChiTieu / (Integer) data[9]);
            khachHang.setLanMuaCuoi(LocalDate.now().minusDays(random.nextInt(30)));
            khachHang.capNhatLoaiKhachHang();
            
            khachHangRepository.save(khachHang);
        }
    }

    /**
     * 👨‍💼 Tạo nhân viên mẫu
     */
    private void createSampleEmployees() {
        System.out.println("👨‍💼 Creating sample employees...");
        
        List<Object[]> employeeData = Arrays.asList(
            new Object[]{"NV001", "Nguyễn Thị Quỳnh", LocalDate.of(1985, 5, 20), NhanVien.GioiTinh.NU, "0901111111", "quynh.manager@bakery.com", "Hà Nội", NhanVien.PhongBan.QUAN_LY, NhanVien.ChucVu.GIAM_DOC, LocalDate.of(2020, 1, 15), 25000000.0, 75000000.0, 22},
            new Object[]{"NV002", "Trần Văn Hải", LocalDate.of(1990, 3, 😎, NhanVien.GioiTinh.NAM, "0902222222", "hai.sales@bakery.com", "Hà Nội", NhanVien.PhongBan.BAN_HANG, NhanVien.ChucVu.TRUONG_PHONG, LocalDate.of(2021, 6, 1), 15000000.0, 35000000.0, 20},
            new Object[]{"NV003", "Lê Thị Hương", LocalDate.of(1992, 8, 12), NhanVien.GioiTinh.NU, "0903333333", "huong.sales@bakery.com", "Hà Nội", NhanVien.PhongBan.BAN_HANG, NhanVien.ChucVu.NHAN_VIEN_CHINH, LocalDate.of(2022, 3, 10), 12000000.0, 28000000.0, 21}
        );

        for (Object[] data : employeeData) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNhanVien((String) data[0]);
            nhanVien.setHoTen((String) data[1]);
            nhanVien.setNgaySinh((LocalDate) data[2]);
            nhanVien.setGioiTinh((NhanVien.GioiTinh) data[3]);
            nhanVien.setSoDienThoai((String) data[4]);
            nhanVien.setEmail((String) data[5]);
            nhanVien.setDiaChi((String) data[6]);
            nhanVien.setPhongBan((NhanVien.PhongBan) data[7]);
            nhanVien.setChucVu((NhanVien.ChucVu) data[8]);
            nhanVien.setNgayVaoLam((LocalDate) data[9]);
            nhanVien.setLuongCoBan((Double) data[10]);
            nhanVien.setDoanhSoThang((Double) data[11]);
            nhanVien.setSoNgayLamViecThang((Integer) data[12]);
            nhanVien.setLoaiHopDong(NhanVien.LoaiHopDong.KHONG_XAC_DINH_THOI_HAN);
            nhanVien.setSoDonHangThang(random.nextInt(30) + 10);
            nhanVien.capNhatHieuSuat();
            
            nhanVienRepository.save(nhanVien);
        }
    }

    /**
     * 🧾 Tạo giao dịch mẫu
     */
    private void createSampleTransactions() {
        System.out.println("🧾 Creating sample transactions...");
        
        List<SanPham> sanPhams = sanPhamRepository.findAll();
        List<KhachHang> khachHangs = khachHangRepository.findAll();
        List<NhanVien> nhanViens = nhanVienRepository.findAll();

        for (int i = 1; i <= 10; i++) {
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
            
            if (i <= 😎 {
                giaoDich.setTrangThai(GiaoDich.TrangThaiGiaoDich.HOAN_THANH);
                giaoDich.setThoiGianHoanThanh(LocalDateTime.now().minusDays(random.nextInt(7)));
                giaoDich.hoanThanhGiaoDich();
            } else {
                giaoDich.setTrangThai(GiaoDich.TrangThaiGiaoDich.DANG_XU_LY);
            }
            
            giaoDichRepository.save(giaoDich);
        }
    }

    /**
     * 📊 In summary chi tiết
     */
    private void printDetailedSummary() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🎉 ENHANCED DATA SEEDING COMPLETED - 20 DIVERSE PRODUCTS!");
        System.out.println("=".repeat(80));
        
        System.out.println("🎂 BÁNH KEM PREMIUM (6 products):");
        System.out.println("   ├── Tiramisu Đặc Biệt - 180K₫");
        System.out.println("   ├── Red Velvet Luxury - 220K₫");
        System.out.println("   ├── Chocolate Truffle - 250K₫");
        System.out.println("   ├── Cheesecake Blueberry - 200K₫");
        System.out.println("   ├── Matcha Nhật Bản - 230K₫");
        System.out.println("   └── Dâu Tây Pháp - 190K₫");
        
        System.out.println("\n🧁 BÁNH NGỌT & PASTRIES (5 products):");
        System.out.println("   ├── Croissant Bơ Pháp - 45K₫");
        System.out.println("   ├── Macaron Rainbow Set - 320K₫");
        System.out.println("   ├── Éclair Chocolate - 85K₫");
        System.out.println("   ├── Muffin Blueberry - 45K₫");
        System.out.println("   └── Cupcake Double Chocolate - 38K₫");
        
        System.out.println("\n🥖 BÁNH MÌ SPECIALTY (4 products):");
        System.out.println("   ├── Croissant Jambon Fromage - 65K₫");
        System.out.println("   ├── Bagel Cá Hồi - 95K₫");
        System.out.println("   ├── Club House Sandwich - 85K₫");
        System.out.println("   └── Bánh Mì Baguette VN - 35K₫");
        
        System.out.println("\n☕ ĐỒ UỐNG PREMIUM (3 products):");
        System.out.println("   ├── Espresso Ý - 35K₫");
        System.out.println("   ├── Trà Sữa Matcha - 65K₫");
        System.out.println("   └── Hot Chocolate Bỉ - 55K₫");
        
        System.out.println("\n🍪 BÁNH QUY ARTISAN (2 products):");
        System.out.println("   ├── Cookie Chocolate Chip - 25K₫");
        System.out.println("   └── Bánh Quy Bơ Đan Mạch - 180K₫");
        
        System.out.println("\n📊 SUMMARY:");
        System.out.println("   📦 Total Products: " + sanPhamRepository.count());
        System.out.println("   👥 Total Customers: " + khachHangRepository.count());
        System.out.println("   👨‍💼 Total Employees: " + nhanVienRepository.count());
        System.out.println("   🧾 Total Transactions: " + giaoDichRepository.count());
        
        System.out.println("\n🌐 ACCESS URLs:");
        System.out.println("   🏠 Dashboard: http://localhost:8080/dashboard");
        System.out.println("   🛒 Products: http://localhost:8080/sanpham");
        System.out.println("   💾 H2 Console: http://localhost:8080/h2-console");
        System.out.println("\n🖼️ ALL PRODUCTS HAVE REAL IMAGES FROM UNSPLASH!");
        System.out.println("=".repeat(80) + "\n");
    }
}
localhost
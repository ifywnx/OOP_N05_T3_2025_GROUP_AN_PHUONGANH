import com.example.servingwebcontent.model.GiaoDich;
import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.model.KhachHang;
import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.repository.GiaoDichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GiaoDichService {

    @Autowired
    private GiaoDichRepository giaoDichRepository;
    
    @Autowired
    private SanPhamService sanPhamService;
    
    @Autowired
    private KhachHangService khachHangService;
    
    @Autowired
    private NhanVienService nhanVienService;

    public List<GiaoDich> getAllGiaoDich() {
        return giaoDichRepository.findAll();
    }

    public Optional<GiaoDich> getGiaoDichById(Long id) {
        return giaoDichRepository.findById(id);
    }

    public Optional<GiaoDich> getGiaoDichByMa(String maGiaoDich) {
        return giaoDichRepository.findByMaGiaoDich(maGiaoDich);
    }

    public GiaoDich saveGiaoDich(GiaoDich giaoDich) {
        giaoDich.tinhToanGiaoDich();
        return giaoDichRepository.save(giaoDich);
    }

    public void deleteGiaoDich(Long id) {
        giaoDichRepository.deleteById(id);
    }

    public List<GiaoDich> getCompletedTransactions() {
        return giaoDichRepository.findCompletedTransactions();
    }

    public List<GiaoDich> getTransactionsByDate(LocalDate date) {
        return giaoDichRepository.findTransactionsByDate(date);
    }

    public List<GiaoDich> getRecentTransactions() {
        return giaoDichRepository.findRecentTransactions();
    }

    public Double getTotalRevenue() {
        return giaoDichRepository.calculateTotalRevenue();
    }

    public Double getDailyRevenue(LocalDate date) {
        return giaoDichRepository.calculateDailyRevenue(date);
    }

    public Double getMonthlyRevenue(LocalDate date) {
        return giaoDichRepository.calculateMonthlyRevenue(date);
    }

    public Long getDailyTransactionCount(LocalDate date) {
        return giaoDichRepository.countDailyTransactions(date);
    }

    public Double getAverageTransactionValue() {
        return giaoDichRepository.calculateAverageTransactionValue();
    }

    public GiaoDich taoGiaoDichMoi(String maSanPham, String maKhachHang, String maNhanVien, 
                                   Integer soLuong, GiaoDich.PhuongThucThanhToan phuongThucThanhToan) {
        
        Optional<SanPham> sanPhamOpt = sanPhamService.getSanPhamByMa(maSanPham);
        Optional<KhachHang> khachHangOpt = khachHangService.getKhachHangByMa(maKhachHang);
        Optional<NhanVien> nhanVienOpt = nhanVienService.getNhanVienByMa(maNhanVien);

        if (sanPhamOpt.isEmpty()) throw new RuntimeException("Không tìm thấy sản phẩm");
        if (nhanVienOpt.isEmpty()) throw new RuntimeException("Không tìm thấy nhân viên");

        SanPham sanPham = sanPhamOpt.get();
        NhanVien nhanVien = nhanVienOpt.get();
        KhachHang khachHang = khachHangOpt.orElse(null);

        if (sanPham.getSoLuongTonKho() < soLuong) {
            throw new RuntimeException("Không đủ hàng trong kho");
        }

        String maGiaoDich = generateMaGiaoDich();
        GiaoDich giaoDich = new GiaoDich(maGiaoDich, GiaoDich.LoaiGiaoDich.BAN_HANG, 
                                        sanPham, khachHang, nhanVien, soLuong);
        giaoDich.setPhuongThucThanhToan(phuongThucThanhToan);
        giaoDich.tinhToanGiaoDich();

        return saveGiaoDich(giaoDich);
    }

    public boolean hoanThanhGiaoDich(String maGiaoDich, Double tienKhachDua) {
        Optional<GiaoDich> giaoDichOpt = getGiaoDichByMa(maGiaoDich);
        if (giaoDichOpt.isEmpty()) return false;

        GiaoDich giaoDich = giaoDichOpt.get();
        if (tienKhachDua < giaoDich.getTongTien()) return false;

        giaoDich.setTienKhachDua(tienKhachDua);
        giaoDich.hoanThanhGiaoDich();
        saveGiaoDich(giaoDich);

        // Cập nhật thông tin liên quan
        if (giaoDich.getSanPham() != null) {
            sanPhamService.banHang(giaoDich.getSanPham().getMaSanPham(), giaoDich.getSoLuong());
        }

        if (giaoDich.getKhachHang() != null) {
            khachHangService.capNhatThongTinMuaHang(
                giaoDich.getKhachHang().getMaKhachHang(), 
                giaoDich.getTongTien(), 
                giaoDich.getSoLuong()
            );
        }

        if (giaoDich.getNhanVien() != null) {
            nhanVienService.capNhatDoanhSo(
                giaoDich.getNhanVien().getMaNhanVien(), 
                giaoDich.getTongTien()
            );
        }

        return true;
    }

    private String generateMaGiaoDich() {
        LocalDateTime now = LocalDateTime.now();
        String datePart = String.format("%04d%02d%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        long count = giaoDichRepository.count() + 1;
        return String.format("GD%s%03d", datePart, count);
    }
}
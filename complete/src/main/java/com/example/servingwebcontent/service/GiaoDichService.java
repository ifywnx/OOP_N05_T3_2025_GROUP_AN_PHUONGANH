package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.GiaoDich;
import com.example.servingwebcontent.model.SanPham;
import com.example.servingwebcontent.repository.GiaoDichRepository;
import com.example.servingwebcontent.repository.SanPhamRepository;
import com.example.servingwebcontent.service.discount.DiscountPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GiaoDichService {

    private final GiaoDichRepository giaoDichRepo;
    private final SanPhamRepository sanPhamRepo;

    public GiaoDichService(GiaoDichRepository giaoDichRepo, SanPhamRepository sanPhamRepo) {
        this.giaoDichRepo = giaoDichRepo;
        this.sanPhamRepo = sanPhamRepo;
    }

    /** Dòng hàng: sản phẩm + số lượng + đơn giá (đơn giá lấy từ form, vì SanPham không có giá trong model) */
    public static class Item {
        private final SanPham sp;
        private final int soLuong;
        private final double donGia;

        public Item(SanPham sp, int soLuong, double donGia) {
            this.sp = sp;
            this.soLuong = soLuong;
            this.donGia = donGia;
        }
        public SanPham getSp() { return sp; }
        public int getSoLuong() { return soLuong; }
        public double getDonGia() { return donGia; }
    }

    /** Tính tổng trước giảm giá */
    public double tinhSubtotal(List<Item> items) {
        return items.stream()
                .mapToDouble(i -> i.getDonGia() * i.getSoLuong())
                .sum();
    }

    /** Tổng sau giảm giá (đa hình qua DiscountPolicy) */
    public double tinhTongTien(List<Item> items, DiscountPolicy policy) {
        double subtotal = tinhSubtotal(items);
        return policy.apply(subtotal);
    }

    /** Tạo giao dịch: kiểm kho -> trừ kho -> tính tiền -> lưu GD */
    @Transactional
    public GiaoDich taoGiaoDich(GiaoDich gd, List<Item> items, DiscountPolicy policy) {
        // Kiểm kho & trừ kho
        for (Item it : items) {
            SanPham sp = sanPhamRepo.findById(it.getSp().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại: " + it.getSp().getId()));
            int conLai = sp.getSoLuong() - it.getSoLuong();
            if (conLai < 0) {
                throw new IllegalArgumentException("Kho không đủ cho SP: " + sp.getTenSanPham() + " (còn " + sp.getSoLuong() + ")");
            }
            sp.setSoLuong(conLai);
            sanPhamRepo.save(sp);
        }

        double tong = tinhTongTien(items, policy);
        gd.setTongTien(tong);
        return giaoDichRepo.save(gd);
    }

    // Các hàm tiện ích cho controller
    public List<GiaoDich> findAll() { return giaoDichRepo.findAll(); }
    public GiaoDich findById(Long id) { return giaoDichRepo.findById(id).orElse(null); }
    public void deleteById(Long id) { giaoDichRepo.deleteById(id); }
}

package com.example.servingwebcontent.service.discount;

/**
 * Interface cho chiến lược giảm giá.
 * Dùng để áp dụng Đa hình (Polymorphism) trong tính toán giao dịch.
 */
public interface DiscountPolicy {
    /**
     * Tính tổng tiền sau khi áp dụng giảm giá.
     * @param amount tổng tiền ban đầu
     * @return tổng tiền sau giảm
     */
    double apply(double amount);
}

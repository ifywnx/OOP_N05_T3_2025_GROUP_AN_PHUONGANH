package com.example.servingwebcontent.service.discount;

/**
 * Giảm giá theo mùa, dịp lễ/tết.
 */
public class SeasonalDiscount implements DiscountPolicy {
    @Override
    public double apply(double amount) {
        return amount * 0.85; // giảm 15%
    }
}

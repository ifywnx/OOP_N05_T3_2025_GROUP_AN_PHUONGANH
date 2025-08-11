package com.example.servingwebcontent.service.discount;

/**
 * Giảm giá cho khách VIP.
 */
public class VipDiscount implements DiscountPolicy {
    @Override
    public double apply(double amount) {
        return amount * 0.9; // giảm 10%
    }
}

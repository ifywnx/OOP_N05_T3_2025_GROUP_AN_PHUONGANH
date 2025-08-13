package com.example.servingwebcontent.service.discount;

/**
 * Không giảm giá.
 */
public class NoDiscount implements DiscountPolicy {
    @Override
    public double apply(double amount) {
        return amount;
    }
}

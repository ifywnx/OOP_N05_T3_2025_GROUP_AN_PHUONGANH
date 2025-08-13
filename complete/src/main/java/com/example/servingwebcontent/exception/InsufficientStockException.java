package com.example.servingwebcontent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientStockException extends BusinessException {
    
    public InsufficientStockException(String productName, Integer requestedQuantity, Integer availableQuantity) {
        super(String.format("Không đủ hàng trong kho! Sản phẩm '%s': Yêu cầu %d, còn lại %d", 
                           productName, requestedQuantity, availableQuantity));
    }
}
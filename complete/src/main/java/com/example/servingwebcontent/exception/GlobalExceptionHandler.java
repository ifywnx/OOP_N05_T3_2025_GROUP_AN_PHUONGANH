// src/main/java/com/example/servingwebcontent/exception/GlobalExceptionHandler.java
package com.example.servingwebcontent.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
// removed: import org.springframework.ui.Model;
// removed: import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 🛡️ Global Exception Handler cho Bakery Management System
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        logger.warn("🔍 Not Found Exception: {} - URL: {}", ex.getMessage(), request.getRequestURL());
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("requestUrl", request.getRequestURL());
        mav.addObject("pageTitle", "Không Tìm Thấy - 404");
        return mav;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBusinessException(BusinessException ex, HttpServletRequest request) {
        logger.warn("💼 Business Exception: {} - URL: {}", ex.getMessage(), request.getRequestURL());
        ModelAndView mav = new ModelAndView("error/business");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("errorType", "Business Error");
        mav.addObject("pageTitle", "Lỗi Nghiệp Vụ");
        return mav;
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest request) {
        logger.warn("📦 Insufficient Stock Exception: {} - URL: {}", ex.getMessage(), request.getRequestURL());
        ModelAndView mav = new ModelAndView("error/stock");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("errorType", "Inventory Error");
        mav.addObject("pageTitle", "Không Đủ Hàng Trong Kho");
        return mav;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.warn("✅ Validation Exception - URL: {}", request.getRequestURL());
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            validationErrors.put(error.getField(), error.getDefaultMessage())
        );
        ModelAndView mav = new ModelAndView("error/validation");
        mav.addObject("validationErrors", validationErrors);
        mav.addObject("errorMessage", "Dữ liệu nhập vào không hợp lệ");
        mav.addObject("pageTitle", "Lỗi Validation");
        return mav;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        logger.warn("🔒 Constraint Violation Exception: {} - URL: {}", ex.getMessage(), request.getRequestURL());
        ModelAndView mav = new ModelAndView("error/validation");
        mav.addObject("errorMessage", "Vi phạm ràng buộc dữ liệu: " + ex.getMessage());
        mav.addObject("errorType", "Constraint Violation");
        mav.addObject("pageTitle", "Lỗi Ràng Buộc Dữ Liệu");
        return mav;
    }

    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleDataAccessException(org.springframework.dao.DataAccessException ex, HttpServletRequest request) {
        logger.error("🗄️ Database Access Exception: {} - URL: {}", ex.getMessage(), request.getRequestURL(), ex);
        ModelAndView mav = new ModelAndView("error/database");
        mav.addObject("errorMessage", "Có lỗi xảy ra khi truy cập cơ sở dữ liệu. Vui lòng thử lại sau.");
        mav.addObject("errorType", "Database Error");
        mav.addObject("pageTitle", "Lỗi Cơ Sở Dữ Liệu");
        return mav;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        logger.warn("⚠️ Illegal Argument Exception: {} - URL: {}", ex.getMessage(), request.getRequestURL());
        ModelAndView mav = new ModelAndView("error/business");
        mav.addObject("errorMessage", "Tham số không hợp lệ: " + ex.getMessage());
        mav.addObject("errorType", "Invalid Parameter");
        mav.addObject("pageTitle", "Tham Số Không Hợp Lệ");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        logger.error("🔥 Internal Server Error: {} - URL: {}", ex.getMessage(), request.getRequestURL(), ex);
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("errorMessage", "Đã xảy ra lỗi hệ thống. Vui lòng liên hệ quản trị viên.");
        mav.addObject("errorType", "Internal Server Error");
        mav.addObject("exception", ex.getClass().getSimpleName());
        mav.addObject("pageTitle", "Lỗi Hệ Thống - 500");
        return mav;
    }

    /** 🎨 Helper cho AJAX; tạm không dùng nên ẩn cảnh báo */
    @SuppressWarnings("unused")
    private Map<String, Object> createErrorResponse(String message, String type, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        errorResponse.put("type", type);
        errorResponse.put("status", status.value());
        errorResponse.put("timestamp", System.currentTimeMillis());
        return errorResponse;
    }
}

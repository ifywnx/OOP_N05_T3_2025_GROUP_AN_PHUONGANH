package com.example.servingwebcontent.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 🛡️ Global Exception Handler cho Bakery Management System
 * 
 * Xử lý tất cả exception trong ứng dụng một cách thống nhất:
 * - Business exceptions với thông báo user-friendly
 * - Validation errors với chi tiết cụ thể
 * - System errors với logging đầy đủ
 * - 404 errors với giao diện đẹp
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 🔍 Xử lý NotFoundException (404)
     */
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

    /**
     * 💼 Xử lý BusinessException (400)
     */
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

    /**
     * 📦 Xử lý InsufficientStockException
     */
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

    /**
     * ✅ Xử lý Validation Errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.warn("✅ Validation Exception - URL: {}", request.getRequestURL());
        
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        });
        
        ModelAndView mav = new ModelAndView("error/validation");
        mav.addObject("validationErrors", validationErrors);
        mav.addObject("errorMessage", "Dữ liệu nhập vào không hợp lệ");
        mav.addObject("pageTitle", "Lỗi Validation");
        return mav;
    }

    /**
     * 🔒 Xử lý Constraint Violation Exception
     */
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

    /**
     * 🗄️ Xử lý Database Access Exception
     */
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

    /**
     * 🔐 Xử lý Access Denied Exception
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex, HttpServletRequest request) {
        logger.warn("🔐 Access Denied Exception: {} - URL: {}", ex.getMessage(), request.getRequestURL());
        
        ModelAndView mav = new ModelAndView("error/403");
        mav.addObject("errorMessage", "Bạn không có quyền truy cập tài nguyên này");
        mav.addObject("pageTitle", "Từ Chối Truy Cập - 403");
        return mav;
    }

    /**
     * ⚠️ Xử lý IllegalArgumentException
     */
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

    /**
     * 🔥 Xử lý tất cả exception khác (500)
     */
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

    /**
     * 🎨 Helper method để tạo error response cho AJAX requests
     */
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
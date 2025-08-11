package com.example.servingwebcontent.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404: Không tìm thấy dữ liệu (nếu bạn chủ động ném NoSuchElement hoặc NotFoundException)
    @ExceptionHandler({NoSuchElementException.class, NotFoundException.class})
    public String handleNotFound(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage() == null ? "Không tìm thấy dữ liệu" : ex.getMessage());
        return "error/404";
    }

    // 404: URL không có handler (truy cập sai đường dẫn)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandler(NoHandlerFoundException ex, Model model) {
        model.addAttribute("message", "Đường dẫn không tồn tại: " + ex.getRequestURL());
        return "error/404";
    }

    // Vi phạm unique / ràng buộc DB (mã bị trùng…)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDuplicate(DataIntegrityViolationException ex, Model model) {
        model.addAttribute("message", "Dữ liệu vi phạm ràng buộc (có thể trùng mã). Vui lòng kiểm tra lại.");
        return "error/500";
    }

    // Lỗi validate mức method (ít gặp với Thymeleaf vì đã có BindingResult)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute("message", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại các trường đã nhập.");
        return "error/500";
    }

    // Bad request theo nghiệp vụ (ví dụ kho không đủ…)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }

    // Chốt: mọi lỗi khác
    @ExceptionHandler(Exception.class)
    public String handleServerError(Exception ex, Model model) {
        model.addAttribute("message", "Đã xảy ra lỗi không mong muốn.");
        return "error/500";
    }
}

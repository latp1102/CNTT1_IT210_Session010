package com.session10miniproject.validator;

import com.session10miniproject.dto.BorrowRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, BorrowRequestDTO> {
    @Override
    public boolean isValid(BorrowRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }
        if (dto.getBorrowDate() == null || dto.getReturnDate() == null) {
            return true;
        }
        return !dto.getReturnDate()
                .isBefore(dto.getBorrowDate());
    }
}


public class ValidDateRangeValidator
        implements ConstraintValidator<ValidDateRange, BorrowRequestDTO> {
// Tạo class validator cho annotation @ValidDateRange
// Validator này áp dụng kiểm tra trên object BorrowRequestDTO

    @Override
    // Override phương thức bắt buộc khi implement ConstraintValidator
    public boolean isValid(BorrowRequestDTO dto, ConstraintValidatorContext context) {
        // Method này sẽ tự động chạy khi Spring thực hiện validation
        if (dto == null) {
            return true;
        }
        // Nếu DTO null → bỏ qua kiểm tra
        // Tránh lỗi NullPointerException
        // Validator khác sẽ xử lý null nếu cần
        if (dto.getBorrowDate() == null || dto.getReturnDate() == null) {
            return true;
        }
        // Nếu ngày mượn hoặc ngày trả chưa nhập
        // thì không kiểm tra logic khoảng ngày
        // vì @NotNull sẽ xử lý lỗi thiếu dữ liệu
        return !dto.getReturnDate().isBefore(dto.getBorrowDate());
        // Kiểm tra logic thời gian:
        // returnDate KHÔNG được đứng trước borrowDate
        // isBefore() = ngày trả < ngày mượn
        // dấu ! nghĩa là:
        // ngày trả >= ngày mượn → hợp lệ
    }
}
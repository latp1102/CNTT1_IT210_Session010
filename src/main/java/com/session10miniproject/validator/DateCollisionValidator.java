package com.session10miniproject.validator;

import com.session10miniproject.dto.BorrowRequestDTO;
import com.session10miniproject.model.BorrowRequest;
import com.session10miniproject.repository.BorrowRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateCollisionValidator implements ConstraintValidator<CheckDateCollision, BorrowRequestDTO> {
    @Autowired
    private BorrowRepository borrowRepository;
    @Override
    public boolean isValid(BorrowRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }
        if (dto.getEquipmentId() == null || dto.getBorrowDate() == null || dto.getReturnDate() == null) {
            return true;
        }
        List<BorrowRequest> existed = borrowRepository.findByEquipment(dto.getEquipmentId());
        for (BorrowRequest r : existed) {
            boolean collision = !dto.getBorrowDate().isAfter(r.getReturnDate()) && !dto.getReturnDate().isBefore(r.getBorrowDate());
            if (collision) {
                return false;
            }
        }
        return true;
    }
}

@Component
// Khai báo validator là một Spring component để có thể inject Repository
public class DateCollisionValidator
        implements ConstraintValidator<CheckDateCollision, BorrowRequestDTO> {
// Class validator kiểm tra annotation @CheckDateCollision
// Validator áp dụng cho object kiểu BorrowRequestDTO

    @Autowired
    // Spring tự động inject BorrowRepository
    private BorrowRepository borrowRepository;
    // Repository dùng để truy vấn các lượt mượn đã có

    @Override
    // Override method bắt buộc của ConstraintValidator
    public boolean isValid(BorrowRequestDTO dto, ConstraintValidatorContext context) {
        // Hàm được gọi tự động khi validation chạy
        if (dto == null) {
            return true;
        }
        // Nếu DTO null → không kiểm tra → xem là hợp lệ
        // (tránh lỗi NullPointerException)
        if (dto.getEquipmentId() == null || dto.getBorrowDate() == null || dto.getReturnDate() == null) {
            return true;
        }
        // Nếu thiếu dữ liệu cần thiết thì bỏ qua
        // vì @NotNull sẽ xử lý lỗi này ở validator khác
        List<BorrowRequest> existed =
                borrowRepository.findByEquipment(dto.getEquipmentId());
        // Lấy toàn bộ danh sách các lần mượn thiết bị theo equipmentId
        // từ database
        for (BorrowRequest r : existed) {
            // Duyệt từng lịch mượn đã tồn tại
            boolean collision = !dto.getBorrowDate().isAfter(r.getReturnDate()) && !dto.getReturnDate().isBefore(r.getBorrowDate());
            // Kiểm tra hai khoảng thời gian có bị trùng nhau không
            //
            // Điều kiện trùng:
            // borrowDate mới <= returnDate cũ
            // AND
            // returnDate mới >= borrowDate cũ
            if (collision) {
                return false;
            }
            // Nếu phát hiện trùng lịch → validation fail
        }
        return true;
        // Không có lịch nào trùng → dữ liệu hợp lệ
    }
}
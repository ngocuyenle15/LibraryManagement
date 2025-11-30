package com.ptit.librarymanagement.dto;



import com.ptit.librarymanagement.common.enums.Role;
import com.ptit.librarymanagement.common.validation.groupvalidation.CreateAccount;
import com.ptit.librarymanagement.common.validation.groupvalidation.ChangePassword;
import com.ptit.librarymanagement.common.validation.groupvalidation.UpdateAccount;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountDTO {
    private Integer id;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{2,15}$",
             message = "Vui lòng nhập username hợp lệ!",
             groups = {CreateAccount.class, UpdateAccount.class, Default.class})
    private String userName;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "Vui lòng nhập mật khẩu theo đúng định dạng!",
             groups = {CreateAccount.class, ChangePassword.class})
    // chỉ validate khi tạo và thay pass, view update không cho thay đổi pass
    // tạo chức năng cấp lại pass word
    private String password;

    @NotNull (message = "Role không được để trống!",
              groups = {CreateAccount.class, UpdateAccount.class})
    private Role role;
}



// chưa kiểm tra user trong db có tồn tại ko khi tạo


/*
Chỉ chứa chữ cái, số, dấu gạch dưới _.

Độ dài từ 3 đến 16 ký tự.

Bắt đầu bằng chữ cái.

Không chứa khoảng trắng hay ký tự đặc biệt khác.
*
*/
/*
    ít nhất 8 ký tự
    chứa ít nhất 1 chữ hoa
    chứa ít nhất 1 chữ thường
    chứa ít nhất 1 chữ số
    chứa ít nhất 1 ký tự đặc biệt
* */

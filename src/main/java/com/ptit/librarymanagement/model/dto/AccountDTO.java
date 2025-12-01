package com.ptit.librarymanagement.model.dto;



import com.ptit.librarymanagement.common.enums.Role;
import com.ptit.librarymanagement.common.validation.groupvalidation.CreateAccount;
import com.ptit.librarymanagement.common.validation.groupvalidation.ChangePassword;
import com.ptit.librarymanagement.common.validation.groupvalidation.UpdateAccount;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;


public class AccountDTO {
    private Integer id;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{2,15}$",
             message = "Vui lòng nhập username hợp lệ!",
             groups = {CreateAccount.class, UpdateAccount.class, Default.class})
    private String userName;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "Vui lòng nhập mật khẩu theo đúng định dạng!",
             groups = {CreateAccount.class, ChangePassword.class})

    private String password;

    @NotNull (message = "Role không được để trống!",
              groups = {CreateAccount.class, UpdateAccount.class})
    private Role role;

    public AccountDTO(Integer id, String userName, String password, Role role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public AccountDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static AccountDTOBuilder builder() {
        return new AccountDTOBuilder();
    }

    public static class AccountDTOBuilder {
        private Integer id;
        private String userName;
        private String password;
        private Role role;

        AccountDTOBuilder() {
        }

        public AccountDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public AccountDTOBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public AccountDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AccountDTOBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public AccountDTO build() {
            return new AccountDTO(this.id, this.userName, this.password, this.role);
        }

        public String toString() {
            Integer var10000 = this.id;
            return "AccountDTO.AccountDTOBuilder(id=" + var10000 + ", userName=" + this.userName + ", password=" + this.password + ", role=" + String.valueOf(this.role) + ")";
        }
    }

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

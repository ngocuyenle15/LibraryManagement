package com.ptit.librarymanagement.dto;

import com.ptit.librarymanagement.common.validation.customvalidation.datevaildation.DateValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class
StaffDTO {
    private Integer id;
    @NotNull (message = "Tên không được để trống!")
    @NotBlank (message = "Tên không được để trống!")
    private String firstName;

    @NotNull (message = "Họ không được để trống!")
    @NotBlank (message = "Họ không được để trống!")
    private String lastName;

    @NotNull (message = "Giới tính không được để trống!")
    private Boolean gender;

    @DateValid (message = "Ngày sinh không hợp lệ!")
    private Date birth;

    @NotNull (message = "Địa chỉ không được để trống!")
    @NotBlank (message = "Địa chỉ không được để trống!")
    private String location;

    @NotNull (message = "Lương không được để trống!")
    private Double salary = 0.0;


    @Pattern(regexp = "^(0[3|5|7|8|9])[0-9]{8}$", message = "Vui lòng nhập số điện thoại hợp lệ!")
    private String phone;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",  message = "Vui lòng nhập email hợp lệ!")
    private String email;


    @Valid
    private AccountDTO account;

    public Object[] getRow () {
        return new Object[]{id, getFullName(), gender ? "Nam" : "Nữ", birth, location, String.format("%.0f", salary), phone, email, account.getUserName(), account.getRole().getRoleName()};
    }

    public String getFullName () {
        return String.format("%s %s", firstName, lastName);
    }
}

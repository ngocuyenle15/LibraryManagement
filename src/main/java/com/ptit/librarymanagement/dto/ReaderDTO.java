package com.ptit.librarymanagement.dto;

import com.ptit.librarymanagement.common.validation.customvalidation.comomdatevaildation.DateValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReaderDTO {
    private Integer id;

    @NotNull (message = "Tên không được để trống!")
    @NotBlank (message = "Tên không được để trống!")
    private String firstName;

    @NotNull (message = "Họ không được để trống!")
    @NotBlank (message = "Họ không được để trống!")
    private String lastName;

    @NotNull (message = "Giới tính không được để trống!")
    private Boolean gender;

    @NotNull (message = "Địa chỉ không được để trống!")
    @NotBlank (message = "Địa chỉ không được để trống!")
    private String location;

    @DateValid(message = "Ngày sinh không hợp lệ!")
    private Date birth;


    @Pattern(regexp = "^(0[3|5|7|8|9])[0-9]{8}$", message = "Vui lòng nhập số điện thoại hợp lệ!")
    private String phone;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Vui lòng nhập email hợp lệ!")
    private String email;

    // todo: nên bỏ field này
    private String note = "";

    // todo: check lại trong view
    private List<BorrowCardDTO> borrowCards = new ArrayList<>();

    public String getFullName () {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public Object[] getRow () {
        return new Object[] {id, String.format("%s %s", firstName, lastName), gender == true ? "Nam" : "Nữ", location, birth, phone, email};
    }


}

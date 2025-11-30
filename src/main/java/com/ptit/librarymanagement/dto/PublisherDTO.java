package com.ptit.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PublisherDTO {
    private Integer id;

    @NotNull (message = "Tên nhà xuất bản không được để trống!")
    @NotBlank (message = "Tên nhà xuất bản không được để trống!")
    private String name;

    @NotNull (message = "Địa chỉ không được để trống!")
    @NotBlank (message = "Địa chỉ không được để trống!")
    private String location;

    @NotNull (message = "Số điện thoại không được để trống!")
    @NotBlank (message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^(84|0[3|5|7|8|9])[0-9]{8}$", message = "Vui lòng nhập số điện thoại hợp lệ!") /*Tìm regex chẩn hơn*/
    private String phone;

    @NotNull (message = "Email không được để trống!")
    @NotBlank (message = "Email không được để trống!")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Vui lòng nhập email hợp lệ!")
    private String email = "";
    @Override
    public String toString() {
        return this.getName();
    }
    public Object[] getRow () {
        return new Object[] {id, name, location, phone, email};
    }

}


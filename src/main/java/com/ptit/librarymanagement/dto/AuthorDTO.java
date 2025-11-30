package com.ptit.librarymanagement.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.math3.analysis.function.Sin;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorDTO {
    private Integer id;

    @NotNull (message = "Tên không được để trống!")
    @NotBlank (message = "Tên không được để trống!")
    private String firstName;

    @NotNull (message = "Họ không được để trống!")
    @NotBlank (message = "Họ không được để trống!")
    private String lastName;

    @NotNull (message = "Giới tính không được để trống!")
    private Boolean gender;

    @Pattern (regexp = "^(19|20)\\d{2}$", message = "Vui lòng nhập năm sinh hợp lệ!")
    private String yearOfBirth;



    // todo: nên bỏ field này
    private String description = "";


    public Object[] getRow () {
        return new Object[] {id, getFullName(), gender ? "Nam" : "Nữ", yearOfBirth};
    }

    public String getFullName () {
        return String.format("%s %s",firstName ,lastName);
    }




}

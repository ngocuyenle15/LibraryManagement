package com.ptit.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedList;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {
    private Integer id;
    @NotNull (message = "Tên danh mục không được để trống!")
    @NotBlank (message = "Tên danh mục không được để trống!")
    private String name;


    public Object[] getRow () {
        return new Object[] {this.id, this.getName()};
    }
}

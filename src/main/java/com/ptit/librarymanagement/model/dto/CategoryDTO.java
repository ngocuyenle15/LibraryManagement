package com.ptit.librarymanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class CategoryDTO {
    private Integer id;
    @NotNull (message = "Tên danh mục không được để trống!")
    @NotBlank (message = "Tên danh mục không được để trống!")
    private String name;

    public CategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO() {
    }

    public static CategoryDTOBuilder builder() {
        return new CategoryDTOBuilder();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object[] getRow () {
        return new Object[] {this.id, this.getName()};
    }

    public static class CategoryDTOBuilder {
        private Integer id;
        private String name;

        CategoryDTOBuilder() {
        }

        public CategoryDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public CategoryDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryDTO build() {
            return new CategoryDTO(this.id, this.name);
        }

        public String toString() {
            return "CategoryDTO.CategoryDTOBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}

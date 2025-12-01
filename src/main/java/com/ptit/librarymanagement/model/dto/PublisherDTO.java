package com.ptit.librarymanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


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

    public PublisherDTO() {
    }

    public PublisherDTO(Integer id, String name, String location, String phone, String email) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
    }
    public static PublisherDTOBuilder builder() {
        return new PublisherDTOBuilder();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class PublisherDTOBuilder {
        private Integer id;
        private String name;
        private String location;
        private String phone;
        private String email;

        PublisherDTOBuilder() {
        }

        public PublisherDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public PublisherDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PublisherDTOBuilder location(String location) {
            this.location = location;
            return this;
        }

        public PublisherDTOBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public PublisherDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PublisherDTO build() {
            return new PublisherDTO(this.id, this.name, this.location, this.phone, this.email);
        }

        public String toString() {
            return "PublisherDTO.PublisherDTOBuilder(id=" + this.id + ", name=" + this.name + ", location=" + this.location + ", phone=" + this.phone + ", email=" + this.email + ")";
        }
    }

}


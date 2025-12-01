package com.ptit.librarymanagement.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



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

    public AuthorDTO() {
    }

    public AuthorDTO(Integer id, String firstName, String lastName, Boolean gender, String yearOfBirth, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
        this.description = description;
    }
    public static AuthorDTOBuilder builder() {
        return new AuthorDTOBuilder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // todo: nên bỏ field này
    private String description = "";


    public Object[] getRow () {
        return new Object[] {id, getFullName(), gender ? "Nam" : "Nữ", yearOfBirth};
    }

    public String getFullName () {
        return String.format("%s %s",lastName ,firstName);
    }

    public static class AuthorDTOBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private Boolean gender;
        private String yearOfBirth;
        private String description;

        AuthorDTOBuilder() {
        }

        public AuthorDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public AuthorDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AuthorDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthorDTOBuilder gender(Boolean gender) {
            this.gender = gender;
            return this;
        }

        public AuthorDTOBuilder yearOfBirth(String yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
            return this;
        }

        public AuthorDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AuthorDTO build() {
            return new AuthorDTO(this.id, this.firstName, this.lastName, this.gender, this.yearOfBirth, this.description);
        }

        public String toString() {
            return "AuthorDTO.AuthorDTOBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", gender=" + this.gender + ", yearOfBirth=" + this.yearOfBirth + ", description=" + this.description + ")";
        }
    }




}

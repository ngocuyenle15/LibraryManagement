package com.ptit.librarymanagement.model.dto;

import com.ptit.librarymanagement.common.validation.customvalidation.datevaildation.DateValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    public ReaderDTO() {
    }

    public ReaderDTO(Integer id, String firstName, String lastName, Boolean gender, String location, Date birth, String phone, String email, String note, List<BorrowCardDTO> borrowCards) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.location = location;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.borrowCards = borrowCards;
    }
    public static ReaderDTOBuilder builder() {
        return new ReaderDTOBuilder();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<BorrowCardDTO> getBorrowCards() {
        return borrowCards;
    }

    public void setBorrowCards(List<BorrowCardDTO> borrowCards) {
        this.borrowCards = borrowCards;
    }

    public String getFullName () {
        return String.format("%s %s", this.lastName, this.firstName);
    }

    public Object[] getRow () {
        return new Object[] {id, String.format("%s %s",  lastName,firstName), gender == true ? "Nam" : "Nữ", location, birth, phone, email};
    }

    public static class ReaderDTOBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private Boolean gender;
        private String location;
        private Date birth;
        private String phone;
        private String email;
        private String note;
        private List<BorrowCardDTO> borrowCards;

        ReaderDTOBuilder() {
        }

        public ReaderDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ReaderDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ReaderDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ReaderDTOBuilder gender(Boolean gender) {
            this.gender = gender;
            return this;
        }

        public ReaderDTOBuilder location(String location) {
            this.location = location;
            return this;
        }

        public ReaderDTOBuilder birth(Date birth) {
            this.birth = birth;
            return this;
        }

        public ReaderDTOBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public ReaderDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ReaderDTOBuilder note(String note) {
            this.note = note;
            return this;
        }

        public ReaderDTOBuilder borrowCards(List<BorrowCardDTO> borrowCards) {
            this.borrowCards = borrowCards;
            return this;
        }

        public ReaderDTO build() {
            return new ReaderDTO(this.id, this.firstName, this.lastName, this.gender, this.location, this.birth, this.phone, this.email, this.note, this.borrowCards);
        }

        public String toString() {
            Integer var10000 = this.id;
            return "ReaderDTO.ReaderDTOBuilder(id=" + var10000 + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", gender=" + this.gender + ", location=" + this.location + ", birth=" + String.valueOf(this.birth) + ", phone=" + this.phone + ", email=" + this.email + ", note=" + this.note + ", borrowCards=" + String.valueOf(this.borrowCards) + ")";
        }
    }


}

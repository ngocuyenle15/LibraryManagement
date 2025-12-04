package com.ptit.librarymanagement.model.dto;

import com.ptit.librarymanagement.common.validation.customvalidation.datevaildation.BirthDateValid;
import com.ptit.librarymanagement.common.validation.customvalidation.datevaildation.DateValid;
import com.ptit.librarymanagement.common.validation.groupvalidation.CreateAccount;
import com.ptit.librarymanagement.common.validation.groupvalidation.CreateStaff;
import com.ptit.librarymanagement.common.validation.groupvalidation.UpdateAccount;
import com.ptit.librarymanagement.common.validation.groupvalidation.UpdateStaff;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;


import java.sql.Date;


public class StaffDTO {
    private Integer id;
    @NotNull (message = "Tên không được để trống!", groups = {UpdateStaff.class, CreateStaff.class})
    @NotBlank (message = "Tên không được để trống!", groups = {UpdateStaff.class, CreateStaff.class})
    private String firstName;

    @NotNull (message = "Họ không được để trống!", groups = {UpdateStaff.class, CreateStaff.class})
    @NotBlank (message = "Họ không được để trống!", groups = {UpdateStaff.class, CreateStaff.class})
    private String lastName;

    @NotNull (message = "Giới tính không được để trống!", groups = {UpdateStaff.class, CreateStaff.class})
    private Boolean gender;

    @DateValid (message = "Ngày sinh không hợp lệ!", groups = {UpdateStaff.class, CreateStaff.class})
    @BirthDateValid(age = 18, message = "Nhân viên phải từ 18 tuổi trở lên!", groups = {UpdateStaff.class, CreateStaff.class})
    private Date birth;

    @NotNull (message = "Địa chỉ không được để trống!", groups = {UpdateStaff.class, CreateStaff.class})
    @NotBlank (message = "Địa chỉ không được để trống!", groups = {UpdateStaff.class, CreateStaff.class})
    private String location;

    @NotNull (message = "Lương không được để trống!", groups = {UpdateStaff.class, CreateStaff.class})
    private Double salary = 0.0;


    @Pattern(regexp = "^(0[3|5|7|8|9])[0-9]{8}$", message = "Vui lòng nhập số điện thoại hợp lệ!", groups = {UpdateStaff.class, CreateStaff.class})
    private String phone;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",  message = "Vui lòng nhập email hợp lệ!", groups = {UpdateStaff.class, CreateStaff.class})
    private String email;


//    @ConvertGroup(from = CreateStaff.class, to = CreateAccount.class)
//    @ConvertGroup(from = UpdateStaff.class, to = UpdateAccount.class)
    @Valid
    @ConvertGroup.List({
            @ConvertGroup(from = CreateStaff.class, to = CreateAccount.class),
            @ConvertGroup(from = UpdateStaff.class, to = UpdateAccount.class)
    })
    private AccountDTO account;

    public StaffDTO(Integer id, String firstName, String lastName, Boolean gender, Date birth, String location, Double salary, String phone, String email, AccountDTO account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birth = birth;
        this.location = location;
        this.salary = salary;
        this.phone = phone;
        this.email = email;
        this.account = account;
    }

    public StaffDTO() {
    }
    public static StaffDTOBuilder builder() {
        return new StaffDTOBuilder();
    }

    public Object[] getRow () {
        return new Object[]{id, getFullName(), gender ? "Nam" : "Nữ", birth, location, String.format("%.0f", salary), phone, email, account.getUserName(), account.getRole().getRoleName()};
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
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

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public String getFullName () {
        return String.format("%s %s",  lastName, firstName);
    }

    public static class StaffDTOBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private Boolean gender;
        private Date birth;
        private String location;
        private Double salary;
        private String phone;
        private String email;
        private AccountDTO account;

        StaffDTOBuilder() {
        }

        public StaffDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public StaffDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StaffDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StaffDTOBuilder gender(Boolean gender) {
            this.gender = gender;
            return this;
        }

        public StaffDTOBuilder birth(Date birth) {
            this.birth = birth;
            return this;
        }

        public StaffDTOBuilder location(String location) {
            this.location = location;
            return this;
        }

        public StaffDTOBuilder salary(Double salary) {
            this.salary = salary;
            return this;
        }

        public StaffDTOBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public StaffDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public StaffDTOBuilder account(AccountDTO account) {
            this.account = account;
            return this;
        }

        public StaffDTO build() {
            return new StaffDTO(this.id, this.firstName, this.lastName, this.gender, this.birth, this.location, this.salary, this.phone, this.email, this.account);
        }

        public String toString() {
            Integer var10000 = this.id;
            return "StaffDTO.StaffDTOBuilder(id=" + var10000 + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", gender=" + this.gender + ", birth=" + String.valueOf(this.birth) + ", location=" + this.location + ", salary=" + this.salary + ", phone=" + this.phone + ", email=" + this.email + ", account=" + String.valueOf(this.account) + ")";
        }
    }
}

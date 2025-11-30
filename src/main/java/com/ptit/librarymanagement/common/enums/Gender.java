package com.ptit.librarymanagement.common.enums;

public enum Gender {
    MALE ("Nam", true),
    FEMALE ("Nữ", false);
    private final String gender;
    private final Boolean flag;

    Gender(String gender, Boolean flag) {
        this.gender = gender;
        this.flag = flag;
    }

    public String getGender() {
        return gender;
    }

    public Boolean getFlag() {
        return flag;
    }
}

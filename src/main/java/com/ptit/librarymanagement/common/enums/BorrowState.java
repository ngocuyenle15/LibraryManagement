package com.ptit.librarymanagement.common.enums;

public enum BorrowState {
    BORROWING ("Đang mượn"),
    RETURNED ("Đã trả"),
    OVERDUE ("Quá hạn"),
    OVERDUE_PUNISH ("Quá hạn - Phạt")
    ;

    private final String state;

    BorrowState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

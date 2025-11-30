package com.ptit.librarymanagement.common.authentication;

import com.ptit.librarymanagement.dto.AccountDTO;
import lombok.Getter;

public class Session {
    private static final Session SESSION;
    private AccountDTO account;
    static {
        SESSION = new Session();
    }
    private Session() {}
    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }
    public boolean isLogged() {
        return account != null;
    }

    public static Session getSession() {
        return Session.SESSION;
    }
}

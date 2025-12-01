package com.ptit.librarymanagement.service;


import com.ptit.librarymanagement.common.mail.MailService;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.AccountDAO;
import com.ptit.librarymanagement.model.dto.AccountDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.util.List;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService (Connection connection) {
        accountDAO = new AccountDAO(connection);
    }

    public AccountService (DAOFactory daoFactory) {
        accountDAO = daoFactory.getAccountDAO();
    }


    public AccountDTO loginAccount (AccountDTO account) throws RuntimeException {
        String pass = account.getPassword();
        AccountDTO accountDTO = accountDAO.findAccountByUserName(account).orElseThrow(() -> new RuntimeException("Không tìm thấy username"));
        if (!BCrypt.checkpw(pass, accountDTO.getPassword()))
            throw new RuntimeException("Sai mật khẩu");
        return accountDTO;
    }
    public void updateAccount (AccountDTO accountDTO) {
        // not update password
        accountDAO.editAccount(accountDTO);
    }
    public void deleteAccount (AccountDTO accountDTO) {
        accountDAO.deleteAccount(accountDTO);
    }

    public List<AccountDTO> getAllAccount () {
        return accountDAO.getAllAccount();
    }



    public void changePassword(String oldPass, AccountDTO account) {
        String passwordDB = accountDAO.getAccountById(account.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy"))
                .getPassword();

        if (!BCrypt.checkpw(oldPass, passwordDB)) {
            throw new RuntimeException("Sai password cũ");
        }

        account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(10)));
        accountDAO.changePassword(account);
    }

    public void resetPassword (AccountDTO accountDTO) {
        String purePassword = accountDTO.getPassword();
        String hashPassword = BCrypt.hashpw(purePassword, BCrypt.gensalt(10));
        accountDTO.setPassword(hashPassword);
        accountDAO.resetPassword(accountDTO);
    }




    public void insertAccount (AccountDTO accountDTO) {
        String hashpw = BCrypt.hashpw(accountDTO.getPassword(), BCrypt.gensalt(10));
        accountDTO.setPassword(hashpw);
        accountDAO.addAccount(accountDTO);

    }

    public String getOTPForResetPassword (String email) {
        boolean checkMailExist = accountDAO.checkMail(email);
        if (checkMailExist) {
            MailService mailService = MailService.getMailService();
            int otp = (int)(Math.random() * 900000) + 100000;
            mailService.sendMail(email, "ResetPass", Integer.toString(otp));
            return Integer.toString(otp);
        } else {
            throw new RuntimeException("Không tìm thấy email");
        }
    }

    public AccountDTO getAccountByEmail (String email) {
        return accountDAO.getAccountByEmail(email).get();
    }
}

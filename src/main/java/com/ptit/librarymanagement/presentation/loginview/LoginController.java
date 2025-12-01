package com.ptit.librarymanagement.presentation.loginview;

import com.ptit.librarymanagement.common.authentication.Session;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.model.dto.AccountDTO;
import com.ptit.librarymanagement.presentation.loginview.dialog.InputEmailForgotPassWordDialog;
import com.ptit.librarymanagement.presentation.loginview.dialog.InputOtpForgotPassWordDialog;
import com.ptit.librarymanagement.presentation.loginview.dialog.InputPassForgotPassWordDialog;
import com.ptit.librarymanagement.service.AccountService;
import com.ptit.librarymanagement.context.service.ServiceFactory;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LoginController {
    private LoginFrame loginFrame;
    private Session session;
    private AccountService accountService;

    private Runnable runnable;

    public void displayFormLogin () {
        this.loginFrame.setVisible(true);
    }
    public void closeFormLogin () {
        this.loginFrame.dispose();
    }

    public LoginController (LoginFrame loginFrame, Session session, ServiceFactory serviceFactory) {
        this.loginFrame = loginFrame;
        this.session = session;
        this.accountService = serviceFactory.getAccountService();
        loginFrame.addWindowListener(closeJFrame());
        initController();
    }

    public void initController () {
        loginFrame.getLbl6().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerLogin ();
            }
        });

        loginFrame.getLbl7().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerForgotPassword ();
            }
        });




    }
    private WindowAdapter closeJFrame () {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DbConnection.closeConnection();
            }
        };
    }






    private void handlerForgotPassword () {
        InputEmailForgotPassWordDialog inputEmailDialog = new InputEmailForgotPassWordDialog(null, true, "Quên mật khẩu", "create");
        inputEmailDialog.getConfirmInput().addActionListener(l -> {
            String email = inputEmailDialog.getEmailInput().getText();
            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Email không hợp lệ!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            try {
                String OTP = accountService.getOTPForResetPassword(email);
                inputEmailDialog.dispose();
                InputOtpForgotPassWordDialog otpDialog = new InputOtpForgotPassWordDialog(null, true, "Nhập OTP", "create");
                otpDialog.getConfirmInput().addActionListener(event -> {
                    String OTPInput = otpDialog.getOptInput().getText();
                    if (!OTPInput.matches("^[0-9]{6}$")) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Vui lòng nhập đúng định dạng OPT (6 số từ 0 đến 9)!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if (!OTPInput.equals(OTP)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "OTP không đúng, vui lòng nhập lại!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;

                    }
                    otpDialog.dispose();
                    InputPassForgotPassWordDialog inputPassDialog = new InputPassForgotPassWordDialog(null, true, "Nhập pass", "create");
                    inputPassDialog.getConfirmInput().addActionListener(lt -> {
                        String pass1 = inputPassDialog.getNewPassword1().getText();
                        String pass2 = inputPassDialog.getNewPassword2().getText();
                        if (!pass1.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Nhập mật khẩu theo đúng định dạng",
                                    "Lỗi",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                        else if (!pass1.equals(pass2)) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Mật khẩu không khớp vui lòng nhập lại!",
                                    "Lỗi",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        AccountDTO account = accountService.getAccountByEmail(email);
                        account.setPassword(pass1);
                        accountService.resetPassword(account);
                        inputPassDialog.dispose();
                        JOptionPane.showMessageDialog(
                                null,
                                "Đổi mật khẩu thành công!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                    });
                inputPassDialog.setVisible(true);


                });
                otpDialog.setVisible(true);




            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Không tìm thấy email trên hệ thống",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        });
        inputEmailDialog.setVisible(true);

    }


    public void handlerLogin () {
        String userName = loginFrame.getUserNameInput().getText();
        String password = loginFrame.getPasswordInput().getPass();
        try {
            AccountDTO accountDTO = accountService.loginAccount(AccountDTO.builder().userName(userName).password(password).build());
            session.setAccount(accountDTO);
            System.out.println("Đăng nhập thành công!");
            loginFrame.dispose();
            runnable.run();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(loginFrame,e.getMessage(), "Lỗi dữ liệu",JOptionPane.ERROR_MESSAGE);
        }

    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}

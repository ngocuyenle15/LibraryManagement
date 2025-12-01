package com.ptit.librarymanagement.presentation.dialog;



import com.ptit.librarymanagement.model.dto.AccountDTO;
import com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom;
import com.ptit.librarymanagement.presentation.dialog.component.HeaderTitle;
import com.ptit.librarymanagement.presentation.dialog.component.InputForm;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;


public class AccountPanel extends JPanel {

    private HeaderTitle titlePage;
    private JPanel mainPanel, bottomPanel;
    private ButtonCustom createButton, updateButton, cancelButton;
    private InputForm userNameInput;
    private InputForm oldPasswordInput;
    private InputForm newPasswordInput;
    private JTextField idInput;

    private Runnable onCancel;

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    public AccountDTO getObjectInField() {
        String id = idInput.getText();
        String oldPass = oldPasswordInput.getText();
        String newPass = newPasswordInput.getText();
        String userName = userNameInput.getText();

        return AccountDTO.builder()
                .id(id.isBlank() ? null : Integer.parseInt(id))
                .password(newPass)
                .userName(userName)
                .build();
    }

    public void setDefaultTextInField(AccountDTO accountDTO) {
        idInput.setText(String.valueOf(accountDTO.getId()));
        userNameInput.setText(accountDTO.getUserName());
    }

    public AccountPanel(String title, String type) {
        init(title, type);
    }

    public void init(String title, String type) {

        setLayout(new BorderLayout(0, 0));
        setBackground(Color.white);

        idInput = new JTextField("");

        titlePage = new HeaderTitle(title.toUpperCase());


        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);

        userNameInput = new InputForm("UserName");
        oldPasswordInput = new InputForm("Mật khẩu cũ");
        newPasswordInput = new InputForm("Mật khẩu mới");


        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        createButton = new ButtonCustom("Thêm người dùng", "success", 14);
        updateButton = new ButtonCustom("Lưu thông tin", "success", 14);
        cancelButton = new ButtonCustom("Thoát", "danger", 14);

        cancelButton.addActionListener((ActionEvent e) -> {
            if (onCancel != null) onCancel.run();
        });

        switch (type) {
            case "changeUserName" -> {
                mainPanel.add(userNameInput);
                bottomPanel.add(updateButton);
            }
            case "changePassword" -> {
                mainPanel.add(oldPasswordInput);
                mainPanel.add(newPasswordInput);
                bottomPanel.add(updateButton);
            }
        }

        bottomPanel.add(cancelButton);

        add(titlePage, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public HeaderTitle getTitlePage() {
        return titlePage;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public ButtonCustom getCreateButton() {
        return createButton;
    }

    public ButtonCustom getUpdateButton() {
        return updateButton;
    }

    public ButtonCustom getCancelButton() {
        return cancelButton;
    }

    public InputForm getUserNameInput() {
        return userNameInput;
    }

    public InputForm getOldPasswordInput() {
        return oldPasswordInput;
    }

    public InputForm getNewPasswordInput() {
        return newPasswordInput;
    }

    public JTextField getIdInput() {
        return idInput;
    }

    public Runnable getOnCancel() {
        return onCancel;
    }
}

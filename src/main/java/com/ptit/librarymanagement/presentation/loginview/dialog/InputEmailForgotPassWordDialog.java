package com.ptit.librarymanagement.presentation.loginview.dialog;



import com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom;
import com.ptit.librarymanagement.presentation.dialog.component.HeaderTitle;
import com.ptit.librarymanagement.presentation.dialog.component.InputForm;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

@Getter
public class InputEmailForgotPassWordDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel mainPanel, bottomPanel;
    private ButtonCustom confirmInput, updateButton, cancelButton;
    private InputForm emailInput;



    public InputEmailForgotPassWordDialog(Frame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);
    }

    public void init(String title, String type) {
//        idInput = new JTextField("");

        this.setSize(new Dimension(470, 250));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);

        emailInput = new InputForm("Email");


        JPanel genderPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        genderPanel.setBackground(Color.white);
        genderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


            mainPanel.add(emailInput);



        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        confirmInput = new ButtonCustom("Xác nhận", "success", 14);
        updateButton = new ButtonCustom("Lưu thông tin", "success", 14);
        cancelButton = new ButtonCustom("Thoát", "danger", 14);

        cancelButton.addActionListener((ActionEvent e) -> dispose());

        switch (type) {
            case "create" -> bottomPanel.add(confirmInput);
            case "update" -> bottomPanel.add(updateButton);
        }

        bottomPanel.add(cancelButton);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        InputEmailForgotPassWordDialog forgotPasswordG = new InputEmailForgotPassWordDialog(null, true, "Nhập ", "create");
        forgotPasswordG.setVisible(true);
    }


}
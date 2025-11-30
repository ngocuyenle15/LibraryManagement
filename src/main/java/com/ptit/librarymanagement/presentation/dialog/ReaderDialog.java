package com.ptit.librarymanagement.presentation.dialog;



import com.ptit.librarymanagement.common.enums.Gender;
import com.ptit.librarymanagement.dto.AuthorDTO;
import com.ptit.librarymanagement.dto.ReaderDTO;
import com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom;
import com.ptit.librarymanagement.presentation.dialog.component.HeaderTitle;
import com.ptit.librarymanagement.presentation.dialog.component.InputDate;
import com.ptit.librarymanagement.presentation.dialog.component.InputForm;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Enumeration;

@Getter
public class ReaderDialog extends JDialog {

    private HeaderTitle titlePage;
    private JPanel main, bottom;

    private ButtonCustom createButton, updateButton, cancelButton;
    private InputForm firstNameInput;
    private InputForm lastNameInput;
    private InputForm locationsInput;
    private InputForm phoneInput;
    private InputForm emailInput;
    private JTextField idInput;
    private ButtonGroup genderInput;
    private JRadioButton male;
    private JRadioButton female;

    private InputDate dateInput;

    public ReaderDTO getObjectInField () {
        String id = idInput.getText();
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        Boolean gender = getGenderForGroupButton();
        String email = emailInput.getText();
        String phone = phoneInput.getText();
        String locate = locationsInput.getText();
        Date date = dateInput.getDate();

        return ReaderDTO.builder()
                .id(id.isBlank() ? null : Integer.parseInt(id))
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .email(email)
                .phone(phone)
                .location(locate)
                .gender(gender)
                .birth(date != null ? new java.sql.Date(date.getTime()) : null)
                .build();
    }

    public void setDefaultTextInField (ReaderDTO readerDTO) {
        idInput.setText(Integer.toString(readerDTO.getId()));
        firstNameInput.setText(readerDTO.getFirstName());
        lastNameInput.setText(readerDTO.getLastName());
        emailInput.setText(readerDTO.getEmail());
        phoneInput.setText(readerDTO.getPhone());
        locationsInput.setText(readerDTO.getLocation());
        dateInput.setDate(readerDTO.getBirth());
        genderInput.setSelected(readerDTO.getGender() ? male.getModel() : female.getModel(), true);
    }

    public ReaderDialog(Frame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);

    }



    private Boolean getGenderForGroupButton () {
        ButtonModel selection = genderInput.getSelection();
        if (selection == null) return null;
        Enumeration<AbstractButton> buttons = genderInput.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.getModel() == selection) {
                JRadioButton button1 = (JRadioButton) button;
                Gender male = (Gender) button1.getClientProperty(Gender.MALE.getGender());
                Gender female = (Gender) button1.getClientProperty(Gender.FEMALE.getGender());
                return male != null ? male.getFlag() : female.getFlag();
            }
        }
        return null;
    }

    public void init(String title, String type) {
        idInput = new JTextField("id");
        idInput.setText("");

        this.setSize(new Dimension(470, 700));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());


        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.white);

        firstNameInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Tên");
        lastNameInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Họ");

        locationsInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Địa chỉ");
        phoneInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Số điện thoại");
        emailInput = new InputForm("Email");


        male   = new JRadioButton("Nam");
        female = new JRadioButton("Nữ");
        male.putClientProperty("Nam", Gender.MALE);
        female.putClientProperty("Nữ",Gender.FEMALE);
        male.setActionCommand("Nam");
        female.setActionCommand("Nữ");

        genderInput = new ButtonGroup();
        genderInput.add(male);
        genderInput.add(female);

        JPanel genderPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        genderPanel.setBackground(Color.white);
        genderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblGender = new JLabel("Giới tính");
        JPanel genderBox = new JPanel(new GridLayout(1, 2));
        genderBox.setBackground(Color.white);
        genderBox.add(male);
        genderBox.add(female);

        genderPanel.add(lblGender);
        genderPanel.add(genderBox);


        dateInput = new InputDate("Ngày sinh");

        main.add(firstNameInput);
        main.add(lastNameInput);
        main.add(genderPanel);

        main.add(dateInput);
        main.add(locationsInput);
        main.add(phoneInput);
        main.add(emailInput);



        bottom = new JPanel(new FlowLayout());
        bottom.setBackground(Color.white);

        createButton = new com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom("Thêm độc giả", "success", 14);
        updateButton = new com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom("Lưu thông tin", "success", 14);
        cancelButton = new ButtonCustom("Thoát", "danger", 14);

        cancelButton.addActionListener((ActionEvent e) -> dispose());

        switch (type) {
            case "create" -> bottom.add(createButton);
            case "update" -> bottom.add(updateButton);
            case "detail" -> {
                makeReadOnly();
            }
        }

        bottom.add(cancelButton);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(main, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
    }

    private void makeReadOnly() {
        firstNameInput.setDisable();
        lastNameInput.setDisable();

        dateInput.setDisable();

        male.setEnabled(false);
        female.setEnabled(false);
    }



}
package com.ptit.librarymanagement.presentation.dialog;



import com.ptit.librarymanagement.common.enums.Gender;
import com.ptit.librarymanagement.dto.AuthorDTO;
import com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom;
import com.ptit.librarymanagement.presentation.dialog.component.HeaderTitle;
import com.ptit.librarymanagement.presentation.dialog.component.InputForm;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;

@Getter
public class AuthorDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel mainPanel, bottomPanel;
    private ButtonCustom createButton, updateButton, cancelButton;
    private InputForm yearOfBirthInput;
    private InputForm firstNameInput;
    private InputForm lastNameInput;
    private JTextField idInput;
    private ButtonGroup genderInput;
    private JRadioButton male;
    private JRadioButton female;

    public AuthorDTO getObjectInField () {
        String id = idInput.getText();
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String year = yearOfBirthInput.getText();
        Boolean gender = getGenderForGroupButton();
        return AuthorDTO.builder()
                .id(id.isBlank() ? null : Integer.parseInt(id))
                .firstName(firstName)
                .lastName(lastName)
                .yearOfBirth(year)
                .gender(gender)
                .build();
    }
    public void setDefaultTextInField (AuthorDTO authorDTO) {
        idInput.setText(Integer.toString(authorDTO.getId()));
        firstNameInput.setText(authorDTO.getFirstName());
        lastNameInput.setText(authorDTO.getLastName());
        yearOfBirthInput.setText(authorDTO.getYearOfBirth());

        genderInput.setSelected(authorDTO.getGender() ? male.getModel() : female.getModel(), true);


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




    public AuthorDialog(Frame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);
    }

    public void init(String title, String type) {
        idInput = new JTextField("");

        this.setSize(new Dimension(470, 470));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);

        firstNameInput = new InputForm("Tên");
        lastNameInput = new InputForm("Họ");
        yearOfBirthInput = new InputForm("Năm sinh");


        male = new JRadioButton(Gender.MALE.getGender());
        female = new JRadioButton(Gender.FEMALE.getGender());
        male.putClientProperty("Nam",Gender.MALE);
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


        mainPanel.add(firstNameInput);
        mainPanel.add(lastNameInput);
        mainPanel.add(yearOfBirthInput);
        mainPanel.add(genderPanel);

        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        createButton = new ButtonCustom("Thêm tác giả", "success", 14);
        updateButton = new ButtonCustom("Lưu thông tin", "success", 14);
        cancelButton = new ButtonCustom("Thoát", "danger", 14);

        cancelButton.addActionListener((ActionEvent e) -> dispose());

        switch (type) {
            case "create" -> bottomPanel.add(createButton);
            case "update" -> bottomPanel.add(updateButton);
        }

        bottomPanel.add(cancelButton);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }



}
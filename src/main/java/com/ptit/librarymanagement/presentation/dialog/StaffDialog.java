package com.ptit.librarymanagement.presentation.dialog;



import com.ptit.librarymanagement.common.enums.Gender;
import com.ptit.librarymanagement.common.enums.Role;
import com.ptit.librarymanagement.model.dto.AccountDTO;
import com.ptit.librarymanagement.model.dto.StaffDTO;
import com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom;
import com.ptit.librarymanagement.presentation.dialog.component.HeaderTitle;
import com.ptit.librarymanagement.presentation.dialog.component.InputDate;
import com.ptit.librarymanagement.presentation.dialog.component.InputForm;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Enumeration;


public class StaffDialog extends JDialog {

    private HeaderTitle titlePage;
    private JPanel mainPanel, bottomPanel;

    private ButtonCustom createButton, updateButton, cancelButton, confirmButton;
    private JTextField staffIdInput;
    private InputForm firstNameInput;
    private InputForm lastNameInput;
    private InputForm locationsInput;
    private InputForm phoneInput;
    private InputForm emailInput;
    private InputForm salaryInput;
    private InputForm userNameInput;
    private InputForm passwordInput; // todo: default pass do addmin tạo nên không cần ẩn


    private ButtonGroup genderInput;
    private JRadioButton male;
    private JRadioButton female;

    private ButtonGroup roleInput;
    private JRadioButton admin;
    private JRadioButton staff;

    private InputDate dateInput;
    private JTextField accountIdInput;

    public void setDefaultTextInField (StaffDTO staffDTO) {
        staffIdInput.setText(String.valueOf(staffDTO.getId()));
        firstNameInput.setText(staffDTO.getFirstName());
        lastNameInput.setText(staffDTO.getLastName());
        genderInput.setSelected(staffDTO.getGender() ? male.getModel() : female.getModel(), true);
        dateInput.setDate(staffDTO.getBirth());
        locationsInput.setText(staffDTO.getLocation());
        phoneInput.setText(staffDTO.getPhone());
        emailInput.setText(staffDTO.getEmail());
        salaryInput.setText(String.format("%.0f", staffDTO.getSalary()));
        accountIdInput.setText(String.valueOf(staffDTO.getAccount().getId()));
        userNameInput.setText(staffDTO.getAccount().getUserName());
        passwordInput.setText(staffDTO.getAccount().getPassword());
        roleInput.setSelected(staffDTO.getAccount().getRole().getId() == 1 ? admin.getModel() : staff.getModel(),true);
    }

    public StaffDTO getObjectInField () {
        String staffId = staffIdInput.getText();
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        Boolean gender = getGenderForGroupButton();
        Date date = dateInput.getDate();
        String location = locationsInput.getText();
        String phone = phoneInput.getText();
        String email = emailInput.getText();
        String salary = salaryInput.getText();
        String accountId = accountIdInput.getText();
        String userName = userNameInput.getText();
        String password = passwordInput.getText();
        return StaffDTO.builder()
                        .id(staffId.isBlank() ? null : Integer.parseInt(staffId))
                        .firstName(firstName)
                        .lastName(lastName)
                        .gender(gender)
                        .birth(date != null ? new java.sql.Date(date.getTime()) : null)
                        .location(location)
                        .phone(phone)
                        .email(email)
                        .salary(salary.matches("^\\d+(\\.\\d+)?$") ? Double.valueOf(salary) : null)
                        .account(AccountDTO.builder()
                                .id(accountId.isBlank() ? null: Integer.valueOf(accountId))
                                .userName(userName)
                                .password(password)
                                .role(getSelectedRole(roleInput))
                                .build())
                        .build();
    }

    public Role getSelectedRole(ButtonGroup group) {
        if (group.getSelection() == null) return null;

        String roleName = group.getSelection().getActionCommand();
        return Role.mapRole(roleName);
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

    public StaffDialog(Frame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);
    }

    public void init(String title, String type) {
        accountIdInput = new JTextField("account_id");
        accountIdInput.setText("");

        staffIdInput = new JTextField("Id");
        staffIdInput.setText("");

        this.setSize(new Dimension(500, 750));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());


        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);

        firstNameInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Tên");
        lastNameInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Họ");
        locationsInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Địa chỉ");
        phoneInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Số điện thoại");
        emailInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Email");
        userNameInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Tên đăng nhập");
        passwordInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Mật khẩu");
        salaryInput = new InputForm("Lương");

        // GENDER
        male   = new JRadioButton("Nam");
        female = new JRadioButton("Nữ");
        male.putClientProperty("Nam", Gender.MALE);
        female.putClientProperty("Nữ",Gender.FEMALE);
        male.setActionCommand("Nam");
        female.setActionCommand("Nữ");

        genderInput = new ButtonGroup();
        genderInput.add(male);
        genderInput.add(female);

        admin   = new JRadioButton("Admin");
        staff = new JRadioButton("Staff");
        admin.setActionCommand("Admin");
        staff.setActionCommand("Staff");
        roleInput = new ButtonGroup();
        roleInput.add(admin);
        roleInput.add(staff);


        JPanel genderPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        genderPanel.setBackground(Color.white);
        genderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel rolePanel = new JPanel(new GridLayout(2, 1, 0, 0));
        rolePanel.setBackground(Color.white);
        rolePanel.setBorder(new EmptyBorder(10, 10, 10, 10));



        JLabel lblGender = new JLabel("Giới tính");
        JPanel genderBox = new JPanel(new GridLayout(1, 2));
        genderBox.setBackground(Color.white);
        genderBox.add(male);
        genderBox.add(female);


        JLabel lblRole = new JLabel("Role");
        JPanel roleBox = new JPanel(new GridLayout(1, 2));
        roleBox.setBackground(Color.white);
        roleBox.add(admin);
        roleBox.add(staff);



        rolePanel.add(lblRole);
        rolePanel.add(roleBox);


        genderPanel.add(lblGender);
        genderPanel.add(genderBox);


        dateInput = new InputDate("Ngày sinh");


        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        createButton = new com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom("Thêm nhân viên", "success", 14);
        updateButton = new com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom("Lưu thông tin", "success", 14);
        cancelButton = new ButtonCustom("Thoát", "danger", 14);
        confirmButton = new com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom("Cấp lại mật khẩu", "success", 14);

        cancelButton.addActionListener((ActionEvent e) -> dispose());

        switch (type) {
            case "create" -> {
                mainPanel.add(firstNameInput);
                mainPanel.add(lastNameInput);
                mainPanel.add(genderPanel);


                mainPanel.add(dateInput);
                mainPanel.add(locationsInput);
                mainPanel.add(phoneInput);
                mainPanel.add(emailInput);
                mainPanel.add(salaryInput);
                mainPanel.add(userNameInput);
                mainPanel.add(rolePanel);
                mainPanel.add(passwordInput);
                bottomPanel.add(createButton);
            }
            case "update" -> {
                mainPanel.add(firstNameInput);
                mainPanel.add(lastNameInput);
                mainPanel.add(genderPanel);


                mainPanel.add(dateInput);
                mainPanel.add(locationsInput);
                mainPanel.add(phoneInput);
                mainPanel.add(emailInput);
                mainPanel.add(salaryInput);
                mainPanel.add(userNameInput);
                mainPanel.add(rolePanel);
                bottomPanel.add(updateButton);

            }
            case "changePassword" -> {
                this.setSize(new Dimension(470, 200));

                mainPanel.add(passwordInput);
                bottomPanel.add(confirmButton);
            }
        }

        bottomPanel.add(cancelButton);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void makeReadOnly() {
        firstNameInput.setDisable();
        lastNameInput.setDisable();
        dateInput.setDisable();

        male.setEnabled(false);
        female.setEnabled(false);
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

    public ButtonCustom getConfirmButton() {
        return confirmButton;
    }

    public JTextField getStaffIdInput() {
        return staffIdInput;
    }

    public InputForm getFirstNameInput() {
        return firstNameInput;
    }

    public InputForm getLastNameInput() {
        return lastNameInput;
    }

    public InputForm getLocationsInput() {
        return locationsInput;
    }

    public InputForm getPhoneInput() {
        return phoneInput;
    }

    public InputForm getEmailInput() {
        return emailInput;
    }

    public InputForm getSalaryInput() {
        return salaryInput;
    }

    public InputForm getUserNameInput() {
        return userNameInput;
    }

    public InputForm getPasswordInput() {
        return passwordInput;
    }

    public ButtonGroup getGenderInput() {
        return genderInput;
    }

    public JRadioButton getMale() {
        return male;
    }

    public JRadioButton getFemale() {
        return female;
    }

    public ButtonGroup getRoleInput() {
        return roleInput;
    }

    public JRadioButton getAdmin() {
        return admin;
    }

    public JRadioButton getStaff() {
        return staff;
    }

    public InputDate getDateInput() {
        return dateInput;
    }

    public JTextField getAccountIdInput() {
        return accountIdInput;
    }
}
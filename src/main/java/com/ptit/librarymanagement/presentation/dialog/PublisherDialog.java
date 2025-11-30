package com.ptit.librarymanagement.presentation.dialog;





import com.ptit.librarymanagement.dto.PublisherDTO;
import com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom;
import com.ptit.librarymanagement.presentation.dialog.component.HeaderTitle;

import com.ptit.librarymanagement.presentation.dialog.component.InputForm;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Data
public class PublisherDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel mainPanel, bottomPanel;

    private ButtonCustom createButton, updateButton, cancelButton;
    private InputForm nameInput;
    private InputForm phoneInput;
    private InputForm emailInput;
    private InputForm locationInput;
    private JTextField idInput;

    public PublisherDTO getObjectInField () {
        String id = idInput.getText();
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        String email = emailInput.getText();
        String location = locationInput.getText();
        return PublisherDTO.builder()
                .id(id.isBlank() ? null : Integer.parseInt(id))
                .name(name)
                .phone(phone)
                .email(email)
                .location(location)
                .build();
    }
    public void setDefaultTextInField (PublisherDTO publisherDTO) {
       idInput.setText(Integer.toString(publisherDTO.getId()));
       nameInput.setText(publisherDTO.getName());
       phoneInput.setText(publisherDTO.getPhone());
       emailInput.setText(publisherDTO.getEmail());
       locationInput.setText(publisherDTO.getLocation());
    }




    public PublisherDialog(Frame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);
    }

    public void init(String title, String type) {
        idInput = new JTextField("Id");
        idInput.setText("");

        this.setSize(new Dimension(480, 480));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);

        nameInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Tên nhà xuất bản");
        locationInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Địa chỉ");
        emailInput = new com.ptit.librarymanagement.presentation.dialog.component.InputForm("Email");
        phoneInput = new InputForm("Số điện thoại");


        mainPanel.add(nameInput);
        mainPanel.add(locationInput);
        mainPanel.add(emailInput);
        mainPanel.add(phoneInput);




        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        createButton = new com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom("Thêm nhà xuất bản", "success", 14);
        updateButton = new com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom("Lưu thông tin", "success", 14);
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
package com.ptit.librarymanagement.presentation.dialog;

import com.ptit.librarymanagement.dto.ShelfDTO;
import com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom;
import com.ptit.librarymanagement.presentation.dialog.component.HeaderTitle;
import com.ptit.librarymanagement.presentation.dialog.component.InputForm;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Data
public class ShelfDialog extends JDialog {

    private HeaderTitle titlePage;
    private JPanel mainPanel, bottomPanel;
    private ButtonCustom createButton, updateButton, cancelButton;
    private InputForm shelfNameInput;
    private InputForm locationInput;
    private JTextField idInput;

    public ShelfDTO getObjectInField () {
        String id = idInput.getText();
        String shelfName = shelfNameInput.getText();
        String location = locationInput.getText();
        return ShelfDTO.builder()
                .id(id.isBlank() ? null : Integer.parseInt(id))
                .shelfName(shelfName)
                .location(location)
                .build();
    }

    public void setDefaultTextInField (ShelfDTO shelfDTO) {
        idInput.setText(Integer.toString(shelfDTO.getId()));
        shelfNameInput.setText(shelfDTO.getShelfName());
        locationInput.setText(shelfDTO.getLocation());
    }




    public ShelfDialog(Frame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);
    }




    public void init(String title, String type) {
        idInput = new JTextField("Id");
        idInput.setText("");

        this.setSize(new Dimension(400, 300));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);

        shelfNameInput = new InputForm("Tên kệ");
        locationInput = new InputForm("Vị trí");




        mainPanel.add(locationInput);
        mainPanel.add(shelfNameInput);

        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        createButton = new ButtonCustom("Thêm vị tri để sách", "success", 14);
        updateButton = new ButtonCustom("Lưu thông tin", "success", 14);
        cancelButton = new ButtonCustom("Thoát", "danger", 14);

        cancelButton.addActionListener((ActionEvent e) -> dispose());

        switch (type) {
            case "create" -> bottomPanel.add(createButton);
            case "update" -> bottomPanel.add(updateButton);
            case "detail" -> {

            }
        }

        bottomPanel.add(cancelButton);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }


}
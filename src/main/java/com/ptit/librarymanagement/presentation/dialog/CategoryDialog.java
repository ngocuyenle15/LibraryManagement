package com.ptit.librarymanagement.presentation.dialog;



import com.ptit.librarymanagement.model.dto.CategoryDTO;
import com.ptit.librarymanagement.presentation.dialog.component.ButtonCustom;
import com.ptit.librarymanagement.presentation.dialog.component.HeaderTitle;
import com.ptit.librarymanagement.presentation.dialog.component.InputForm;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;


public class CategoryDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel mainPanel, bottomPanel;
    private ButtonCustom createButton, updateButton, cancelButton;
    private InputForm categoryNameInput;
    private JTextField idInput;


    public CategoryDTO getObjectInField () {
        String id = idInput.getText();
        String categoryName = categoryNameInput.getText();
        return CategoryDTO.builder()
                .id(id.isBlank() ? null : Integer.parseInt(id))
                .name(categoryName)
                .build();
    }
    public void setDefaultTextInField (CategoryDTO categoryDTO) {
        idInput.setText(Integer.toString(categoryDTO.getId()));
        categoryNameInput.setText(categoryDTO.getName());
        idInput.setText(String.valueOf(categoryDTO.getId()));
        categoryNameInput.setText(categoryDTO.getName());
    }



    public CategoryDialog(Frame owner, boolean modal, String title, String type) {
        super(owner, title, modal);

        init(title, type);
        this.setLocationRelativeTo(null);
    }

    public void init(String title, String type) {

        idInput = new JTextField();

        this.setSize(new Dimension(470, 230));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);

        categoryNameInput = new InputForm("Tên danh mục");


        JPanel genderPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        genderPanel.setBackground(Color.white);
        genderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));



        mainPanel.add(categoryNameInput);
        idInput.setText("");

        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        createButton = new ButtonCustom("Thêm danh mục", "success", 14);
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

    public InputForm getCategoryNameInput() {
        return categoryNameInput;
    }

    public JTextField getIdInput() {
        return idInput;
    }
}
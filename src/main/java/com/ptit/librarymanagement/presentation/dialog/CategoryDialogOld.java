package com.ptit.librarymanagement.presentation.dialog;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class CategoryDialogOld extends JDialog {

    private JPanel mainPanel, bottomPanel;
    private JButton createButton, updateButton, cancelButton;
    private JTextField categoryField;
    private boolean isUpdate = false;

    public CategoryDialogOld(JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        if (type.equals("update")) isUpdate = true;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(450, 200));
        this.setLayout(new BorderLayout(0, 0));

        JLabel lblTitle = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));

        mainPanel = new JPanel(new GridLayout(1, 2, 15, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.white);

        mainPanel.add(new JLabel("Tên danh mục:"));
        categoryField = new JTextField();
        mainPanel.add(categoryField);

        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottomPanel.setBackground(Color.white);

        createButton = new JButton("Thêm danh mục");
        updateButton = new JButton("Lưu thông tin");
        cancelButton = new JButton("Hủy bỏ");

        if (!isUpdate) {
            bottomPanel.add(createButton);
        } else {
            bottomPanel.add(updateButton);
        }
        bottomPanel.add(cancelButton);

        this.add(lblTitle, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JTextField getCategoryField() {
        return categoryField;
    }

    public boolean isUpdate() {
        return isUpdate;
    }
}

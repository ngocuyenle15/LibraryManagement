package com.ptit.librarymanagement.presentation.contentview.component;




import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class PunishDialog extends JDialog {

    private JTextArea textArea;
    private JButton btnSubmit;
    private JLabel titleLabel;
    private JPanel centerPanel;
    private JScrollPane scrollPane;
    private JPanel btnPanel;

    public void setText (String text) {
        textArea.setText(text);
    }

    public PunishDialog(Frame parent) {
        super(parent, "Thông tin phạt", true);
        initComponents();
        layoutComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Center panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title label
        titleLabel = new JLabel("Thông tin phạt:");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 13f));

        // Text area
        textArea = new JTextArea(5, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(null);

        // Scroll pane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);

        // Submit button
        btnSubmit = new JButton("Update");
        btnPanel = new JPanel();
        btnPanel.add(btnSubmit);
    }

    private void layoutComponents() {
        // Thêm title và text area vào center panel
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Thêm vào dialog
        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JButton getBtnSubmit() {
        return btnSubmit;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JPanel getBtnPanel() {
        return btnPanel;
    }
}

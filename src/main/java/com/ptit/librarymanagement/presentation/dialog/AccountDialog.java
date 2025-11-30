package com.ptit.librarymanagement.presentation.dialog;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class AccountDialog extends JDialog {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel changeUserNamePanel;
    private JPanel changePasswordPanel;

    public AccountDialog(JFrame owner, JPanel changeUserNamePanel, JPanel changePasswordPanel) {
        super(owner, "Quản lý tài khoản", true);
        this.changeUserNamePanel = changeUserNamePanel;
        this.changePasswordPanel = changePasswordPanel;

        initComponent();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 300));


        JMenuBar menuBar = new JMenuBar();

        JButton btnView1 = new JButton("Đổi tên đăng nhập");
        JButton btnView2 = new JButton("Đổi mật khẩu");

        menuBar.add(btnView1);
        menuBar.add(btnView2);

        add(menuBar, BorderLayout.NORTH);


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        changeUserNamePanel.setBackground(Color.CYAN);


        changePasswordPanel.setBackground(Color.ORANGE);


        cardPanel.add(changeUserNamePanel, "VIEW_1");
        cardPanel.add(changePasswordPanel, "VIEW_2");

        add(cardPanel, BorderLayout.CENTER);
        btnView1.addActionListener(e -> cardLayout.show(cardPanel, "VIEW_1"));
        btnView2.addActionListener(e -> cardLayout.show(cardPanel, "VIEW_2"));
    }

}

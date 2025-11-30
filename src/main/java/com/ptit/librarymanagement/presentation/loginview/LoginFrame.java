package com.ptit.librarymanagement.presentation.loginview;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.ptit.librarymanagement.dto.AccountDTO;
import lombok.Data;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Data
public class LoginFrame extends JFrame {
    private JPanel mainPanel, loginPanel;
    private JLabel lblImage, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7;
    private InputFormPanel userNameInput, passwordInput;

    Color FontColor = new Color(96, 125, 139);

    public AccountDTO getAccountLogin () {
        String userName = userNameInput.getText();
        String password = passwordInput.getText();
        return AccountDTO.builder()
                        .id(null)
                        .userName(userName)
                        .password(password)
                        .build();
    }

    public LoginFrame() {
        initComponent();

    }
    private void initComponent() {
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle("Đăng nhập");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame jf = this;
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        mainPanel.setPreferredSize(new Dimension(500, 740));
        mainPanel.setLayout(new FlowLayout(1, 0, 10));
        lbl3 = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
        lbl3.setFont(new Font(FlatRobotoFont.FAMILY_SEMIBOLD, Font.BOLD, 20));
        mainPanel.add(lbl3);
        JPanel paneldn = new JPanel();
        paneldn.setBackground(Color.BLACK);
        paneldn.setPreferredSize(new Dimension(400, 200));
        paneldn.setLayout(new GridLayout(2, 1));
        userNameInput = new InputFormPanel("Tên đăng nhập");
        paneldn.add(userNameInput);
        passwordInput = new InputFormPanel("Mật khẩu", "password");
        paneldn.add(passwordInput);
        mainPanel.add(paneldn);
        lbl6 = new JLabel("ĐĂNG NHẬP");
        lbl6.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16));
        lbl6.setForeground(Color.white);
        loginPanel = new JPanel();
        loginPanel.putClientProperty( FlatClientProperties.STYLE, "arc: 99" );
        loginPanel.setBackground(Color.BLACK);
        loginPanel.setPreferredSize(new Dimension(380, 45));
        loginPanel.setLayout(new FlowLayout(1, 0, 15));
        loginPanel.add(lbl6);
        lbl7 = new JLabel("Quên mật khẩu", JLabel.RIGHT);
        lbl7.setPreferredSize(new Dimension(380, 50));
        lbl7.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 18));
        mainPanel.add(lbl7);
        mainPanel.add(loginPanel);
        this.add(mainPanel, BorderLayout.EAST);
    }

    private void pnlLogInMouseEntered(java.awt.event.MouseEvent evt) {
        loginPanel.setBackground(FontColor);
        loginPanel.setForeground(Color.black);
    }

    private void pnlLogInMouseExited(java.awt.event.MouseEvent evt) {
        loginPanel.setBackground(Color.BLACK);
        loginPanel.setForeground(Color.white);
    }


    public void acc () {
        lbl6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }


}

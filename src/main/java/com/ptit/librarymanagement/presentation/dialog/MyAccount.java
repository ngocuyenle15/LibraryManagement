package com.ptit.librarymanagement.presentation.dialog;

import com.formdev.flatlaf.extras.FlatSVGIcon;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class MyAccount extends JPanel {
    private JPanel iconPanel;
    private JLabel iconLabel;
    private JPanel infoPanel;
    private JLabel usernameLabel;
    private JLabel roleLabel;

    public void setUserNameAndRoleText (String userName, String role) {
        usernameLabel.setText(userName);
        roleLabel.setText(role);
    }

    public MyAccount () {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setLayout(new BorderLayout(0, 0));

        iconPanel = new JPanel(new FlowLayout());
        iconPanel.setPreferredSize(new Dimension(60, 0));
        iconPanel.setOpaque(false);
        this.add(iconPanel, BorderLayout.WEST);

        iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(50, 70));
        iconLabel.setIcon(new FlatSVGIcon("icon/iconaccount.svg"));
        iconPanel.add(iconLabel);
        infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new FlowLayout(0, 10, 5));
        infoPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        this.add(infoPanel, BorderLayout.CENTER);
        usernameLabel = new JLabel();

        usernameLabel = new JLabel();
        usernameLabel.putClientProperty("FlatLaf.style", "font: 150% $semibold.font");
        infoPanel.add(usernameLabel);
        roleLabel = new JLabel();

        roleLabel = new JLabel();
        roleLabel.putClientProperty("FlatLaf.style", "font: 120% $light.font");
        roleLabel.setForeground(Color.GRAY);
        infoPanel.add(roleLabel);

    }

    public JPanel getIconPanel() {
        return iconPanel;
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public JLabel getRoleLabel() {
        return roleLabel;
    }
}

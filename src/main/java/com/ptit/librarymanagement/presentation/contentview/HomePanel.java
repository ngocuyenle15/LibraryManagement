package com.ptit.librarymanagement.presentation.contentview;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel() {

        setLayout(new GridBagLayout());
        setOpaque(false);
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBackground(new Color(240, 247, 250));
        content.add(createTitleLabel("CHƯƠNG TRÌNH QUẢN LÝ THƯ VIỆN"));
        content.add(Box.createVerticalStrut(25));

        content.add(createSubLabel("Thực hiện bởi nhóm: N23DCCN099"));
        content.add(Box.createVerticalStrut(15));

        content.add(createSubLabel("Lê Ngọc Uyển - N23DCCN135"));
        content.add(createSubLabel("Phan Thiện Vỹ - N23DCCN137"));
        content.add(createSubLabel("Trần Quang Khoan - N23DCCN099"));

        add(content);
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.black);
        label.setFont(new Font("Tahoma", Font.BOLD, 32));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel createSubLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.black);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }


}

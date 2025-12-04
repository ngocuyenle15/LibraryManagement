package com.ptit.librarymanagement.presentation.mainview.menu;


import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.ptit.librarymanagement.presentation.contentview.component.InputImage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemMenu extends JPanel {


    Color FontColor = new Color(96, 125, 139);
    Color ColorBlack = new Color(26, 26, 26);
    Color DefaultColor = new Color(255, 255, 255);
    JLabel iconLabel    , contentPanel, quantityPanel;
    JPanel rightPanel;
    JLabel img;
    public boolean isSelected;

    public ItemMenu(String linkIcon, String content) {
        this.setLayout(new FlowLayout(1, 10, 7));
        this.setPreferredSize(new Dimension(225, 45));
        this.setBackground(DefaultColor);
        this.putClientProperty( FlatClientProperties.STYLE, "arc: 15" );
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isSelected) {
                    setBackground(new Color(235, 237, 240));
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelected) {
                    setBackground(new Color(255, 255, 255));
                }
            }
        });
        iconLabel = new JLabel();
        iconLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        iconLabel.setPreferredSize(new Dimension(45, 30));
        iconLabel.setIcon(new FlatSVGIcon("./menu_icon/" + linkIcon));
        this.add(iconLabel);

        contentPanel = new JLabel(content);
        contentPanel.setPreferredSize(new Dimension(155, 30));
        contentPanel.putClientProperty("FlatLaf.style", "font: 145% $medium.font");
        contentPanel.setForeground(ColorBlack);
        this.add(contentPanel);
    }

    public ItemMenu(String linkImg, String title, int quantity) {

        this.setLayout(new BorderLayout(0, 0));
        this.setPreferredSize(new Dimension(380, 60));
        this.setBackground(Color.white);

        img = new JLabel("");
        img.setIcon(InputImage.resizeImage(new ImageIcon("" + linkImg), 38));
        this.add(img, BorderLayout.WEST);

        rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(0, 0, 0));
        rightPanel.setBorder(new EmptyBorder(10, 10, 0, 0));
        rightPanel.setOpaque(false);
        this.add(rightPanel, BorderLayout.CENTER);

        contentPanel = new JLabel(title);
        contentPanel.putClientProperty("FlatLaf.style", "font: 120% $semibold.font");
        contentPanel.setForeground(Color.black);
        rightPanel.add(contentPanel);

        quantityPanel = new JLabel("Số lượng: " + quantity);
        quantityPanel.setPreferredSize(new Dimension(350, 20));
        quantityPanel.putClientProperty("FlatLaf.style", "font: 100% $medium.font");
        quantityPanel.setForeground(Color.gray);
        rightPanel.add(quantityPanel);

    }

}

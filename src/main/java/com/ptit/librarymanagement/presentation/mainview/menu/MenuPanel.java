package com.ptit.librarymanagement.presentation.mainview.menu;
import com.ptit.librarymanagement.presentation.dialog.MyAccount;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MenuPanel extends JPanel {
    String[][] menuItemInformation = {
            {"Trang chủ", "home_icon.svg", "homePanel"},
            {"Sách", "book_icon.svg", "bookPanel"},
            {"Độc giả", "reader_icon.svg", "readerPanel"},
            {"Danh mục", "category_icon.svg", "categoryPanel"},
            {"Tác giả", "author_icon.svg", "authorPanel"},
            {"Nhà xuất bản", "publisher_icon.svg", "publisherPanel"},
            {"Kệ sách", "book_shelf_icon.svg", "bookShelvePanel"},
            {"Thẻ mượn", "borrowcard_icon.svg", "borrowSlipPanel"},
            {"Nhân viên", "staff_icon.svg", "staffPanel"},
            {"Thùng rác", "recyclebin_icon.svg", "recycleBinPanel"},
            {"Đăng xuất", "logout_icon.svg", "dangxuat"},
    };

    public String[][] getMenuItemInformation () {
        return this.menuItemInformation;
    }


    private ItemMenu[] listItem;

    public ItemMenu[] getListItem () {
        return listItem;
    }

    JScrollPane scrollPane;

    JPanel centerPanel, topPanel, bottomPanel, bar1, bar2, bar3, bar4;

    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);
    Color HowerFontColor = new Color(1, 87, 155);
    Color HowerBackgroundColor = new Color(187, 222, 251);

    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    MyAccount accountInformationPanel;


    public MenuPanel() {
        this.initComponent();
    }

    private void initComponent() {
        listItem = new ItemMenu[menuItemInformation.length];
        this.setOpaque(true);
        this.setBackground(DefaultColor);
        this.setLayout(new BorderLayout(0, 0));

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(250, 80));
        topPanel.setBackground(DefaultColor);
        topPanel.setLayout(new BorderLayout(0, 0));
        this.add(topPanel, BorderLayout.NORTH);

        accountInformationPanel = new MyAccount();
        topPanel.add(accountInformationPanel, BorderLayout.CENTER);



        bar1 = new JPanel();
        bar1.setBackground(new Color(204, 214, 219));
        bar1.setPreferredSize(new Dimension(1, 0));
        topPanel.add(bar1, BorderLayout.EAST);

        bar2 = new JPanel();
        bar2.setBackground(new Color(204, 214, 219));
        bar2.setPreferredSize(new Dimension(0, 1));
        topPanel.add(bar2, BorderLayout.SOUTH);

        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(230, 600));
        centerPanel.setBackground(DefaultColor);
        centerPanel.setLayout(new FlowLayout(0, 0, 5));

        bar3 = new JPanel();
        bar3.setBackground(new Color(204, 214, 219));
        bar3.setPreferredSize(new Dimension(1, 1));
        this.add(bar3, BorderLayout.EAST);

        scrollPane = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(250, 50));
        bottomPanel.setBackground(DefaultColor);
        bottomPanel.setLayout(new BorderLayout(0, 0));


        bar4 = new JPanel();
        bar4.setBackground(new Color(204, 214, 219));
        bar4.setPreferredSize(new Dimension(1, 1));
        bottomPanel.add(bar4, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);

        for (int i = 0; i < menuItemInformation.length; i++) {
            if (i + 1 == menuItemInformation.length) {
                listItem[i] = new ItemMenu(menuItemInformation[i][1], menuItemInformation[i][0]);
                bottomPanel.add(listItem[i]);
            } else {
                listItem[i] = new ItemMenu(menuItemInformation[i][1], menuItemInformation[i][0]);
                centerPanel.add(listItem[i]);
                if (i != 0) {
                    if (false) {
                        listItem[i].setVisible(false);
                    }
                }
            }
        }

        listItem[0].setBackground(HowerBackgroundColor);
        listItem[0].setForeground(HowerFontColor);
        listItem[0].isSelected = true;

        for (int i = 0; i < menuItemInformation.length; i++) {
            listItem[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    pnlMenuTaskbarMousePress(evt);
                }
            });
        }



    }



    public void pnlMenuTaskbarMousePress(MouseEvent evt) {

        for (int i = 0; i < menuItemInformation.length; i++) {
            if (evt.getSource() == listItem[i]) {
                listItem[i].isSelected = true;
                listItem[i].setBackground(HowerBackgroundColor);
                listItem[i].setForeground(HowerFontColor);
            } else {
                listItem[i].isSelected = false;
                listItem[i].setBackground(DefaultColor);
                listItem[i].setForeground(FontColor);
            }
        }
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public JPanel getBar1() {
        return bar1;
    }

    public JPanel getBar2() {
        return bar2;
    }

    public JPanel getBar3() {
        return bar3;
    }

    public JPanel getBar4() {
        return bar4;
    }

    public Color getFontColor() {
        return FontColor;
    }

    public Color getDefaultColor() {
        return DefaultColor;
    }

    public Color getHowerFontColor() {
        return HowerFontColor;
    }

    public Color getHowerBackgroundColor() {
        return HowerBackgroundColor;
    }

    public JFrame getOwner() {
        return owner;
    }

    public MyAccount getAccountInformationPanel() {
        return accountInformationPanel;
    }
}
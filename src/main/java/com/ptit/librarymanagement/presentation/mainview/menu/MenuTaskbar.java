package com.ptit.librarymanagement.presentation.mainview.menu;
import com.ptit.librarymanagement.presentation.dialog.MyAccount;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MenuTaskbar extends JPanel {
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


    private ItemTaskbar[] listItem;

    public ItemTaskbar[] getListItem () {
        return listItem;
    }

    JScrollPane scrollPane;

    JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;

    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);
    Color HowerFontColor = new Color(1, 87, 155);
    Color HowerBackgroundColor = new Color(187, 222, 251);

    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    MyAccount accountInformationPanel;


    public MenuTaskbar() {
        this.initComponent();
    }

    private void initComponent() {
        listItem = new ItemTaskbar[menuItemInformation.length];
        this.setOpaque(true);
        this.setBackground(DefaultColor);
        this.setLayout(new BorderLayout(0, 0));

        pnlTop = new JPanel();
        pnlTop.setPreferredSize(new Dimension(250, 80));
        pnlTop.setBackground(DefaultColor);
        pnlTop.setLayout(new BorderLayout(0, 0));
        this.add(pnlTop, BorderLayout.NORTH);

        accountInformationPanel = new MyAccount();
        pnlTop.add(accountInformationPanel, BorderLayout.CENTER);



        bar1 = new JPanel();
        bar1.setBackground(new Color(204, 214, 219));
        bar1.setPreferredSize(new Dimension(1, 0));
        pnlTop.add(bar1, BorderLayout.EAST);

        bar2 = new JPanel();
        bar2.setBackground(new Color(204, 214, 219));
        bar2.setPreferredSize(new Dimension(0, 1));
        pnlTop.add(bar2, BorderLayout.SOUTH);

        pnlCenter = new JPanel();
        pnlCenter.setPreferredSize(new Dimension(230, 600));
        pnlCenter.setBackground(DefaultColor);
        pnlCenter.setLayout(new FlowLayout(0, 0, 5));

        bar3 = new JPanel();
        bar3.setBackground(new Color(204, 214, 219));
        bar3.setPreferredSize(new Dimension(1, 1));
        this.add(bar3, BorderLayout.EAST);

        scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        pnlBottom = new JPanel();
        pnlBottom.setPreferredSize(new Dimension(250, 50));
        pnlBottom.setBackground(DefaultColor);
        pnlBottom.setLayout(new BorderLayout(0, 0));


        bar4 = new JPanel();
        bar4.setBackground(new Color(204, 214, 219));
        bar4.setPreferredSize(new Dimension(1, 1));
        pnlBottom.add(bar4, BorderLayout.EAST);

        this.add(pnlBottom, BorderLayout.SOUTH);

        for (int i = 0; i < menuItemInformation.length; i++) {
            if (i + 1 == menuItemInformation.length) {
                listItem[i] = new ItemTaskbar(menuItemInformation[i][1], menuItemInformation[i][0]);
                pnlBottom.add(listItem[i]);
            } else {
                listItem[i] = new ItemTaskbar(menuItemInformation[i][1], menuItemInformation[i][0]);
                pnlCenter.add(listItem[i]);
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

    public JPanel getPnlCenter() {
        return pnlCenter;
    }

    public JPanel getPnlTop() {
        return pnlTop;
    }

    public JPanel getPnlBottom() {
        return pnlBottom;
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
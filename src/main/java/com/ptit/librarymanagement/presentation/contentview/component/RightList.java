package com.ptit.librarymanagement.presentation.contentview.component;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.ptit.librarymanagement.presentation.mainview.menu.ItemTaskbar;

import javax.swing.*;
import java.awt.*;

public class RightList extends JScrollPane {

    private JPanel right;
    private String title;
    private Color backgroundColor = new Color(240, 247, 250);

    public RightList(String title) {
        super(null, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.title = title;
        this.right = new JPanel();
        this.right.setBackground(backgroundColor);
        this.right.setLayout(new FlowLayout(0, 4, 10));
        this.right.setPreferredSize(new Dimension(400, 800));
        JLabel titleLabel = new JLabel(this.title);
        titleLabel.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 1, 16));
        this.right.add(titleLabel);
        super.setViewportView(this.right);
        ListCustomersInDePot();
        this.setBackground(backgroundColor);



    }

    public void ListCustomersInDePot(/*ArrayList<SanPhamDTO> result*/) {
//        List<Integer> result = new List();
        this.right.removeAll();
        JLabel tit = new JLabel("Danh sách sản phẩm đang có ở khu vực");
        tit.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 1, 16));
        right.add(tit);
        ItemTaskbar listItem[] = new ItemTaskbar[3];
        int i = 0;
        for (int j = 0; j < 10; j++) {
            right.add(new ItemTaskbar("hh", "dd"));
            ++i;
        }

        right.repaint();
        right.validate();
    }


}

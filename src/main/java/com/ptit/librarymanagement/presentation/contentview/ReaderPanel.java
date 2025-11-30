package com.ptit.librarymanagement.presentation.contentview;


import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.ptit.librarymanagement.dto.BookDTO;
import com.ptit.librarymanagement.dto.ReaderDTO;
import com.ptit.librarymanagement.presentation.contentview.component.*;
import com.ptit.librarymanagement.presentation.mainview.menu.ItemTaskbar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
public class ReaderPanel extends JPanel {
    private JPanel bookInCategory;
    private JPanel contentCenter;
    private IntegratedSearch integratedSearch;
    private ScrollTable scrollTable;
    private JScrollPane scrollPane;
    private JScrollPane scrollTableSanPham;
    private FunctionToolBar functionToolBar;
    private PanelBorderRadius functionBar, main;


    private ReaderPanelModel model;

    private Color backgroundColor;

    public ReaderPanel() {
        model = new ReaderPanelModel();
        initComponent();

    }

    private void initComponent () {
        backgroundColor = new Color(240, 247, 250);
        this.setBackground(backgroundColor);
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(true);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(backgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        HashMap<String, ButtonToolBar> btn = new LinkedHashMap<>();
        btn.put("create", new ButtonToolBar("THÊM", "icon/create.svg"));
        btn.put("delete", new ButtonToolBar("XÓA", "icon/delete.svg"));
        btn.put("update", new ButtonToolBar("SỬA", "icon/update.svg"));

        functionToolBar = new FunctionToolBar(btn);

        functionBar.add(functionToolBar);

        integratedSearch = new IntegratedSearch(new String[]{"Id", "Họ và tên"});

        functionBar.add(integratedSearch);

        functionBar.add(integratedSearch);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        scrollTable = new ScrollTable(new String[]{"Id", "Họ và tên", "Giới tính", "Địa chỉ", "Ngày sinh", "Số điện thoại", "Email"});
        scrollTableSanPham = new JScrollPane();

        scrollTableSanPham.setViewportView(scrollTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        TableColumnModel columnModel = scrollTable.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(1).setCellRenderer(leftRenderer);
        columnModel.getColumn(2).setCellRenderer(centerRenderer);
        columnModel.getColumn(3).setCellRenderer(leftRenderer);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);
        columnModel.getColumn(5).setCellRenderer(centerRenderer);
        columnModel.getColumn(6).setCellRenderer(leftRenderer);
        scrollTable.setFocusable(false);




        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableSanPham);

        bookInCategory = new JPanel();
        bookInCategory.setBackground(backgroundColor);
        bookInCategory.setLayout(new FlowLayout(0, 4, 10));
        bookInCategory.setPreferredSize(new Dimension(400, 800));

        scrollPane = new JScrollPane(bookInCategory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setBackground(backgroundColor);
        contentCenter.add(scrollPane, BorderLayout.EAST);
        this.add(contentCenter);
    }

    public void loadAllDada () {

        DefaultTableModel tableModel = scrollTable.getTableModel();
        tableModel.setRowCount(0);
        for (ReaderDTO categoryDTO : model.listReaders) {
            tableModel.addRow(categoryDTO.getRow());
        }
    }

    public void showListBookOfCategory() {
        bookInCategory.removeAll();
        JLabel tit = new JLabel("Sách độc giả đang mượn");
        tit.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));
        bookInCategory.add(tit);
        int size = model.listBooks.size();
        ItemTaskbar listItem[] = new ItemTaskbar[size];
        for (int i = 0; i < size; i++) {
            BookDTO bookDTO = model.listBooks.get(i);
            listItem[i] = new ItemTaskbar("imagePath", bookDTO.getTitle(), bookDTO.getQuantity());
            bookInCategory.add(listItem[i]);
        }
        bookInCategory.repaint();
        bookInCategory.validate();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ReaderPanelModel {
        List<ReaderDTO> listReaders = new ArrayList<>();
        List<BookDTO> listBooks = new ArrayList<>();
    }
}

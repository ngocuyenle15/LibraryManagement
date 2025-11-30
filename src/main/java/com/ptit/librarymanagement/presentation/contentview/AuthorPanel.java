package com.ptit.librarymanagement.presentation.contentview;


import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.ptit.librarymanagement.dto.AuthorDTO;
import com.ptit.librarymanagement.dto.BookDTO;
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
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;

@Getter
public class AuthorPanel extends JPanel {
    private JPanel bookOfAuthor;
    private JPanel contentCenter;
    private IntegratedSearch integratedSearch;
    private ScrollTable scrollTable;
    private JScrollPane scrollPane;
    private JScrollPane scrollTableAuthor;
    private FunctionToolBar functionToolBar;
    private PanelBorderRadius functionBar, main;
    private AuthorPanelModel model;
    private Color backgroundColor;

    public AuthorPanel() {
        this.model = new AuthorPanelModel();
        initComponent();
    }

    private void initComponent () {
        backgroundColor = new Color(240, 247, 250);
        this.setBackground(backgroundColor);
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(true);

        functionToolBar = new FunctionToolBar(((Supplier<HashMap<String, ButtonToolBar>>) () -> {
            HashMap<String, ButtonToolBar> btn = new LinkedHashMap<>();
            btn.put("create", new ButtonToolBar("THÊM", "icon/create.svg"));
            btn.put("delete", new ButtonToolBar("XÓA", "icon/delete.svg"));
            btn.put("update", new ButtonToolBar("SỬA", "icon/update.svg"));
            return btn;
        }).get());

        integratedSearch = new IntegratedSearch(new String[]{"Id", "Tên tác giả"});

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        functionBar.add(functionToolBar);
        functionBar.add(integratedSearch);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(backgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        contentCenter.add(functionBar, BorderLayout.NORTH);

        scrollTable = new ScrollTable(new String[]{"Id", "Họ và tên", "Giới tính", "Năm sinh"});
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        this.scrollTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        this.scrollTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        this.scrollTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        this.scrollTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);


        scrollTable.setFocusable(false);

        scrollTableAuthor = new JScrollPane();
        scrollTableAuthor.setViewportView(scrollTable);

        main = new PanelBorderRadius();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableAuthor);

        bookOfAuthor = new JPanel();
        bookOfAuthor.setBackground(backgroundColor);
        bookOfAuthor.setLayout(new FlowLayout(0, 4, 10));
        bookOfAuthor.setPreferredSize(new Dimension(400, 800));

        scrollPane = new JScrollPane(bookOfAuthor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setBackground(backgroundColor);
        contentCenter.add(scrollPane, BorderLayout.EAST);
        this.add(contentCenter);
    }

    public void loadDataInTable() {
        DefaultTableModel tableModel = scrollTable.getTableModel();
        tableModel.setRowCount(0);
        for (AuthorDTO authorDTO : model.listAuthors) {
            tableModel.addRow(authorDTO.getRow());
        }
    }

    public void showListBookOfAuthor() {
        bookOfAuthor.removeAll();
        JLabel title = new JLabel("Sách của tác giả");
        title.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));
        bookOfAuthor.add(title);
        int size = model.bookDTOS.size();
        ItemTaskbar listItem[] = new ItemTaskbar[size];
        for (int i = 0; i < size; i++) {
            BookDTO bookDTO = model.bookDTOS.get(i);
            listItem[i] = new ItemTaskbar("imagePath", bookDTO.getTitle(), bookDTO.getQuantity());
            bookOfAuthor.add(listItem[i]);
        }
        bookOfAuthor.repaint();
        bookOfAuthor.validate();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class AuthorPanelModel {
        List<AuthorDTO> listAuthors = new ArrayList<>();
        List<BookDTO> bookDTOS = new ArrayList<>();
    }
}

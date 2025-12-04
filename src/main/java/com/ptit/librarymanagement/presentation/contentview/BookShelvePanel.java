package com.ptit.librarymanagement.presentation.contentview;


import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.ShelfDTO;
import com.ptit.librarymanagement.presentation.contentview.component.*;
import com.ptit.librarymanagement.presentation.mainview.menu.ItemMenu;

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
import java.util.function.Supplier;


public class BookShelvePanel extends JPanel {
    private JPanel bookOnShelf;
    private JPanel contentCenter;
    private IntegratedSearch integratedSearch;
    private ScrollTable scrollTable;
    private JScrollPane scrollPane;
    private JScrollPane scrollTableShelf;
    private FunctionToolBar functionToolBar;
    private PanelBorderRadius functionBar, main;
    private BookShelvePanelModel model;
    private Color backgroundColor;

    public BookShelvePanel() {
        model = new BookShelvePanelModel();
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

        integratedSearch = new IntegratedSearch(new String[]{"Id", "Vị trí", "Tên kệ"});

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        functionBar.add(functionToolBar);
        functionBar.add(integratedSearch);

        scrollTable = new ScrollTable(new String[]{"Id", "Vị trí", "Tên kệ"});
        scrollTableShelf = new JScrollPane();
        scrollTableShelf.setViewportView(scrollTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        TableColumnModel columnModel = scrollTable.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
//        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(2).setCellRenderer(centerRenderer);
        scrollTable.setFocusable(false);

        main = new PanelBorderRadius();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(scrollTableShelf);

        bookOnShelf = new JPanel();
        bookOnShelf.setBackground(backgroundColor);
        bookOnShelf.setLayout(new FlowLayout(0, 4, 10));
        bookOnShelf.setPreferredSize(new Dimension(400, 800));

        scrollPane = new JScrollPane(bookOnShelf, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setBackground(backgroundColor);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(backgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        contentCenter.add(main, BorderLayout.CENTER);
        contentCenter.add(functionBar, BorderLayout.NORTH);
        contentCenter.add(scrollPane, BorderLayout.EAST);
        this.add(contentCenter);
    }

    public void loadDataInTable() {
        DefaultTableModel tableModel = scrollTable.getTableModel();
        tableModel.setRowCount(0);
        for (ShelfDTO ShelfDTO : model.getListShelf()) {
            tableModel.addRow(ShelfDTO.getRow());
        }
    }

    public void showListBookOnShelf() {
        bookOnShelf.removeAll();
        JLabel tit = new JLabel("Sách thuộc vị trí đang chọn");
        tit.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));
        bookOnShelf.add(tit);
        int size = model.listBooks.size();
        ItemMenu listItem[] = new ItemMenu[size];
        for (int i = 0; i < size; i++) {
            BookDTO bookDTO = model.listBooks.get(i);
            listItem[i] = new ItemMenu("imagePath", bookDTO.getTitle(), bookDTO.getQuantity());
            bookOnShelf.add(listItem[i]);
        }
        bookOnShelf.repaint();
        bookOnShelf.validate();
    }

    public JPanel getBookOnShelf() {
        return bookOnShelf;
    }

    public JPanel getContentCenter() {
        return contentCenter;
    }

    public IntegratedSearch getIntegratedSearch() {
        return integratedSearch;
    }

    public ScrollTable getScrollTable() {
        return scrollTable;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JScrollPane getScrollTableShelf() {
        return scrollTableShelf;
    }

    public FunctionToolBar getFunctionToolBar() {
        return functionToolBar;
    }

    public PanelBorderRadius getFunctionBar() {
        return functionBar;
    }

    public PanelBorderRadius getMain() {
        return main;
    }

    public BookShelvePanelModel getModel() {
        return model;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }


    public class BookShelvePanelModel {
        List<ShelfDTO> listShelf = new ArrayList<>();
        List<BookDTO> listBooks = new ArrayList<>();

        public BookShelvePanelModel(List<ShelfDTO> listShelf, List<BookDTO> listBooks) {
            this.listShelf = listShelf;
            this.listBooks = listBooks;
        }

        public BookShelvePanelModel() {
        }

        public List<ShelfDTO> getListShelf() {
            return listShelf;
        }

        public void setListShelf(List<ShelfDTO> listShelf) {
            this.listShelf = listShelf;
        }

        public List<BookDTO> getListBooks() {
            return listBooks;
        }

        public void setListBooks(List<BookDTO> listBooks) {
            this.listBooks = listBooks;
        }
    }
}

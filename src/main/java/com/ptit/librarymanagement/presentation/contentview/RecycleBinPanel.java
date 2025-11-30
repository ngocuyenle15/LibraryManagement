package com.ptit.librarymanagement.presentation.contentview;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.transactionmanager.TransactionManager;
import com.ptit.librarymanagement.dto.BookDTO;
import com.ptit.librarymanagement.presentation.contentview.component.*;
import com.ptit.librarymanagement.service.BookService;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class RecycleBinPanel extends JPanel {
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4;
    private Color BackgroundColor = new Color(240, 247, 250);
    private ScrollTable scrollTable;
    private PanelBorderRadius functionBar;
    private IntegratedSearch integratedSearch;
    private FunctionToolBar functionToolBar;
    private JPanel contentCenter;
    private PanelBorderRadius main;
    private BookPanelModel model;
    private BookService bookService;
    private PaginationPanel paginationPanel;




    public RecycleBinPanel() {
        this.bookService = TransactionManager.createService(BookService.class, DbConnection.getConnection());
        this.model = new BookPanelModel();
        initComponent();
        decorateView();

        this.functionBar.add(functionToolBar);
        this.functionBar.add(integratedSearch);
        this.add(contentCenter, BorderLayout.CENTER);
        this.add(functionBar, BorderLayout.NORTH);
        this.add(scrollTable, BorderLayout.CENTER);




    }


    private void initComponent() {
        this.scrollTable = new ScrollTable(new String[]{"Id", "Tiêu đề", "Số luợng", "Nhà xuất bản", "Tác giả", "Danh mục", "Vị trí"});
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        this.scrollTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        this.scrollTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        this.scrollTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        HashMap<String, ButtonToolBar> btn = new LinkedHashMap<>();
        btn.put("delete", new ButtonToolBar("Xóa", "icon/delete.svg"));
        btn.put("undo", new ButtonToolBar("Hoàn tác", "icon/undo.svg"));



        this.functionToolBar = new FunctionToolBar(btn);
        this.integratedSearch = new IntegratedSearch(new String[]{"Id", "Tiêu đề"});
        functionBar = new PanelBorderRadius();
        main = new PanelBorderRadius();
        contentCenter = new JPanel();


    }

    private void decorateView() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(true);
        this.initPadding();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
    }

    public void loadDataInTable () {
        DefaultTableModel tableModel = this.scrollTable.getTableModel();
        tableModel.setRowCount(0);
        for (BookDTO book : model.getBookDTOS()) {
            tableModel.addRow(book.getRow());
        }
    }


    private void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }





    @Getter
    @Setter
    @NoArgsConstructor
    public static class BookPanelModel {
        private List <BookDTO> bookDTOS;
    }
}


package com.ptit.librarymanagement.presentation.contentview;

import com.ptit.librarymanagement.common.paging.Pageable;
import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.presentation.contentview.component.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;


public class BookPanel extends JPanel {
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4;
    private Color backgroundColor = new Color(240, 247, 250);
    private ScrollTable scrollTable;
    private PanelBorderRadius functionBar;
    private IntegratedSearch integratedSearch;
    private FunctionToolBar functionToolBar;
    private JPanel contentCenter;
    private PanelBorderRadius main;
    private BookPanelModel model;
    private PaginationPanel paginationPanel;


    public BookPanel() {
        this.model = new BookPanelModel();
        initComponent();
        this.add(contentCenter, BorderLayout.CENTER);
        this.add(functionBar, BorderLayout.NORTH);
        this.add(scrollTable, BorderLayout.CENTER);
        this.add(paginationPanel, BorderLayout.SOUTH);
    }
    public JPanel getPnlBorder1() {
        return this.pnlBorder1;
    }

    public JPanel getPnlBorder2() {
        return this.pnlBorder2;
    }

    public JPanel getPnlBorder3() {
        return this.pnlBorder3;
    }

    public JPanel getPnlBorder4() {
        return this.pnlBorder4;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public ScrollTable getScrollTable() {
        return this.scrollTable;
    }

    public PanelBorderRadius getFunctionBar() {
        return this.functionBar;
    }

    public IntegratedSearch getIntegratedSearch() {
        return this.integratedSearch;
    }

    public FunctionToolBar getFunctionToolBar() {
        return this.functionToolBar;
    }

    public JPanel getContentCenter() {
        return this.contentCenter;
    }

    public PanelBorderRadius getMain() {
        return this.main;
    }

    public BookPanelModel getModel() {
        return this.model;
    }

    public PaginationPanel getPaginationPanel() {
        return this.paginationPanel;
    }

    public void setPnlBorder1(JPanel pnlBorder1) {
        this.pnlBorder1 = pnlBorder1;
    }

    public void setPnlBorder2(JPanel pnlBorder2) {
        this.pnlBorder2 = pnlBorder2;
    }

    public void setPnlBorder3(JPanel pnlBorder3) {
        this.pnlBorder3 = pnlBorder3;
    }

    public void setPnlBorder4(JPanel pnlBorder4) {
        this.pnlBorder4 = pnlBorder4;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setScrollTable(ScrollTable scrollTable) {
        this.scrollTable = scrollTable;
    }

    public void setFunctionBar(PanelBorderRadius functionBar) {
        this.functionBar = functionBar;
    }

    public void setIntegratedSearch(IntegratedSearch integratedSearch) {
        this.integratedSearch = integratedSearch;
    }

    public void setFunctionToolBar(FunctionToolBar functionToolBar) {
        this.functionToolBar = functionToolBar;
    }

    public void setContentCenter(JPanel contentCenter) {
        this.contentCenter = contentCenter;
    }

    public void setMain(PanelBorderRadius main) {
        this.main = main;
    }

    public void setModel(BookPanelModel model) {
        this.model = model;
    }

    public void setPaginationPanel(PaginationPanel paginationPanel) {
        this.paginationPanel = paginationPanel;
    }


    private void initComponent() {
        this.setBackground(backgroundColor);
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(true);
        this.initPadding();

        this.scrollTable = new ScrollTable(new String[]{"Id", "Tiêu đề", "Số luợng", "Nhà xuất bản", "Tác giả", "Danh mục", "Vị trí"});
        this.scrollTable.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        this.scrollTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        this.scrollTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        this.scrollTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);


        this.functionToolBar = new FunctionToolBar(
                ((Supplier<HashMap<String, ButtonToolBar>>) () -> {
                    HashMap<String, ButtonToolBar> btn = new LinkedHashMap<>();
                    btn.put("create", new ButtonToolBar("THÊM", "icon/create.svg"));
                    btn.put("delete", new ButtonToolBar("XÓA", "icon/delete.svg"));
                    btn.put("update", new ButtonToolBar("SỬA", "icon/update.svg"));
                    btn.put("view", new ButtonToolBar("CHI TIẾT", "icon/detail.svg"));
                    return btn;
                }).get()
        );

        this.integratedSearch = new IntegratedSearch(new String[]{"Id", "Tiêu đề"});

        this.functionBar = new PanelBorderRadius();
        this.functionBar.setPreferredSize(new Dimension(0, 100));
        this.functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        this.functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.functionBar.add(functionToolBar);
        this.functionBar.add(integratedSearch);

        this.main = new PanelBorderRadius();
        this.main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        this.main.setBorder(new EmptyBorder(0, 0, 0, 0));

        this.contentCenter = new JPanel();
        this.contentCenter.setBackground(backgroundColor);
        this.contentCenter.setLayout(new BorderLayout(10, 10));
        this.paginationPanel = new PaginationPanel(model.pageable.getTotalRecord(),model.pageable.getPageSize());

    }

    public void loadDataInTable () {
        DefaultTableModel tableModel = this.scrollTable.getTableModel();
        tableModel.setRowCount(0);
        for (BookDTO book : model.getListBooks()) {
            tableModel.addRow(book.getRow());
        }
    }


    private void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(backgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(backgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(backgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(backgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }



    public static class BookPanelModel {
        private List <BookDTO> listBooks;
        private Pageable pageable = new Pageable();

        public BookPanelModel(List<BookDTO> listBooks, Pageable pageable) {
            this.listBooks = listBooks;
            this.pageable = pageable;
        }

        public BookPanelModel() {
        }

        public List<BookDTO> getListBooks() {
            return listBooks;
        }

        public void setListBooks(List<BookDTO> listBooks) {
            this.listBooks = listBooks;
        }

        public Pageable getPageable() {
            return pageable;
        }

        public void setPageable(Pageable pageable) {
            this.pageable = pageable;
        }
    }

}

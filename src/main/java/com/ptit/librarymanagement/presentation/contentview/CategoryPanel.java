package com.ptit.librarymanagement.presentation.contentview;


import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.CategoryDTO;
import com.ptit.librarymanagement.presentation.contentview.component.*;
import com.ptit.librarymanagement.presentation.mainview.menu.ItemTaskbar;


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


public class CategoryPanel extends JPanel {
    private JPanel bookInCategory;
    private JPanel contentCenter;
    private IntegratedSearch integratedSearch;
    private ScrollTable scrollTable;
    private JScrollPane scrollPane;
    private JScrollPane scrollTableCategory;
    private FunctionToolBar functionToolBar;
    private PanelBorderRadius functionBar, main;
    private CategoryPanelModel model;
    private Color backgroundColor;

    public CategoryPanel() {
        model = new CategoryPanelModel();
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


        functionToolBar = new FunctionToolBar(((Supplier<HashMap<String, ButtonToolBar>>) () -> {
            HashMap<String, ButtonToolBar> btn = new LinkedHashMap<>();
            btn.put("create", new ButtonToolBar("THÊM", "icon/create.svg"));
            btn.put("delete", new ButtonToolBar("XÓA", "icon/delete.svg"));
            btn.put("update", new ButtonToolBar("SỬA", "icon/update.svg"));
            return btn;
        }).get());

        functionBar.add(functionToolBar);

        integratedSearch = new IntegratedSearch(new String[]{"Id", "Tên danh mục"});

        functionBar.add(integratedSearch);

        functionBar.add(integratedSearch);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        scrollTable = new ScrollTable(new String[]{"Id", "Danh mục"});
        scrollTableCategory = new JScrollPane();



        scrollTableCategory.setViewportView(scrollTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        TableColumnModel columnModel = scrollTable.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
//        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(1).setCellRenderer(leftRenderer);
        scrollTable.setFocusable(false);






        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableCategory);

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

    public void loadDataInTable() {
        DefaultTableModel tableModel = scrollTable.getTableModel();
        tableModel.setRowCount(0);
        for (CategoryDTO categoryDTO : model.listCategory) {
            tableModel.addRow(categoryDTO.getRow());
        }
    }

    public void showListBookOfCategory(CategoryDTO categoryDTO) {
        bookInCategory.removeAll();
        JLabel tit = new JLabel(String.format("Sách thuộc danh mục \"%s\"", categoryDTO.getName()));
        tit.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 1, 16));
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

    public JPanel getBookInCategory() {
        return bookInCategory;
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

    public JScrollPane getScrollTableCategory() {
        return scrollTableCategory;
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

    public CategoryPanelModel getModel() {
        return model;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }


    public class CategoryPanelModel {
        List<CategoryDTO> listCategory = new ArrayList<>();
        List<BookDTO> listBooks = new ArrayList<>();

        public CategoryPanelModel(List<CategoryDTO> listCategory, List<BookDTO> listBooks) {
            this.listCategory = listCategory;
            this.listBooks = listBooks;
        }

        public CategoryPanelModel() {
        }

        public List<CategoryDTO> getListCategory() {
            return listCategory;
        }

        public void setListCategory(List<CategoryDTO> listCategory) {
            this.listCategory = listCategory;
        }

        public List<BookDTO> getListBooks() {
            return listBooks;
        }

        public void setListBooks(List<BookDTO> listBooks) {
            this.listBooks = listBooks;
        }
    }
}

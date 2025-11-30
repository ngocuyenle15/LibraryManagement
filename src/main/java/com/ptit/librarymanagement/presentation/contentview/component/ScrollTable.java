package com.ptit.librarymanagement.presentation.contentview.component;



import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;


// refactor lại sau
@Getter
@Setter
public class ScrollTable extends JScrollPane {
    private JTable table;
    private String[] columnName;
    private TableColumnModel columnModel;
    private DefaultTableModel tableModel;
    private DefaultTableCellRenderer centerRenderer;

    public ScrollTable (String[] columnName) {
        this.intiComponent(columnName);
//        this.decorateTable();
        this.setViewportView(table);
    }

    private void intiComponent (String[] columnName) {
        this.table = new JTable();
        this.columnName = columnName;
        this.tableModel = new DefaultTableModel();
        this.table.setModel(tableModel);
        this.columnModel = table.getColumnModel();
        tableModel.setColumnIdentifiers(this.columnName);

    }

    private void decorateTable () {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setViewportBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        JTableHeader header = this.table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setOpaque(true);
        header.setBackground(new Color(233, 236, 242));
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder());
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        header.setReorderingAllowed(false);
        this.table.setShowHorizontalLines(true);
        this.table.setShowVerticalLines(true);
        this.table.setGridColor(new Color(220, 220, 220));
        this.table.setIntercellSpacing(new Dimension(1, 1));
        this.table.setBorder(BorderFactory.createEmptyBorder());
        this.table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        this.table.setShowVerticalLines(false);
        for (int i = 0; i < this.columnName.length; i++) {
            this.columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
        this.table.getColumnModel().getColumn(1).setPreferredWidth(180);
        this.table.setFocusable(false);
        this.table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(this.table, 2, TableSorter.INTEGER_COMPARATOR);
        this.table.setDefaultEditor(Object.class, null);
    }


}

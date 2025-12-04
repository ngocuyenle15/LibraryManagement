package com.ptit.librarymanagement.presentation.contentview;


import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.BorrowCardDTO;
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


public class BorrowCardPanel extends JPanel {
    private JPanel bookInBorrowCard;
    private JPanel contentCenter;
    private IntegratedSearch integratedSearch;
    private ScrollTable scrollTable;
    private JScrollPane scrollPane;
    private JScrollPane scrollTableBorrowCard;
    private FunctionToolBar functionToolBar;
    private PanelBorderRadius functionBar, main;
    private BorrowCardPanelModel model;
    private Color backgroundColor;

    private MenuSelectionViewBorrowCardPanel selectionView;

    public BorrowCardPanel() {
        model = new BorrowCardPanelModel();
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
//            btn.put("view", new ButtonToolBar("Chi tiết", "icon/punish.svg"));
            btn.put("punish", new ButtonToolBar("Phạt", "icon/punish.svg"));
            btn.put("returnBook", new ButtonToolBar("Trả sách", "icon/returnbook.svg"));

            return btn;
        }).get());

        integratedSearch = new IntegratedSearch(new String[]{"Id", "Tên độc giả"});

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        functionBar.add(functionToolBar);
        functionBar.add(integratedSearch);

        scrollTable = new ScrollTable(new String[]{"Id", "Độc giả" ,"Ngày mượn", "Ngày trả", "Trạng thái"});
        scrollTableBorrowCard = new JScrollPane();
        scrollTableBorrowCard.setViewportView(scrollTable);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = scrollTable.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(1).setCellRenderer(leftRenderer);
        columnModel.getColumn(2).setCellRenderer(centerRenderer);
        columnModel.getColumn(3).setCellRenderer(centerRenderer);
        scrollTable.setFocusable(false);


        main = new PanelBorderRadius();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(scrollTableBorrowCard);

        bookInBorrowCard = new JPanel();
        bookInBorrowCard.setBackground(backgroundColor);
        bookInBorrowCard.setLayout(new FlowLayout(0, 4, 10));
        bookInBorrowCard.setPreferredSize(new Dimension(400, 800));

        scrollPane = new JScrollPane(bookInBorrowCard, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setBackground(backgroundColor);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(backgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        contentCenter.add(scrollPane, BorderLayout.EAST);
        contentCenter.add(main, BorderLayout.CENTER);


        selectionView = new MenuSelectionViewBorrowCardPanel(new JButton("Đang mượn"), new JButton("Quá hạn"), new JButton("Đã trả"));
        selectionView.setLayout(new FlowLayout(FlowLayout.LEFT));


        contentCenter.add(selectionView, BorderLayout.SOUTH);

        contentCenter.add(functionBar, BorderLayout.NORTH);
        this.add(contentCenter);
    }

    public void loadDataInTable() {
        DefaultTableModel tableModel = scrollTable.getTableModel();
        tableModel.setRowCount(0);
        for (BorrowCardDTO cardDTO : model.getListBorrowCards()) {
            tableModel.addRow(cardDTO.getRow());
        }
    }

    public void showListBookOfBorrowCard(BorrowCardDTO cardDTO) {
        bookInBorrowCard.removeAll();
        JLabel title = new JLabel("Sách liên kết với thẻ mượn");
        title.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));
        bookInBorrowCard.add(title);
        int size = model.listBooks.size();
        ItemMenu listItem[] = new ItemMenu[size];
        for (int i = 0; i < size; i++) {
            BookDTO bookDTO = model.listBooks.get(i);
            listItem[i] = new ItemMenu("imagePath", bookDTO.getTitle(), bookDTO.getQuantity());
            bookInBorrowCard.add(listItem[i]);
        }
        if (listItem.length == 0) {
            bookInBorrowCard.add(new JLabel("Toàn bộ sách liên kết với thẻ mượn đã bị xóa"));
        }
        bookInBorrowCard.repaint();
        bookInBorrowCard.validate();
    }

    public JPanel getBookInBorrowCard() {
        return bookInBorrowCard;
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

    public JScrollPane getScrollTableBorrowCard() {
        return scrollTableBorrowCard;
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

    public BorrowCardPanelModel getModel() {
        return model;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public MenuSelectionViewBorrowCardPanel getSelectionView() {
        return selectionView;
    }


    public class BorrowCardPanelModel {
        List<BorrowCardDTO> listBorrowCards = new ArrayList<>();
        List<BookDTO> listBooks = new ArrayList<>();

        public BorrowCardPanelModel(List<BorrowCardDTO> listBorrowCards, List<BookDTO> listBooks) {
            this.listBorrowCards = listBorrowCards;
            this.listBooks = listBooks;
        }

        public BorrowCardPanelModel() {
        }

        public List<BorrowCardDTO> getListBorrowCards() {
            return listBorrowCards;
        }

        public void setListBorrowCards(List<BorrowCardDTO> listBorrowCards) {
            this.listBorrowCards = listBorrowCards;
        }

        public List<BookDTO> getListBooks() {
            return listBooks;
        }

        public void setListBooks(List<BookDTO> listBooks) {
            this.listBooks = listBooks;
        }
    }
}

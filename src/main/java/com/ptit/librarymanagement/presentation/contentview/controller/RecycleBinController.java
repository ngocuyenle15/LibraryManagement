package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.presentation.contentview.RecycleBinPanel;
import com.ptit.librarymanagement.service.*;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import javax.swing.*;
import java.awt.event.*;
import java.util.Optional;

public class RecycleBinController {
    private final RecycleBinPanel recycleBinPanel;
    private final RecycleBinPanel.BookPanelModel model;
    private final BookService bookService;

    public RecycleBinController(RecycleBinPanel bookPanel) {
        this.bookService = new BookService(DbConnection.getConnection());
        this.recycleBinPanel = bookPanel;
        this.model = this.recycleBinPanel.getModel();
        model.setBookDTOS(bookService.getAllBook(true));
        this.recycleBinPanel.loadDataInTable();
        initController();
    }

    public RecycleBinController(RecycleBinPanel bookPanel, ServiceFactory serviceFactory) {
        this.bookService = serviceFactory.getBookService();
        this.recycleBinPanel = bookPanel;
        this.model = this.recycleBinPanel.getModel();
        model.setBookDTOS(bookService.getAllBook(true));
        this.recycleBinPanel.loadDataInTable();
        initController();
    }

    public void loadAllDataInBookRecycleBinPanel() {
        handlerReloadPanel();
    }


    private void initController () {
        this.recycleBinPanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearchForm();
            }
        });
        this.recycleBinPanel.getIntegratedSearch().getBtnReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerReloadPanel();
            }
        });
        this.recycleBinPanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<BookDTO> bookSelectedRow = getBookSelectedRow();
                if (bookSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(recycleBinPanel, "Vui lòng lựa chọn sách");
                }
                else {
                    handlerDeleteBookPermanent(bookSelectedRow.get());
                }

            }
        });

        this.recycleBinPanel.getFunctionToolBar().getButtons().get("undo").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<BookDTO> bookSelectedRow = getBookSelectedRow();
                if (bookSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(recycleBinPanel, "Vui lòng lựa chọn sách");
                }
                else {
                    handlerUndo(bookSelectedRow.get());
                }
            }
        });
    }


    private void handleSearchForm () {
        String txt = this.recycleBinPanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)this.recycleBinPanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        BookDTO book = BookDTO.builder()
                .id(searchOption.equals("Id") && txt.matches("^[1-9]\\d*$") ? Integer.parseInt(txt) : null)
                .title(searchOption.equals("Tiêu đề") ? txt : null).build();
        model.setBookDTOS(bookService.searchBook(book, true));
        recycleBinPanel.loadDataInTable();
    }
    private void handlerReloadPanel() {
        this.recycleBinPanel.getIntegratedSearch().getCbxChoose().setSelectedItem("Id");
        this.recycleBinPanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setBookDTOS(bookService.getAllBook(true));
        this.recycleBinPanel.loadDataInTable();
    }
    private void handlerDeleteBookPermanent(BookDTO bookDTO) {
        int input = JOptionPane.showConfirmDialog(null, "Xóa sách vĩnh viễn", "Xóa sách vĩnh viễn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            try {
                bookService.deletePermanently(bookDTO);
                model.setBookDTOS(bookService.getAllBook(true));
                recycleBinPanel.loadDataInTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    private void handlerUndo(BookDTO bookDTO) {
        int input = JOptionPane.showConfirmDialog(null, "Hoàn tác xóa", "Chuyển sách khỏi thùng rác", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            bookService.softDelete(bookDTO, false);
            model.setBookDTOS(bookService.getAllBook(true));
            recycleBinPanel.loadDataInTable();
        }

    }

    private Optional<BookDTO> getBookSelectedRow() {
        int selectedRow = recycleBinPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) recycleBinPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(bookService.getBookById(id, true));
    }





}

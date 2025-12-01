package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.model.dto.ReaderDTO;
import com.ptit.librarymanagement.presentation.dialog.ReaderDialog;
import com.ptit.librarymanagement.presentation.contentview.ReaderPanel;
import com.ptit.librarymanagement.service.BookService;
import com.ptit.librarymanagement.service.BorrowCardService;
import com.ptit.librarymanagement.service.ReaderService;
import com.ptit.librarymanagement.context.service.ServiceFactory;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class ReaderController {
    private final ReaderPanel readerPanel;
    private final ReaderPanel.ReaderPanelModel model;
    private final ReaderService readerService;
    private final BorrowCardService borrowCardService;
    private final BookService bookService;
    private final ValidationService validationService;

    public ReaderController (ReaderPanel readerPanel, ServiceFactory serviceFactory) {
        this.readerPanel = readerPanel;
        this.model = readerPanel.getModel();
        this.readerService = serviceFactory.getReaderService();
        this.borrowCardService = serviceFactory.getBorrowCardService();
        this.bookService = serviceFactory.getBookService();
        this.validationService = serviceFactory.getValidationService();
        model.setListReaders(readerService.getAllReader());
        readerPanel.loadAllDada();
        initController();
    }

    public void initController () {
        readerPanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handlerSearchForm();
            }
        });
        readerPanel.getIntegratedSearch().getBtnReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerReloadPanel();
            }
        });


        readerPanel.getScrollTable().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerActionOnRow();
            }
        });

        readerPanel.getFunctionToolBar().getButtons().get("create").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerCreateReader();

            }
        });
        readerPanel.getFunctionToolBar().getButtons().get("update").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<ReaderDTO> readerSelectedRow = getReaderSelectedRow();
                if (readerSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(readerPanel, "Vui lòng lựa chọn độc giả");
                }
                else {
                    handlerUpdateReader(readerSelectedRow.get());
                }
            }

        });
        readerPanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<ReaderDTO> readerSelectedRow = getReaderSelectedRow();
                if (readerSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(readerPanel, "Vui lòng lựa chọn độc giả");
                }
                else {
                    handlerDeleteReader(readerSelectedRow.get());
                }
            }
        });


    }

    private Optional<ReaderDTO> getReaderSelectedRow() {
        int selectedRow = readerPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) readerPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(readerService.getReaderById(id));
    }
    private void handlerUpdateReader (ReaderDTO readerDTO) {
        ReaderDialog dialog =  new ReaderDialog(new JFrame(), true, "Sửa thông tin độc giả", "update");
        dialog.setDefaultTextInField(readerDTO);
        dialog.getIdInput().setText(Integer.toString(readerDTO.getId()));
        dialog.getUpdateButton().addActionListener(l -> {

            // todo: chua validation date
            ReaderDTO readerUpdate = dialog.getObjectInField();

            if (validationService.checkConstraint(readerUpdate)) {
                readerService.updateReader(readerUpdate);
                model.setListReaders(readerService.getAllReader());
                readerPanel.loadAllDada();
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }
    private void handlerCreateReader() {
        ReaderDialog dialog = new ReaderDialog (null, true, "Thêm độc giả","create");

        dialog.getCreateButton().addActionListener(l -> {
            ReaderDTO reader = dialog.getObjectInField();
            if (validationService.checkConstraint(reader)) {
                readerService.insertReader(reader);
                model.setListReaders(readerService.getAllReader());
                readerPanel.loadAllDada();
                dialog.dispose();
            }

        });
        dialog.setVisible(true);

    }

    private void handlerDeleteReader(ReaderDTO readerDTO) {
        int input = JOptionPane.showConfirmDialog(null, "Xóa độc giả", "Xóa độc giả", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            try {
                readerService.removeReader(readerDTO);
                model.setListReaders(readerService.getAllReader());
                readerPanel.loadAllDada();
            } catch (Exception e)  {
                JOptionPane.showMessageDialog(
                        null,
                        "Không thể xóa độc giả đang mượn sách",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    public void handlerReloadPanel() {
        readerPanel.getBookInCategory().removeAll();
        readerPanel.getBookInCategory().repaint();
        readerPanel.getBookInCategory().validate();
        readerPanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setListReaders(readerService.getAllReader());
        readerPanel.loadAllDada();
    }

    private void handlerSearchForm() {
        String txt = readerPanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)readerPanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        Integer i = null;
        String name = null;
        if (searchOption.equals("Họ và tên")) {
            name = txt;
        } else if (searchOption.equals("Id")) {
            try {
                i = Integer.parseInt(txt);
            }catch (NumberFormatException e) {
                i = null;
            }
        }

        model.setListReaders(readerService.searchReader(i, name));
        readerPanel.loadAllDada();
    }

    private Integer getIdReaderOnRow () {
        int selectedRow = readerPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        Integer id = (Integer) readerPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return id;
    }

    private void handlerActionOnRow() {
        Integer id = getIdReaderOnRow();
        if (id != null) {
            model.setListBooks(bookService.getBookByReader(id));
            readerPanel.showListBookOfCategory();
        }
    }

    public void loadAllDataInReaderPanel() {
        handlerReloadPanel();
    }


}

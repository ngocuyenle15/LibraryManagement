package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.transactionmanager.TransactionManager;
import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.model.dto.ShelfDTO;
import com.ptit.librarymanagement.presentation.dialog.ShelfDialog;
import com.ptit.librarymanagement.presentation.contentview.BookShelvePanel;
import com.ptit.librarymanagement.service.ShelfService;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import jakarta.validation.Validation;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class BookShelveController {
    private BookShelvePanel bookShelvePanel;
    private BookShelvePanel.BookShelvePanelModel model;
    private ShelfService shelfService;
    private ValidationService validationService;

    
    public BookShelveController (BookShelvePanel bookShelvePanel) {
        this.bookShelvePanel = bookShelvePanel;
        this.model = bookShelvePanel.getModel();
        this.shelfService = TransactionManager.createService(ShelfService.class, DbConnection.getConnection());
        this.validationService = new ValidationService(Validation.buildDefaultValidatorFactory().getValidator());
        model.setListShelf(shelfService.getAllShelves());
        bookShelvePanel.loadDataInTable();
        initController();


    }
    public BookShelveController (BookShelvePanel bookShelvePanel, ServiceFactory serviceFactory) {
        this.bookShelvePanel = bookShelvePanel;
        this.model = bookShelvePanel.getModel();
        this.shelfService = serviceFactory.getShelfService();
        this.validationService = serviceFactory.getValidationService();
        model.setListShelf(shelfService.getAllShelves());
        bookShelvePanel.loadDataInTable();
        initController();
    }

    public void initController () {
        bookShelvePanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handlerSearchForm();
            }
        });
        bookShelvePanel.getIntegratedSearch().getBtnReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerReloadPanel();
            }
        });



        bookShelvePanel.getScrollTable().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerActionOnRow();
            }
        });

        bookShelvePanel.getFunctionToolBar().getButtons().get("create").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerCreateShelf();

            }
        });
        bookShelvePanel.getFunctionToolBar().getButtons().get("update").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<ShelfDTO> shelfSelectedRow = getShelfSelectedRow();
                if (shelfSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(bookShelvePanel, "Vui lòng chọn vị trí");
                }
                else {
                    handlerUpdateShelf(shelfSelectedRow.get());
                }
            }

        });
        bookShelvePanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<ShelfDTO> shelfSelectedRow = getShelfSelectedRow();
                if (shelfSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(bookShelvePanel, "Vui lòng chọn vị trí");
                }
                else {
                    handlerDeleteShelf(shelfSelectedRow.get());
                }
            }
        });



    }

    private Optional<ShelfDTO> getShelfSelectedRow() {
        int selectedRow = bookShelvePanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) bookShelvePanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(shelfService.getShelfById(id));
    }

    private Integer getShelfIdInRow() {
        int selectedRow = bookShelvePanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        Integer id = (Integer) bookShelvePanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return id;
    }


    private void handlerUpdateShelf(ShelfDTO shelfDTO) {
        ShelfDialog dialog = new ShelfDialog(new JFrame(), true, "Sửa thông tin vị trí để sách", "update");
        dialog.setDefaultTextInField(shelfDTO);
        dialog.getUpdateButton().addActionListener(l -> {
            ShelfDTO shelfUpdate = dialog.getObjectInField();
            if (validationService.checkConstraint(shelfUpdate)) {
                shelfService.updateShelfDetails(shelfUpdate);
                model.setListShelf(shelfService.getAllShelves());
                bookShelvePanel.loadDataInTable();
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    private void handlerDeleteShelf(ShelfDTO shelfDTO) {
        int input = JOptionPane.showConfirmDialog(null, "Xóa kệ sách", "Xóa kệ sách", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            try {
                shelfService.removeShelf(shelfDTO.getId());
                model.setListShelf(shelfService.getAllShelves());
                bookShelvePanel.loadDataInTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Không thể thực hiện thao tác",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

    }

    private void handlerCreateShelf() {
        ShelfDialog dialog = new ShelfDialog(new JFrame(), true, "Thêm vị trí để sách sách", "create");
        dialog.getCreateButton().addActionListener(l -> {
            ShelfDTO shelfDTO = dialog.getObjectInField();
            if (validationService.checkConstraint(shelfDTO)) {
                try {
                    shelfService.createShelf(shelfDTO);
                    model.setListShelf(shelfService.getAllShelves());
                    bookShelvePanel.loadDataInTable();
                    dialog.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Vị trí đã tồn tại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        dialog.setVisible(true);
    }

    private void handlerActionOnRow() {
        Integer id = getShelfIdInRow();
        if (id != null) {
            model.setListBooks(shelfService.getBookOfShelf(id));
            bookShelvePanel.showListBookOnShelf();
        }
    }

    public void handlerReloadPanel() {
        bookShelvePanel.getBookOnShelf().removeAll();
        bookShelvePanel.getBookOnShelf().repaint();
        bookShelvePanel.getBookOnShelf().validate();
        bookShelvePanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setListShelf(shelfService.getAllShelves());
        bookShelvePanel.loadDataInTable();
    }

    private void handlerSearchForm() {
        String txt = bookShelvePanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)bookShelvePanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        ShelfDTO shelfDTO = ShelfDTO.builder().build();
        switch (searchOption) {
            case "Id":
                try {
                    shelfDTO.setId(Integer.parseInt(txt));
                } catch (NumberFormatException e) {
                    shelfDTO.setId(null);
                }
                break;
            case "Tên kệ":
                shelfDTO.setShelfName(txt);
                break;
            case "Vị trí":
                shelfDTO.setLocation(txt);
            default:
        }

        model.setListShelf(shelfService.getShelfByIdOrNameOrLocation(shelfDTO));
        bookShelvePanel.loadDataInTable();
    }

    public void loadAllDataInShelfPanel() {
        handlerReloadPanel();
    }


}

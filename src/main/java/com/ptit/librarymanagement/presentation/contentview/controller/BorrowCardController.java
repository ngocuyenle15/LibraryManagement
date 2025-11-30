package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.transactionmanager.TransactionManager;
import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.dto.BorrowCardDTO;
import com.ptit.librarymanagement.dto.ReaderDTO;
import com.ptit.librarymanagement.presentation.dialog.BorrowCardDialog;
import com.ptit.librarymanagement.presentation.dialog.ReaderDialog;
import com.ptit.librarymanagement.presentation.contentview.BorrowCardPanel;
import com.ptit.librarymanagement.common.enums.BorrowState;
import com.ptit.librarymanagement.presentation.contentview.component.PunishDialog;
import com.ptit.librarymanagement.service.BookService;
import com.ptit.librarymanagement.service.BorrowCardService;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import com.ptit.librarymanagement.service.ReaderService;

import javax.swing.*;
import java.awt.event.*;
import java.util.Optional;

public class BorrowCardController {
    private BorrowCardPanel borrowCardPanel;
    private BorrowCardPanel.BorrowCardPanelModel model;
    private BorrowCardService borrowCardService;
    private ReaderService readerService;
    private BookService bookService;
    private ValidationService validationService;
    
    

    public BorrowCardController (BorrowCardPanel borrowCardPanel) {

        this.borrowCardPanel = borrowCardPanel;
        this.model = borrowCardPanel.getModel();
        this.borrowCardService = TransactionManager.createService(BorrowCardService.class, DbConnection.getConnection());
        model.setListBorrowCards(borrowCardService.getAllBorrowCard());
        borrowCardPanel.loadDataInTable();
        
    }
    public BorrowCardController (BorrowCardPanel borrowCardPanel, ServiceFactory serviceFactory) {
       this.validationService = serviceFactory.getValidationService();
        this.readerService = serviceFactory.getReaderService();
        this.bookService = serviceFactory.getBookService();
        this.borrowCardPanel = borrowCardPanel;
        this.model = borrowCardPanel.getModel();
        this.borrowCardService = serviceFactory.getBorrowCardService();
        model.setListBorrowCards(borrowCardService.getAllBorrowCard());
        borrowCardPanel.loadDataInTable();
        initController();

    }


    public void initController () {
        borrowCardPanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearchForm();
            }
        });
        borrowCardPanel.getIntegratedSearch().getBtnReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerReloadPanel();
            }
        });



        borrowCardPanel.getScrollTable().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleActionOnRow();
            }
        });

        borrowCardPanel.getFunctionToolBar().getButtons().get("create").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerCreateBorrowCard();

            }
        });
        borrowCardPanel.getFunctionToolBar().getButtons().get("update").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<BorrowCardDTO> borrowCardSelectedRow = getBorrowCardSelectedRow();
                if (borrowCardSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(borrowCardPanel, "Vui lòng chọn thẻ");
                } else {
                    handlerUpdateCategory(borrowCardSelectedRow.get());
                }

            }

        });
        borrowCardPanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<BorrowCardDTO> borrowCardSelectedRow = getBorrowCardSelectedRow();
                if (borrowCardSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(borrowCardPanel, "Vui lòng chọn thẻ");
                } else {
                    handlerDeleteBorrowCard(borrowCardSelectedRow.get());
                }
            }
        });

        borrowCardPanel.getFunctionToolBar().getButtons().get("punish").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Integer key = null;
                int selectedRow = borrowCardPanel.getScrollTable().getTable().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(borrowCardPanel, "Vui lòng chọn thẻ");
                    return;
                }
                key = (Integer) (borrowCardPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0));
                handlerPunish(borrowCardService.getBorrowCardById(key));
            }
        });

        borrowCardPanel.getFunctionToolBar().getButtons().get("returnBook").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Integer key = null;
                int selectedRow = borrowCardPanel.getScrollTable().getTable().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(borrowCardPanel, "Vui lòng chọn thẻ");
                    return;
                }
                key = (Integer) (borrowCardPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0));
                handlerReturnBook(borrowCardService.getBorrowCardById(key));
            }
        });





        borrowCardPanel.getSelectionView().getButton1().addActionListener(l -> selectViewHandler (BorrowState.BORROWING));
        borrowCardPanel.getSelectionView().getButton2().addActionListener(l -> selectViewHandler (BorrowState.OVERDUE));
        borrowCardPanel.getSelectionView().getButton3().addActionListener(l -> selectViewHandler (BorrowState.RETURNED));




    }

    private void handlerViewDetailBorrowCard  (BorrowCardDTO borrowCardDTO) {
        BorrowCardDialog dialog = new BorrowCardDialog(null, true, "Sửa thông tin thẻ mượn", "view");
        dialog.setDefaultTextInField(borrowCardDTO);
        dialog.getIdInput().setText(String.valueOf(borrowCardDTO.getId()));
        dialog.getStateInput().setText(String.valueOf(borrowCardDTO.getState()));
        dialog.getPunishInput().setText(borrowCardDTO.getPunishment());

        dialog.setVisible(true);
    }

    private void handlerReturnBook (BorrowCardDTO borrowCardDTO) {
        int input = JOptionPane.showConfirmDialog(null, "Xác nhận trả sách", "Trả sách ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            borrowCardService.returnBooks(borrowCardDTO);
            model.setListBorrowCards(borrowCardService.getAllBorrowCard());
            borrowCardPanel.loadDataInTable();
        }
    }


    private boolean checkValidPunish (String input) {
        String regex = ".*[a-zA-Z]+.*";
        if (input == null) return false;
        String trimmed = input.trim();
        if (trimmed.isEmpty()) return false;
        return trimmed.matches(regex);
    }

    private void handlerPunish(BorrowCardDTO borrowCardDTO) {
        if (borrowCardDTO.getState().equals(BorrowState.OVERDUE.getState())) {
            PunishDialog punishDialog = new PunishDialog(null);
            punishDialog.setText(borrowCardDTO.getPunishment());
            punishDialog.getBtnSubmit().addActionListener(l -> {
                JTextArea textArea = punishDialog.getTextArea();
                String punishInfo = textArea.getText();
                if (punishInfo.isBlank()) {
                    JOptionPane.showMessageDialog(borrowCardPanel, "Vui lòng không để trống");
                } else {
                    borrowCardDTO.setPunishment(textArea.getText());
                    borrowCardService.update(borrowCardDTO);
                    punishDialog.dispose();
                }
            });
            punishDialog.setVisible(true);
        } else if (borrowCardDTO.getState().equals(BorrowState.BORROWING.getState())) {
            JOptionPane.showMessageDialog(borrowCardPanel, "Chưa quá hạn trả sách");
        }
        else if (borrowCardDTO.getState().equals(BorrowState.RETURNED.getState())) {
            JOptionPane.showMessageDialog(borrowCardPanel, "Đã trả");
        }


    }
    private void selectViewHandler (BorrowState borrowState) {
        model.setListBorrowCards(borrowCardService.getAllBorrowCardByState(borrowState));
        borrowCardPanel.loadDataInTable();
    }

    private void handlerUpdateCategory(BorrowCardDTO cardDTO) {
        BorrowCardDialog dialog = new BorrowCardDialog(null, true, "Sửa thông tin thẻ mượn", "update");
        dialog.getIdInput().setText(String.valueOf(cardDTO.getId()));
        dialog.getStateInput().setText(String.valueOf(cardDTO.getState()));
        dialog.setDefaultTextInField(cardDTO);
        dialog.setListForMultiSelectField(readerService.getAllReader(), bookService.getAllBook(false));
        dialog.getUpdateButton().addActionListener(l -> {
            BorrowCardDTO cardUpdate = dialog.getObjectInField();
            if (validationService.checkConstraint(cardUpdate)) {
                borrowCardService.update(cardUpdate);
                model.setListBorrowCards(borrowCardService.getAllBorrowCard());
                borrowCardPanel.loadDataInTable();
                dialog.dispose();
            }
        });

        dialog.setVisible(true);

    }

    private void handlerDeleteBorrowCard(BorrowCardDTO cardDTO) {
        if (cardDTO.getState().equals(BorrowState.RETURNED.getState())) {
            int input = JOptionPane.showConfirmDialog(null, "Xóa thẻ mượn", "Xóa thẻ mượn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                borrowCardService.deleteBorrowCard(cardDTO);
                model.setListBorrowCards(borrowCardService.getAllBorrowCard());
                borrowCardPanel.loadDataInTable();
            }
        }
        else {
            JOptionPane.showMessageDialog(borrowCardPanel, "Thẻ ở trạng thái đang mượn không thể xóa");
        }


    }

    private void handlerCreateBorrowCard () {
        BorrowCardDialog dialog = new BorrowCardDialog(null, true, "Tạo thẻ mượn", "create");
        dialog.getReaderInput().getField().getCreateButton().addActionListener(createReader(dialog));
        dialog.setListForMultiSelectField(readerService.getAllReader(), bookService.getBookCanBorrow());
        dialog.getCreateButton().addActionListener(l -> {
            BorrowCardDTO cardDTO = dialog.getObjectInField();
            cardDTO.setState(BorrowState.BORROWING.getState());
            if (validationService.checkConstraint(cardDTO)) {
                borrowCardService.createBorrowCard(cardDTO);
                model.setListBorrowCards(borrowCardService.getAllBorrowCard());
                borrowCardPanel.loadDataInTable();
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    private ActionListener createReader(BorrowCardDialog dialog) {
        return l -> {
            ReaderDialog readerDialog = new ReaderDialog (null, true, "Thêm độc giả","create");

            readerDialog.getCreateButton().addActionListener(e-> {
                ReaderDTO reader = readerDialog.getObjectInField();
                if (validationService.checkConstraint(reader)) {
                    readerService.insertReader(reader);
                    dialog.getReaderInput().setItems(readerService.getAllReader());
                    readerDialog.dispose();
                }

            });
            readerDialog.setVisible(true);
        };
    }

    private void handleActionOnRow() {
        int index = borrowCardPanel.getScrollTable().getTable().getSelectedRow();
        final Integer key = (Integer) borrowCardPanel.getScrollTable().getTableModel().getValueAt(index, 0);
        if (index != -1) {
            BorrowCardDTO borrowCard = borrowCardService.getBorrowCardById(key);
            model.setListBooks(borrowCardService.getBookOfBorrowCard(borrowCard));
            borrowCardPanel.showListBookOfBorrowCard(borrowCard);
        }
    }

    private void handlerReloadPanel() {
        borrowCardPanel.getBookInBorrowCard().removeAll();
        borrowCardPanel.getBookInBorrowCard().repaint();
        borrowCardPanel.getBookInBorrowCard().validate();
        borrowCardPanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setListBorrowCards(borrowCardService.getAllBorrowCard());
        borrowCardPanel.loadDataInTable();

    }

    private void handleSearchForm() {
        String txt = borrowCardPanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)borrowCardPanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        Integer i = null;
        String name = null;
        if (searchOption.equals("Tên độc giả")) {
            name = txt;
        } else if (searchOption.equals("Id")) {
            try {
                i = Integer.parseInt(txt);
            }catch (NumberFormatException e) {
                i = null;
            }
        }

        model.setListBorrowCards(borrowCardService.getBorrowCardByIdOrNameReader(i, name));
        borrowCardPanel.loadDataInTable();

    }

    public void loadAllDataInBorrowCardPanel() {
        handlerReloadPanel();
    }
    private Integer getBorrowCardIOnRow() {
        int selectedRow = borrowCardPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        Integer id = (Integer) borrowCardPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return id;
    }

    private Optional<BorrowCardDTO> getBorrowCardSelectedRow() {
        int selectedRow = borrowCardPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) borrowCardPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(borrowCardService.getBorrowCardById(id));
    }






}

package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.transactionmanager.TransactionManager;
import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.model.dto.AuthorDTO;
import com.ptit.librarymanagement.presentation.dialog.AuthorDialog;
import com.ptit.librarymanagement.presentation.contentview.AuthorPanel;
import com.ptit.librarymanagement.service.AuthorService;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import jakarta.validation.Validation;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class AuthorController {
    private AuthorPanel authorPanel;
    private AuthorPanel.AuthorPanelModel model;
    private AuthorService authorService;
    private ValidationService validationService;



    public AuthorController (AuthorPanel authorPanel) {
        this.authorPanel = authorPanel;
        this.model = authorPanel.getModel();
        authorService = TransactionManager.createService(AuthorService.class, DbConnection.getConnection());
        this.validationService = new ValidationService(Validation.buildDefaultValidatorFactory().getValidator());
        model.setListAuthors(authorService.getAllAuthor());
        authorPanel.loadDataInTable();
        initController();
    }
    public AuthorController (AuthorPanel authorPanel, ServiceFactory serviceFactory) {
        this.authorPanel = authorPanel;
        this.model = authorPanel.getModel();
        authorService = serviceFactory.getAuthorService();
        model.setListAuthors(authorService.getAllAuthor());
        this.validationService = serviceFactory.getValidationService();
        authorPanel.loadDataInTable();
        initController();
    }
    public void initController () {
        authorPanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearchForm();
            }
        });
        authorPanel.getIntegratedSearch().getBtnReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerReloadPanel();
            }
        });
        authorPanel.getScrollTable().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleActionOnRow();
            }
        });

        authorPanel.getFunctionToolBar().getButtons().get("create").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerCreateAuthor();

            }
        });
        authorPanel.getFunctionToolBar().getButtons().get("update").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<AuthorDTO> authorSelectedRow = getAuthorSelectedRow();
                if (authorSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(authorPanel, "Vui lòng lựa chọn tác giả");
                }
                else {
                    handlerUpdateAuthor(authorSelectedRow.get());
                }
            }

        });
        authorPanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<AuthorDTO> authorSelectedRow = getAuthorSelectedRow();
                if (authorSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(authorPanel, "Vui lòng lựa chọn tác giả");
                }
                else {
                    handlerDeleteAuthor(authorSelectedRow.get());
                }
            }
        });



    }

    private Optional<AuthorDTO> getAuthorSelectedRow() {
        int selectedRow = authorPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) authorPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(authorService.getAuthorById(id));
    }

    private void handlerUpdateAuthor(AuthorDTO authorDTO) {
        AuthorDialog dialog =  new AuthorDialog(new JFrame(), true, "Cập nhập thông tin của tác giả", "update");
        dialog.setDefaultTextInField(authorDTO);
        dialog.getIdInput().setText(Integer.toString(authorDTO.getId()));
        dialog.getUpdateButton().addActionListener(l -> {

            AuthorDTO authorUpdate = dialog.getObjectInField();
            if (validationService.checkConstraint(authorUpdate)) {
                // todo nên bọc thêm try catch
                authorService.updateAuthor(authorUpdate);
                model.setListAuthors(authorService.getAllAuthor());
                authorPanel.loadDataInTable();
                dialog.dispose();
            }
        });
        dialog.setVisible(true);

    }

    private void handlerDeleteAuthor(AuthorDTO authorById) {
        int input = JOptionPane.showConfirmDialog(null, "Xóa tác giả", "Xóa tác giả", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            try {
                authorService.deleteAuthor(authorById);
                model.setListAuthors(authorService.getAllAuthor());
                authorPanel.loadDataInTable();
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

    private void handlerCreateAuthor() {
        AuthorDialog dialog = new AuthorDialog(new JFrame(), true, "Thêm tác giả", "create");
        dialog.getCreateButton().addActionListener(l -> {
            AuthorDTO author = dialog.getObjectInField();
            if (validationService.checkConstraint(author)) {
                // todo nên bọc thêm try catch
                authorService.insertAuthor(author);
                model.setListAuthors(authorService.getAllAuthor());
                authorPanel.loadDataInTable();
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    private void handlerCreatePublisher() {
        AuthorDialog dialog = new AuthorDialog(new JFrame(), true, "Thêm tác giả", "create");
        dialog.getCreateButton().addActionListener(l -> {
            AuthorDTO author = dialog.getObjectInField();
            if (validationService.checkConstraint(author)) {
                // todo nên bọc thêm try catch
                authorService.insertAuthor(author);
                model.setListAuthors(authorService.getAllAuthor());
                authorPanel.loadDataInTable();
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    private Integer getAuthorIdInRow() {
        int selectedRow = authorPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        Integer id = (Integer) authorPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return id;
    }

    private void handleActionOnRow() {
        Integer id = getAuthorIdInRow();
        if (id != null) {
            model.setBookDTOS(authorService.getBookOfAuthor(id));
            authorPanel.showListBookOfAuthor();
        }
    }

    public void handlerReloadPanel() {
        authorPanel.getBookOfAuthor().removeAll();
        authorPanel.getBookOfAuthor().repaint();
        authorPanel.getBookOfAuthor().validate();
        authorPanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setListAuthors(authorService.getAllAuthor());
        authorPanel.loadDataInTable();

    }

    private void handleSearchForm() {
        String txt = authorPanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)authorPanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        Integer id = null;
        String name = null;
        if (searchOption.equals("Id"))
            id = txt.matches("^\\d+$") ? Integer.parseInt(txt) : null;
        else if (searchOption.equals("Tên tác giả"))
            name = txt;
        model.setListAuthors(authorService.getAuthorByIdOrName(id, name));
        authorPanel.loadDataInTable();

    }

    public void loadAllDataInAuthorPanel() {
        handlerReloadPanel();
    }



}

package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.transactionmanager.TransactionManager;
import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.common.validation.ValidatorManager;
import com.ptit.librarymanagement.dto.CategoryDTO;
import com.ptit.librarymanagement.presentation.dialog.CategoryDialog;
import com.ptit.librarymanagement.presentation.dialog.CategoryDialogOld;
import com.ptit.librarymanagement.presentation.contentview.CategoryPanel;
import com.ptit.librarymanagement.service.CategoryService;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import jakarta.validation.Validator;

import javax.swing.*;
import java.awt.event.*;
import java.util.Optional;
import java.util.function.Function;

public class CategoryController {
    private CategoryPanel categoryPanel;
    private CategoryPanel.CategoryPanelModel model;
    private CategoryService categoryService;
    private Validator validator;
    private ValidationService validationService;

    private CategoryDialogOld createDialog = new CategoryDialogOld(null, "Thêm danh mục", true, "create");
    private CategoryDialogOld updateDialog = new CategoryDialogOld(null, "Sửa danh mục", true, "update");
    public CategoryController (CategoryPanel categoryPanel) {
        this.validator = ValidatorManager.getValidator();
        this.categoryPanel = categoryPanel;
        this.categoryService = TransactionManager.createService(CategoryService.class, DbConnection.getConnection());
        this.model = categoryPanel.getModel();
        this.model.setListCategory(categoryService.getAllCategory());
        categoryPanel.loadDataInTable();
        initController();
    }
    public CategoryController (CategoryPanel categoryPanel, ServiceFactory serviceFactory) {
        this.validator = ValidatorManager.getValidator();
        this.categoryPanel = categoryPanel;
        this.categoryService = serviceFactory.getCategoryService();
        this.model = categoryPanel.getModel();
        this.model.setListCategory(categoryService.getAllCategory());
        this.validationService = serviceFactory.getValidationService();
        categoryPanel.loadDataInTable();
        initController();
    }


    public void initController () {
        categoryPanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearchForm();
            }
        });
        categoryPanel.getIntegratedSearch().getBtnReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerReloadPanel();
            }
        });



        categoryPanel.getScrollTable().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleActionOnRow();
            }
        });

        categoryPanel.getFunctionToolBar().getButtons().get("create").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerCreateCategory();

            }
        });
        categoryPanel.getFunctionToolBar().getButtons().get("update").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<CategoryDTO> categorySelectedRow = getCategorySelectedRow();
                if (categorySelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(categoryPanel, "Vui lòng lựa chọn danh mục");
                }
                else {
                    handlerUpdateCategory(categorySelectedRow.get());
                }
            }

        });
        categoryPanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<CategoryDTO> categorySelectedRow = getCategorySelectedRow();
                if (categorySelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(categoryPanel, "Vui lòng lựa chọn danh mục");
                }
                else {
                    handlerDeleteCategory(categorySelectedRow.get());
                }
            }
        });



    }

    private void handlerReloadPanel() {
        categoryPanel.getBookInCategory().removeAll();
        categoryPanel.getBookInCategory().repaint();
        categoryPanel.getBookInCategory().validate();
        categoryPanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setListCategory(categoryService.getAllCategory());
        categoryPanel.loadDataInTable();
    }

    private void handleSearchForm() {
        String txt = categoryPanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)categoryPanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        CategoryDTO category = CategoryDTO.builder()
                .id(((Function<String, Integer>) o -> {
                    Integer i;
                    try {
                        i = Integer.parseInt(o);
                    } catch (NumberFormatException numberFormatException) {
                        i = null;
                    }
                    return i;
                }).apply(txt))
                .name(searchOption.equals("Tên danh mục") ? txt : null)
                .build();
        model.setListCategory(categoryService.getCategoryByIdOrName(category));
        categoryPanel.loadDataInTable();
    }

    private void handlerDeleteCategory (CategoryDTO categoryDTO) {
            int input = JOptionPane.showConfirmDialog(null, "Xóa danh mục", "Xóa danh mục", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                try {
                    categoryService.deleteCategory(categoryDTO);
                    model.setListCategory(categoryService.getAllCategory());
                    categoryPanel.loadDataInTable();
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

    private void handlerCreateCategory () {
        CategoryDialog dialog = new CategoryDialog(null, true, "Thêm danh mục", "create");
        dialog.getCreateButton().addActionListener(l -> {
            CategoryDTO category = dialog.getObjectInField();
            if (validationService.checkConstraint(category)) {
                // todo: bắt thêm ngoại lệ trùng category_name
                try {
                    categoryService.insertCategory(category);
                    model.setListCategory(categoryService.getAllCategory());
                    categoryPanel.loadDataInTable();
                    dialog.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Danh mục đã tồn tại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        dialog.setVisible(true);




    }

    private void handlerUpdateCategory (CategoryDTO categoryDTO)  {
        CategoryDialog dialog = new CategoryDialog(null, true, "Sửa thông tin danh mục", "update");
        dialog.setDefaultTextInField(categoryDTO);
        dialog.getUpdateButton().addActionListener(l -> {
            CategoryDTO categoryUpdate = dialog.getObjectInField();
            if (validationService.checkConstraint(categoryUpdate)) {
                // todo: bắt thêm ngoại lệ trùng category_name
                try {
                    categoryService.updateCategory(categoryUpdate);
                    model.setListCategory(categoryService.getAllCategory());
                    categoryPanel.loadDataInTable();
                    dialog.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Danh mục đã tồn tại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        dialog.setVisible(true);



    }




    private void handleActionOnRow() {
        int index = categoryPanel.getScrollTable().getTable().getSelectedRow();
        final Integer key = (Integer) categoryPanel.getScrollTable().getTableModel().getValueAt(index, 0);
        if (index != -1) {
            CategoryDTO build = categoryService.getCategoryById(key);
            model.setListBooks(categoryService.getBookOfCategory(build));
            categoryPanel.showListBookOfCategory(build);
        }
    }

    public void loadAllDataInCategoryPanel() {
        handlerReloadPanel();
    }
    private Integer getCategoryIdInRow() {
        int selectedRow = categoryPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        Integer id = (Integer) categoryPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return id;
    }

    private Optional<CategoryDTO> getCategorySelectedRow() {
        int selectedRow = categoryPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) categoryPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(categoryService.getCategoryById(id));
    }





}

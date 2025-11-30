package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.validation.ValidationService;

import com.ptit.librarymanagement.common.validation.groupvalidation.ChangePassword;
import com.ptit.librarymanagement.common.validation.groupvalidation.CreateAccount;
import com.ptit.librarymanagement.common.validation.groupvalidation.UpdateAccount;
import com.ptit.librarymanagement.dto.AccountDTO;
import com.ptit.librarymanagement.dto.StaffDTO;
import com.ptit.librarymanagement.presentation.dialog.StaffDialog;
import com.ptit.librarymanagement.presentation.contentview.StaffPanel;
import com.ptit.librarymanagement.service.AccountService;
import com.ptit.librarymanagement.service.StaffService;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import jakarta.validation.groups.Default;

import javax.swing.*;
import java.awt.event.*;
import java.util.Optional;

public class StaffController {
    private final StaffPanel staffPanel;
    private final StaffService staffService;
    private final AccountService accountService;
    private StaffPanel.StaffPanelModel model;

    private ValidationService validationService;

    public StaffController (StaffPanel staffPanel, ServiceFactory serviceFactory) {
        this.staffPanel = staffPanel;
        this.accountService = serviceFactory.getAccountService();
        this.staffService = serviceFactory.getStaffService();
        this.model = staffPanel.getModel();
        this.validationService = serviceFactory.getValidationService();
        model.setListStaff(staffService.getAllStaff());
        staffPanel.loadDataInTable();
        initController();
    }




    private void initController () {
        this.staffPanel.getIntegratedSearch().getBtnReset().addActionListener((r) -> handlerReloadPanel());
        staffPanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handlerSearchForm ();
            }
        });
        this.staffPanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<StaffDTO> staffSelectedRow = getStaffSelectedRow();
                if (staffSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(staffPanel, "Vui lòng lựa chọn nhân viên");
                }
                else {
                    handlerDeleteStaff(staffSelectedRow.get());
                }
            }
        });

        this.staffPanel.getFunctionToolBar().getButtons().get("create").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
             handlerCreateStaff();
            }
        });
        this.staffPanel.getFunctionToolBar().getButtons().get("update").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<StaffDTO> staffSelectedRow = getStaffSelectedRow();
                if (staffSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(staffPanel, "Vui lòng lựa chọn nhân viên");
                }
                else {
                    handlerUpdateStaff(staffSelectedRow.get());
                }
            }
        });

        this.staffPanel.getFunctionToolBar().getButtons().get("changePassword").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<StaffDTO> staffSelectedRow = getStaffSelectedRow();
                if (staffSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(staffPanel, "Vui lòng lựa chọn nhân viên");
                }
                else {
                    handlerChangePassword (staffSelectedRow.get().getAccount());
                }

            }
        });

    }


    private void handlerChangePassword (AccountDTO accountDTO) {
        StaffDialog dialog = new StaffDialog(null, true, "Cấp lại mật khẩu", "changePassword");
        dialog.getConfirmButton().addActionListener(l -> {
            String password = dialog.getPasswordInput().getText();
            accountDTO.setPassword(password);
            if (validationService.checkConstraint(accountDTO, ChangePassword.class)) {
                // todo: chắc chắn lấy được staff và account mới vào được đến đây ko cần try catch
                accountService.resetPassword(accountDTO);
                dialog.dispose();
            }
        });
        dialog.setVisible(true);

    }



    private void handlerUpdateStaff(StaffDTO staffDTO) {
        StaffDialog dialog = new StaffDialog(null, true, "Sửa thông tin nhân viên", "update");
        dialog.setDefaultTextInField(staffDTO);
        dialog.getStaffIdInput().setText(String.valueOf(staffDTO.getId()));
        dialog.getAccountIdInput().setText(String.valueOf(staffDTO.getAccount().getId()));
        dialog.getUpdateButton().addActionListener (l -> {
            StaffDTO staffUpdate = dialog.getObjectInField();
            if (validationService.checkConstraint(staffUpdate, UpdateAccount.class)) {
                try {
                    staffService.updateStaffAndAccount(staffUpdate);
                    model.setListStaff(staffService.getAllStaff());
                    staffPanel.loadDataInTable();
                    dialog.dispose();
                } catch (Exception e) {
                    System.out.println(e.getCause().getMessage());
                }
            }
        });
        dialog.setVisible(true);



    }

    private void handlerCreateStaff() {
        StaffDialog dialog = new StaffDialog(null, true, "Thêm nhân viên", "create");
       dialog.getCreateButton().addActionListener(l -> {
           StaffDTO staff = dialog.getObjectInField();
           if (validationService.checkConstraint(staff, Default.class, CreateAccount.class)) {
               try {
                   staffService.CreateStaffAccount(staff);
                   model.setListStaff(staffService.getAllStaff());
                   staffPanel.loadDataInTable();
                   dialog.dispose();
               } catch (Exception e) {
                   System.out.println(e);
               }
           }
       });
       dialog.setVisible(true);



    }
    private void handlerDeleteStaff(StaffDTO staffDTO) {
        int input = JOptionPane.showConfirmDialog(null, "Xóa nhân viên", "Xóa nhân viên", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            staffService.deleteStaffAccount(staffDTO);
            model.setListStaff(staffService.getAllStaff());
            this.staffPanel.loadDataInTable();
        }

    }
    private void handlerReloadPanel() {
        this.staffPanel.getIntegratedSearch().getCbxChoose().setSelectedItem("Id");
        this.staffPanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setListStaff(staffService.getAllStaff());
        this.staffPanel.loadDataInTable();
    }


    private void handlerSearchForm () {
        String txt = this.staffPanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)this.staffPanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        String staffName = null;
        Integer id = null;
        String userName = staffName;
        if (searchOption.equals("Id")) {
            try {
                id = Integer.parseInt(txt);
            } catch (NumberFormatException e) {
                id = null;
            }
        }
        else if (searchOption.equals("Họ và tên")) {
            staffName = txt;
        }
        else if (searchOption.equals("Username")) {
            userName = txt;
        }

        model.setListStaff(staffService.search(id, staffName, userName));
        staffPanel.loadDataInTable();
    }

    private Optional<StaffDTO> getStaffSelectedRow() {
        int selectedRow = staffPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) staffPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(staffService.getStaffById(id));
    }

    private Integer getStaffIdInRow() {
        int selectedRow = staffPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        Integer id = (Integer) staffPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return id;
    }

    public void loadAllDataInStaffPanel() {
        handlerReloadPanel();
    }



}

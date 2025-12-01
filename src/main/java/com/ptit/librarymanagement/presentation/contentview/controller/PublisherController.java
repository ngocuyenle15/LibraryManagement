package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.model.dto.PublisherDTO;
import com.ptit.librarymanagement.presentation.dialog.PublisherDialog;
import com.ptit.librarymanagement.presentation.contentview.PublisherPanel;
import com.ptit.librarymanagement.service.PublisherService;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import jakarta.validation.Validation;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class PublisherController {
    private PublisherPanel publisherPanel;
    private PublisherPanel.PublisherPanelModel model;
    private PublisherService publisherService;

    private ValidationService validationService;

    public PublisherController (PublisherPanel publisherPanel) {

        this.publisherPanel = publisherPanel;
        this.model = publisherPanel.getModel();
        this.publisherService = new PublisherService(DbConnection.getConnection());
        model.setPublisherDTOS(publisherService.getAllPublisher());
        publisherPanel.loadDataInTable();
        this.validationService = new ValidationService(Validation.buildDefaultValidatorFactory().getValidator());
        initController();
    }

    public PublisherController (PublisherPanel publisherPanel, ServiceFactory serviceFactory) {
        this.publisherPanel = publisherPanel;
        this.model = publisherPanel.getModel();
        this.publisherService = serviceFactory.getPublisherService();
        this.validationService = serviceFactory.getValidationService();
        model.setPublisherDTOS(publisherService.getAllPublisher());
        publisherPanel.loadDataInTable();

        initController();
    }

    public void initController () {
        publisherPanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handlerSearchForm();
            }
        });
        publisherPanel.getIntegratedSearch().getBtnReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerReloadPanel();
            }
        });



        publisherPanel.getScrollTable().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleActionOnRow();
            }
        });

        publisherPanel.getFunctionToolBar().getButtons().get("create").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerCreatePublisher();

            }
        });
        publisherPanel.getFunctionToolBar().getButtons().get("update").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<PublisherDTO> publisherSelectedRow = getPublisherSelectedRow();
                if (publisherSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(publisherPanel, "Vui lòng lựa chọn nhà xuất bản");
                }
                else {
                    handlerUpdatePublisher(publisherSelectedRow.get());
                }
            }

        });
        publisherPanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<PublisherDTO> publisherSelectedRow = getPublisherSelectedRow();
                if (publisherSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(publisherPanel, "Vui lòng lựa chọn nhà xuất bản");
                }
                else {
                    handlerDeletePublisher(publisherSelectedRow.get());
                }
            }
        });



    }

    private void handlerUpdatePublisher(PublisherDTO authorDTO) {
        PublisherDialog dialog = new PublisherDialog(new JFrame(), true, "Sửa thông tin nhà xuất bản", "update");
        dialog.setDefaultTextInField(authorDTO);
        dialog.getUpdateButton().addActionListener(l -> {
            PublisherDTO publisherUpdate = dialog.getObjectInField();
            if (validationService.checkConstraint(publisherUpdate)) {
                // todo bọc thêm try catch
                publisherService.updatePublisher(publisherUpdate);
                model.setPublisherDTOS(publisherService.getAllPublisher());
                publisherPanel.loadDataInTable();
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    private Optional<PublisherDTO> getPublisherSelectedRow() {
        int selectedRow = publisherPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) publisherPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(publisherService.getPublisherById(id));
    }

    private Integer getPublisherIdInRow() {
        int selectedRow = publisherPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        Integer id = (Integer) publisherPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return id;
    }

    private void handlerDeletePublisher(PublisherDTO publisherDTO) {
        int input = JOptionPane.showConfirmDialog(null, "Xóa tác giả", "Xóa tác giả", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            try {
                publisherService.deletePublisher(publisherDTO);
                model.setPublisherDTOS(publisherService.getAllPublisher());
                publisherPanel.loadDataInTable();
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

    private void handlerCreatePublisher() {
        PublisherDialog dialog = new PublisherDialog(new JFrame(), true, "Thêm nhà xuất bản", "create");

        dialog.getCreateButton().addActionListener(e -> {
            PublisherDTO publisher = dialog.getObjectInField();
            if (validationService.checkConstraint(publisher)) {
                publisherService.insertPublisher(publisher);
                model.setPublisherDTOS(publisherService.getAllPublisher());
                publisherPanel.loadDataInTable();
            }

        });
        dialog.setVisible(true);
    }

    private void handleActionOnRow() {

        Integer id = getPublisherIdInRow();
        if (id != null) {
            model.setBookDTOS(publisherService.getBookOfPublisher(id));
            publisherPanel.showListBookOfPublisher();
        }


    }

    private void handlerReloadPanel() {
        publisherPanel.getBookInPublisher().removeAll();
        publisherPanel.getBookInPublisher().repaint();
        publisherPanel.getBookInPublisher().validate();
        publisherPanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setPublisherDTOS(publisherService.getAllPublisher());
        publisherPanel.loadDataInTable();
    }

    private void handlerSearchForm() {
        String txt = publisherPanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)publisherPanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        PublisherDTO publisherDTO = PublisherDTO.builder().build();
        switch (searchOption) {
            case "Id":
                try {
                    publisherDTO.setId(Integer.parseInt(txt));
                } catch (NumberFormatException numberFormatException) {
                    publisherDTO.setId(null);
                }
                break;
            case "Tên nhà xuất bản":
                publisherDTO.setName(txt);
                break;
            default:
                // todo
        }
        model.setPublisherDTOS(publisherService.getPublisherByIdOrName(publisherDTO));
        publisherPanel.loadDataInTable();



    }

    public void loadAllDataInPublisherPanel() {
        handlerReloadPanel();
    }








}

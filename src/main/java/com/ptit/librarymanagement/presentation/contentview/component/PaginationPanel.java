package com.ptit.librarymanagement.presentation.contentview.component;

import com.ptit.librarymanagement.common.paging.Pageable;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class PaginationPanel extends JPanel {

    private final Pageable pageable;
    private final JButton prevButton;
    private final JButton nextButton;
//    private final JLabel infoLabel;
    private PageChangeListener listener;

    public void down () {
        this.pageable.prevPage();
        notifyPageChange();
    }

    public void up () {
        this.pageable.nextPage();
        notifyPageChange();
    }


    public PaginationPanel(int totalRecords, int pageSize) {
        this.setBackground(new Color(240, 247, 250));
        this.pageable = new Pageable();
        this.pageable.setCurrentPage(0);
        this.pageable.setTotalRecord(totalRecords);
        this.pageable.setPageSize(pageSize);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
//        infoLabel = new JLabel();

        this.add(prevButton);
        this.add(nextButton);
//        this.add(infoLabel);

//        updateInfoLabel();

    }

    public void addActionForPrevButton (ActionListener actionListener) {
        for (ActionListener ActionListener : prevButton.getActionListeners())
            prevButton.removeActionListener(ActionListener);
        prevButton.addActionListener(actionListener);

    }

    public void addActionForNextButton (ActionListener actionListener) {
        for (ActionListener ActionListener : nextButton.getActionListeners())
            nextButton.removeActionListener(ActionListener);
        nextButton.addActionListener(actionListener);

    }

//    public void updateInfoLabel() {
//        infoLabel.setText(String.format("Trang %d / %d, Tổng %d bản ghi",
//                pageable.getCurrenPage(), pageable.getTotalPage(), pageable.getTotalRecord()));
//    }

    public void notifyPageChange() {
        if (listener != null) {
            listener.onPageChanged(pageable.getCurrentPage());
        }
    }

    public void setPageChangeListener(PageChangeListener listener) {
        this.listener = listener;
    }

    public int getCurrentPage() {
        return pageable.getCurrentPage();
    }

    public interface PageChangeListener {
        void onPageChanged(int newPage);
    }

    public Pageable getPageable() {
        return pageable;
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public PageChangeListener getListener() {
        return listener;
    }
}

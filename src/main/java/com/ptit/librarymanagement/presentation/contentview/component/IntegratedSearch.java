package com.ptit.librarymanagement.presentation.contentview.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;



public class IntegratedSearch extends JPanel {

    private JComboBox<String> cbxChoose;
    private JButton btnReset;
    private JTextField txtSearchForm;
    private JPanel jpSearch;

//    public Object getTextInField () {
//        return txtSearchForm.getText();
//    }
//
//    public Object get () {
//
//    }

    public IntegratedSearch(String str[]) {
        initComponent(str);
        decorate();
    }

    private void decorate () {
        this.setBackground(Color.WHITE);

        jpSearch.setBorder(new EmptyBorder(18,15,18,15));
        jpSearch.setBackground(Color.white);


        cbxChoose.setPreferredSize(new Dimension(140, 0));
        cbxChoose.setFont(new Font(FlatRobotoFont.FAMILY, 0, 13));
        cbxChoose.setFocusable(false);


        txtSearchForm.setFont(new Font(FlatRobotoFont.FAMILY, 0, 13));
        txtSearchForm.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm...");
        txtSearchForm.putClientProperty("JTextField.showClearButton", true);
        jpSearch.add(txtSearchForm);

        btnReset.setFont(new Font(FlatRobotoFont.FAMILY, 0, 14));
        btnReset.setIcon(new FlatSVGIcon("icon/reload.svg"));
        btnReset.setPreferredSize(new Dimension(125, 0));
        btnReset.addActionListener(e -> {
            txtSearchForm.setText("");
            cbxChoose.setSelectedIndex(0);
        });
        jpSearch.add(btnReset,BorderLayout.EAST);
    }

    private void initComponent(String str[]) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.jpSearch = new JPanel(new BorderLayout(5,10));
        cbxChoose = new JComboBox();
        cbxChoose.setModel(new DefaultComboBoxModel<>(str));
        jpSearch.add(cbxChoose,BorderLayout.WEST);
        txtSearchForm = new JTextField();
        btnReset = new JButton("Làm mới");
        this.add(jpSearch);
    }

    public JComboBox<String> getCbxChoose() {
        return cbxChoose;
    }

    public void setCbxChoose(JComboBox<String> cbxChoose) {
        this.cbxChoose = cbxChoose;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public void setBtnReset(JButton btnReset) {
        this.btnReset = btnReset;
    }

    public JTextField getTxtSearchForm() {
        return txtSearchForm;
    }

    public void setTxtSearchForm(JTextField txtSearchForm) {
        this.txtSearchForm = txtSearchForm;
    }

    public JPanel getJpSearch() {
        return jpSearch;
    }

    public void setJpSearch(JPanel jpSearch) {
        this.jpSearch = jpSearch;
    }
}

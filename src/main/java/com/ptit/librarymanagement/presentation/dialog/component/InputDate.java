
package com.ptit.librarymanagement.presentation.dialog.component;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

public class InputDate extends JPanel {

    JLabel lbltitle;
    public JDateChooser date;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    private final SimpleDateFormat dateFormat;

    public InputDate(String title) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        lbltitle = new JLabel(title);
        date = new JDateChooser();
        date.setDateFormatString("dd/MM/yyyy");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.add(lbltitle);
        this.add(date);
    }

    public InputDate(String title, int w, int h) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(w, h));
        lbltitle = new JLabel(title);
        date = new JDateChooser();
        date.setDateFormatString("dd/MM/yyyy");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.add(lbltitle);
        this.add(date);
    }
    
    public JDateChooser getDateChooser() {
        return this.date;
    }

    public Date getDate(){
        Date dt = date.getDate();
        try {
            String txt_date = dateFormat.format(dt);
            if (!txt_date.matches("^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$"))
                throw new DateTimeException("Cannot format date");
            return dateFormat.parse(txt_date);
        } catch (Exception e) {
            return null;
        }

    }

    public void setDate(JDateChooser date) {
        this.date = date;
    }

    public void setDate(Date date) {
        this.date.setDate(date);
    }

    public void setDisable() {
        JTextFieldDateEditor editor = (JTextFieldDateEditor) date.getDateEditor();
        date.setEnabled(false);
        editor.setEditable(false);
    }
}

package com.ptit.librarymanagement.presentation.dialog;



import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.enums.BorrowState;
import com.ptit.librarymanagement.dto.BookDTO;
import com.ptit.librarymanagement.dto.BorrowCardDTO;
import com.ptit.librarymanagement.dto.ReaderDTO;
import com.ptit.librarymanagement.presentation.dialog.component.*;
import com.ptit.librarymanagement.service.BorrowCardService;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
public class BorrowCardDialog extends JDialog {

    private BorrowCardService borrowCardService = new BorrowCardService(DbConnection.getConnection());

    private JPanel mainPanel, bottomPanel;
    private ButtonCustom createButton, updateButton, cancelButton;
    private HeaderTitle titlePage;
    private InputDate startDateInput;
    private InputDate returnDateInput;
    private MultiSelectFieldPanel <ReaderDTO> readerInput;
    private MultiSelectFieldPanel <BookDTO> bookInput;
    private InputForm idInput;
    private InputForm stateInput;
    private InputForm punishInput;



    public void setListForMultiSelectField (List<ReaderDTO> listReaders, List<BookDTO> listBooks) {
        readerInput.setItems(listReaders);
        bookInput.setItems(listBooks);
    }
    public void setDefaultTextInField (BorrowCardDTO cardDTO) {
        idInput.setText(Integer.toString(cardDTO.getId()));
        startDateInput.setDate(cardDTO.getStartDate());
        returnDateInput.setDate(cardDTO.getReturnDate());
        readerInput.setSelectedItems(List.of(cardDTO.getReader()));
        bookInput.setSelectedItems(cardDTO.getBooks());
    }

    public BorrowCardDTO getObjectInField () {
        String id = idInput.getText();
        String state = stateInput.getText();
        String punish = punishInput.getText();
        Date startDate = startDateInput.getDate();
        Date returnDate = returnDateInput.getDate();
        Set<BookDTO> books = bookInput.getSelectedItems();
        ReaderDTO reader = readerInput.getSelectedItems().iterator().hasNext() ? readerInput.getSelectedItems().iterator().next() : null;
        return BorrowCardDTO.builder()
                            .id(id.isBlank() ? null : Integer.parseInt(id))
                            .startDate(startDate != null ? new java.sql.Date(startDate.getTime()) : null)
                            .returnDate(returnDate != null ? new java.sql.Date(returnDate.getTime()) : null)
                            .books(books.stream().toList())
                            .reader(reader)
                            .state(state)
                            .punishment(punish) // todo: check lại punish
                            .build();
    }






    public BorrowCardDialog(Frame owner, boolean modal, String title, String type) {
        super(owner, title, modal);



        init(title, type);
        this.setLocationRelativeTo(null);

    }

    public void init(String title, String type) {



        this.setSize(new Dimension(470, 450));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());
        idInput = new InputForm("Id");
        idInput.setText("");
        stateInput = new InputForm("State");
        stateInput.setText(BorrowState.BORROWING.getState());

        punishInput = new InputForm("punish");
        punishInput.setText("");


        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);



        JPanel genderPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        genderPanel.setBackground(Color.white);
        genderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblGender = new JLabel("Giới tính");
        JPanel genderBox = new JPanel(new GridLayout(1, 2));
        genderBox.setBackground(Color.white);


        genderPanel.add(lblGender);
        genderPanel.add(genderBox);

        readerInput = new MultiSelectFieldPanel<ReaderDTO>("Độc giả",new ArrayList<>(), o -> String.format("id: %d - Họ và tên: %s %s",o.getId() ,o.getFirstName(), o.getLastName()));
        readerInput.getField().setSingle(true);

        bookInput = new MultiSelectFieldPanel<BookDTO>("Sách",new ArrayList<>(), o -> String.format("id: %d - Tiêu đề: %s", o.getId(), o.getTitle()));


        startDateInput = new InputDate("Ngày mượn");
        returnDateInput = new InputDate("Ngày trả");


        mainPanel.add(readerInput);
        mainPanel.add(bookInput);

        mainPanel.add(startDateInput);
        mainPanel.add(returnDateInput);





        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        createButton = new ButtonCustom("Tạo thẻ mượn", "success", 14);
        updateButton = new ButtonCustom("Lưu thông tin", "success", 14);
        cancelButton = new ButtonCustom("Thoát", "danger", 14);

        cancelButton.addActionListener((ActionEvent e) -> dispose());

        switch (type) {
            case "create" -> bottomPanel.add(createButton);
            case "update" -> {
                bookInput.getField().removeTextFieldMouseListeners();
                readerInput.getField().removeTextFieldMouseListeners();

                startDateInput.setDisable();
                bottomPanel.add(updateButton);
            }
            case "view" -> {
                mainPanel.add(stateInput);
                mainPanel.add(punishInput);
                mainPanel.add(stateInput);

            }

        }

        bottomPanel.add(cancelButton);
        this.add(titlePage, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }




}
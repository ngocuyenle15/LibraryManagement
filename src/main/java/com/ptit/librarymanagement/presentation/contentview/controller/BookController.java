package com.ptit.librarymanagement.presentation.contentview.controller;

import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.model.dto.*;
import com.ptit.librarymanagement.presentation.dialog.CategoryDialog;
import com.ptit.librarymanagement.presentation.dialog.AuthorDialog;
import com.ptit.librarymanagement.presentation.dialog.BookDialog;
import com.ptit.librarymanagement.presentation.dialog.PublisherDialog;
import com.ptit.librarymanagement.presentation.dialog.ShelfDialog;
import com.ptit.librarymanagement.presentation.contentview.BookPanel;
import com.ptit.librarymanagement.presentation.contentview.component.PaginationPanel;
import com.ptit.librarymanagement.service.*;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import jakarta.validation.Validation;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.Optional;

public class BookController  {
    private final int  pageSize = 50;
    private final BookPanel bookPanel;
    private final BookPanel.BookPanelModel model;
    private final BookService bookService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final ShelfService shelfService;
    private final ValidationService validationService;

    public BookController(BookPanel bookPanel, BookPanel.BookPanelModel model, BookService bookService) {
        this.publisherService = new PublisherService(DbConnection.getConnection());
        this.categoryService = new CategoryService(DbConnection.getConnection());
        this.authorService = new AuthorService(DbConnection.getConnection());
        this.shelfService = new ShelfService(DbConnection.getConnection());
        this.validationService = new ValidationService(Validation.buildDefaultValidatorFactory().getValidator());
        this.bookService = bookService;
        this.bookPanel = bookPanel;
        this.model = this.bookPanel.getModel();
        this.model.setPageable(bookService.createPageableLoadAllBook(pageSize));
        this.model.setListBooks(bookService.loadAllBook(this.model.getPageable(), false));
        this.bookPanel.loadDataInTable();
        initController();
    }
    public BookController(BookPanel bookPanel, ServiceFactory serviceFactory) {
        this.publisherService = serviceFactory.getPublisherService();
        this.categoryService = serviceFactory.getCategoryService();
        this.authorService = serviceFactory.getAuthorService();
        this.shelfService = serviceFactory.getShelfService();
        this.bookService = serviceFactory.getBookService();
        this.validationService = serviceFactory.getValidationService();
        this.bookPanel = bookPanel;
        this.model = this.bookPanel.getModel();
        this.model.setPageable(bookService.createPageableLoadAllBook(pageSize));
        this.model.setListBooks(bookService.loadAllBook(this.model.getPageable(), false));
        this.bookPanel.loadDataInTable();
        initController();
    }

    public void loadAllDataInBookPanel () {
        handlerReloadPanel();
    }


    private void initController () {
        this.bookPanel.getIntegratedSearch().getTxtSearchForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearchForm();
            }
        });
        this.bookPanel.getIntegratedSearch().getBtnReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerReloadPanel();
            }
        });
        this.bookPanel.getFunctionToolBar().getButtons().get("delete").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<BookDTO> bookSelectedRow = getBookSelectedRow();
                if (bookSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(bookPanel, "Vui lòng lựa chọn sách");
                }
                else {
                    handlerDeleteBook(bookSelectedRow.get());
                }
            }
        });

        this.bookPanel.getFunctionToolBar().getButtons().get("create").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handlerCreateBook();
            }
        });

        this.bookPanel.getFunctionToolBar().getButtons().get("update").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<BookDTO> bookSelectedRow = getBookSelectedRow();
                if (bookSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(bookPanel, "Vui lòng lựa chọn sách");
                }
                else {
                    handlerUpdateBook(bookSelectedRow.get());
                }

            }
        });
        this.bookPanel.getFunctionToolBar().getButtons().get("view").addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Optional<BookDTO> bookSelectedRow = getBookSelectedRow();
                if (bookSelectedRow.isEmpty()) {
                    JOptionPane.showMessageDialog(bookPanel, "Vui lòng lựa chọn sách");
                }
                else {
                    handlerShowDetailBook(bookSelectedRow.get());
                }
            }
        });
        this.bookPanel.getPaginationPanel().addActionForPrevButton(this::handlePagingForCommon);
        this.bookPanel.getPaginationPanel().addActionForNextButton(this::handlePagingForCommon);


    }




    private void handlePagingForSearch (ActionEvent e, BookDTO book) {
        PaginationPanel paginationPanel = this.bookPanel.getPaginationPanel();
        if (e.getActionCommand().equals("Previous")) {
            model.getPageable().prevPage();
            model.setListBooks(bookService.searchBook(book,model.getPageable(), false));
            this.bookPanel.loadDataInTable();
            paginationPanel.notifyPageChange();
        }
        if (e.getActionCommand().equals("Next")) {
            model.getPageable().nextPage();
            List<BookDTO> listBooks = bookService.searchBook(book, model.getPageable(), false);
            model.setListBooks(listBooks);
            this.bookPanel.loadDataInTable();
            paginationPanel.notifyPageChange();
        }
    }

    private void handlePagingForCommon(ActionEvent e) {
        PaginationPanel paginationPanel = this.bookPanel.getPaginationPanel();
        if (e.getActionCommand().equals("Previous")) {
                model.getPageable().prevPage();
                model.setListBooks(bookService.loadAllBook(model.getPageable(), false));
                this.bookPanel.loadDataInTable();
                paginationPanel.notifyPageChange();
        }
        if (e.getActionCommand().equals("Next")) {
                model.getPageable().nextPage();
                model.setListBooks(bookService.loadAllBook(model.getPageable(), false));
                this.bookPanel.loadDataInTable();
                paginationPanel.notifyPageChange();
        }
    }

    private void handleSearchForm () {
        String txt = this.bookPanel.getIntegratedSearch().getTxtSearchForm().getText();
        String searchOption = (String)this.bookPanel.getIntegratedSearch().getCbxChoose().getSelectedItem();
        BookDTO book = BookDTO.builder()
                .id(searchOption.equals("Id") && txt.matches("^[1-9]\\d*$") ? Integer.parseInt(txt) : null)
                .title(searchOption.equals("Tiêu đề") ? txt : null).build();
        model.setPageable(bookService.createPageableSearchBook(pageSize, book, false));
        List<BookDTO> list = bookService.searchBook(book, model.getPageable(), false);
        model.setListBooks(list);
        bookPanel.getPaginationPanel().addActionForPrevButton (event  -> handlePagingForSearch(event, book));
        bookPanel.getPaginationPanel().addActionForNextButton(event  -> handlePagingForSearch(event, book));
        this.bookPanel.loadDataInTable();
    }


    // ---- ok

    private Optional<BookDTO> getBookSelectedRow () {
        int selectedRow = bookPanel.getScrollTable().getTable().getSelectedRow();
        if (selectedRow == -1) {
            return Optional.empty();
        }
        Integer id = (Integer) bookPanel.getScrollTable().getTableModel().getValueAt(selectedRow, 0);
        return Optional.of(bookService.getBookById(id));
    }

    private void handlerReloadPanel() {
        bookPanel.getIntegratedSearch().getCbxChoose().setSelectedItem("Id");
        bookPanel.getIntegratedSearch().getTxtSearchForm().setText("");
        model.setPageable(bookService.createPageableLoadAllBook(pageSize));
        model.setListBooks(bookService.loadAllBook(model.getPageable(), false));
        bookPanel.getPaginationPanel().addActionForNextButton(this::handlePagingForCommon);
        bookPanel.getPaginationPanel().addActionForPrevButton(this::handlePagingForCommon);
        this.bookPanel.loadDataInTable();
    }
    private void handlerDeleteBook(BookDTO bookDTO) {
        int input = JOptionPane.showConfirmDialog(null, "Di chuyển sách vào thùng rác", "Xóa sách", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            bookService.softDelete(bookDTO, true);
            model.setListBooks(bookService.loadAllBook(model.getPageable(), false));
            bookPanel.loadDataInTable();
        }
    }

    private void handlerCreateBook() {
        BookDialog dialog = new BookDialog(new JFrame(), "Thêm sách", true, "create");
        dialog.getAuthorInput().getField().getCreateButton().addActionListener(createAuthor(dialog));
        dialog.getPublisherInput().getField().getCreateButton().addActionListener(createPublisher(dialog));
        dialog.getShelfInput().getField().getCreateButton().addActionListener(createShelf(dialog));
        dialog.getCategoryInput().getField().getCreateButton().addActionListener(createCategory(dialog));
        dialog.setListForMultiSelectField(publisherService.getAllPublisher(),
                categoryService.getAllCategory(),
                authorService.getAllAuthor(),
                shelfService.getAllShelves());
        dialog.getCreateButton().addActionListener(actionEvent -> {
            BookDTO bookDTO = dialog.getObjectInField();

            if (validationService.checkConstraint(bookDTO)) {
                try {
                    bookService.insertBook(bookDTO);
                    model.setListBooks(bookService.loadAllBook(model.getPageable(), false));
                    bookPanel.loadDataInTable();
                    dialog.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        dialog.setVisible(true);
    }



    private void handlerUpdateBook(BookDTO bookDTO) {
        BookDialog dialog = new BookDialog(new JFrame(), "Sửa thông tin sách", true, "update");
        dialog.getAuthorInput().getField().getCreateButton().addActionListener(createAuthor(dialog));
        dialog.getPublisherInput().getField().getCreateButton().addActionListener(createPublisher(dialog));
        dialog.getShelfInput().getField().getCreateButton().addActionListener(createShelf(dialog));
        dialog.getCategoryInput().getField().getCreateButton().addActionListener(createCategory(dialog));
        dialog.setDefaultTextInField(bookDTO);
        dialog.setListForMultiSelectField(publisherService.getAllPublisher(),
                categoryService.getAllCategory(),
                authorService.getAllAuthor(),
                shelfService.getAllShelves());
        dialog.getSaveButton().addActionListener(actionEvent -> {
            BookDTO bookUpdate = dialog.getObjectInField();
            if (validationService.checkConstraint(bookUpdate)) {
                try {
                    bookService.updateBook(bookUpdate);
                    model.setListBooks(bookService.loadAllBook(model.getPageable(), false));
                    bookPanel.loadDataInTable();
                    dialog.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        dialog.setVisible(true);
    }

    private ActionListener createAuthor(BookDialog dialog) {
        return l -> {
            AuthorDialog authorDialog = new AuthorDialog(new JFrame(), true, "Thêm tác giả", "create");
            authorDialog.getCreateButton().addActionListener(ll -> {
                AuthorDTO author = authorDialog.getObjectInField();
                if (validationService.checkConstraint(author)) {
                    // todo nên bọc thêm try catch
                    authorService.insertAuthor(author);
                    dialog.getAuthorInput().setItems(authorService.getAllAuthor());
                    authorDialog.dispose();
                }
            });
            authorDialog.setVisible(true);
        };
    }

    private ActionListener createPublisher(BookDialog dialog) {
        return l -> {
            PublisherDialog publisherDialog = new PublisherDialog(new JFrame(), true, "Thêm nhà xuất bản", "create");
            publisherDialog.getCreateButton().addActionListener(e -> {
                PublisherDTO publisher = publisherDialog.getObjectInField();
                if (validationService.checkConstraint(publisher)) {
                    publisherService.insertPublisher(publisher);
                    dialog.getPublisherInput().setItems(publisherService.getAllPublisher());
                    publisherDialog.dispose();
                }
            });

            publisherDialog.setVisible(true);
        };
    }

    private ActionListener createShelf(BookDialog dialog) {
        return l -> {
            ShelfDialog shelfDialog = new ShelfDialog(new JFrame(), true, "Thêm vị trí để sách", "create");
            shelfDialog.getCreateButton().addActionListener(e -> {
                ShelfDTO shelfDTO = shelfDialog.getObjectInField();
                if (validationService.checkConstraint(shelfDTO)) {
                    shelfService.createShelf(shelfDTO);
                    dialog.getShelfInput().setItems(shelfService.getAllShelves());
                    shelfDialog.dispose();
                }
            });
            shelfDialog.setVisible(true);
        };
    }

    private ActionListener createCategory(BookDialog dialog) {
        return l -> {
            CategoryDialog categoryDialog = new CategoryDialog(null, true, "Thêm danh mục", "create");
            categoryDialog.getCreateButton().addActionListener(e -> {
                CategoryDTO category = categoryDialog.getObjectInField();
                if (validationService.checkConstraint(category)) {
                    // todo: bắt thêm ngoại lệ trùng category_name
                    try {
                        categoryService.insertCategory(category);
                        dialog.getCategoryInput().setItems(categoryService.getAllCategory());
                        categoryDialog.dispose();
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Danh mục đã tồn tại",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            });
            categoryDialog.setVisible(true);
        };
    }
    private void handlerShowDetailBook(BookDTO bookDTO) {
        BookDialog dialog = new BookDialog(new JFrame(), "Thông tin chi tiết của sách", true, "view");
        dialog.setListForMultiSelectField(publisherService.getAllPublisher(),
                categoryService.getAllCategory(),
                authorService.getAllAuthor(),
                shelfService.getAllShelves());
        dialog.setDefaultTextInField(bookDTO);
        dialog.setVisible(true);

    }

}

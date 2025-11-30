package com.ptit.librarymanagement.context.service;

import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.service.*;
import jakarta.validation.Validation;

import java.sql.Connection;

public class BaseServiceFactory implements ServiceFactory{
    private final BookService bookService;
    private final AccountService accountService;
    private final AuthorService authorService;
    private final BorrowCardService borrowCardService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;
    private final ReaderService readerService;
    private final ShelfService shelfService;
    private final StaffService staffService;
    private final ValidationService validationService;

    public BaseServiceFactory(Connection connection) {
        this.bookService = new BookService(connection);
        this.accountService = new AccountService(connection);
        this.authorService = new AuthorService(connection);
        this.borrowCardService = new BorrowCardService(connection);
        this.categoryService = new CategoryService(connection);
        this.publisherService = new PublisherService(connection);
        this.readerService = new ReaderService(connection);
        this.shelfService = new ShelfService(connection);
        this.staffService = new StaffService(connection);
        this.validationService = new ValidationService(Validation.buildDefaultValidatorFactory().getValidator());
    }


    @Override
    public BookService getBookService() {
        return bookService;
    }

    @Override
    public AccountService getAccountService() {
        return accountService;
    }

    @Override
    public AuthorService getAuthorService() {
        return authorService;
    }

    @Override
    public BorrowCardService getBorrowCardService() {
        return borrowCardService;
    }

    @Override
    public CategoryService getCategoryService() {
        return categoryService;
    }

    @Override
    public PublisherService getPublisherService() {
        return publisherService;
    }

    @Override
    public ReaderService getReaderService() {
        return readerService;
    }

    @Override
    public ShelfService getShelfService() {
        return shelfService;
    }

    @Override
    public StaffService getStaffService() {
        return staffService;
    }

    @Override
    public ValidationService getValidationService() {
        return this.validationService;
    }
}

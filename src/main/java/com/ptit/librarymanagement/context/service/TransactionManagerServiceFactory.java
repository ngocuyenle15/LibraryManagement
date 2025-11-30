package com.ptit.librarymanagement.context.service;

import com.ptit.librarymanagement.common.transactionmanager.TransactionManager;
import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.service.*;
import jakarta.validation.Validation;

import java.sql.Connection;

public class TransactionManagerServiceFactory implements ServiceFactory{
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


    public TransactionManagerServiceFactory(Connection connection) {
        this.bookService = TransactionManager.createService(BookService.class, connection);
        this.accountService =TransactionManager.createService(AccountService.class, connection);
        this.authorService = TransactionManager.createService(AuthorService.class, connection);
        this.borrowCardService = TransactionManager.createService(BorrowCardService.class, connection);
        this.categoryService = TransactionManager.createService(CategoryService.class, connection);
        this.publisherService = TransactionManager.createService(PublisherService.class, connection);
        this.readerService = TransactionManager.createService(ReaderService.class, connection);
        this.shelfService = TransactionManager.createService(ShelfService.class, connection);
        this.staffService = TransactionManager.createService(StaffService.class, connection);
        this.validationService = new ValidationService(Validation.buildDefaultValidatorFactory().getValidator());
    }

    public TransactionManagerServiceFactory(DAOFactory daoFactory) {
        this.bookService = TransactionManager.createService(BookService.class, daoFactory);
        this.accountService =TransactionManager.createService(AccountService.class, daoFactory);
        this.authorService = TransactionManager.createService(AuthorService.class, daoFactory);
        this.borrowCardService = TransactionManager.createService(BorrowCardService.class, daoFactory);
        this.categoryService = TransactionManager.createService(CategoryService.class, daoFactory);
        this.publisherService = TransactionManager.createService(PublisherService.class, daoFactory);
        this.readerService = TransactionManager.createService(ReaderService.class, daoFactory);
        this.shelfService = TransactionManager.createService(ShelfService.class, daoFactory);
        this.staffService = TransactionManager.createService(StaffService.class, daoFactory);
        this.validationService = new ValidationService(Validation.buildDefaultValidatorFactory().getValidator());
    }




    @Override
    public ValidationService getValidationService() {
        return validationService;
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
}

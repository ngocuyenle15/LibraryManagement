package com.ptit.librarymanagement.context.dao;

import com.ptit.librarymanagement.dao.*;

import java.sql.Connection;

public class BaseDAOFactory implements DAOFactory{
    private final Connection connection;
    private final AccountDAO accountDAO;
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;
    private final BorrowCardDAO borrowCardDAO;
    private final CategoryDAO categoryDAO;
    private final PublisherDAO publisherDAO;
    private final ReaderDAO readerDAO;
    private final ShelfDAO shelfDAO;
    private final StaffDAO staffDAO;

    public BaseDAOFactory(Connection connection) {
        this.connection = connection;
        this.accountDAO = new AccountDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
        this.bookDAO = new BookDAO(connection);
        this.borrowCardDAO = new BorrowCardDAO(connection);
        this.categoryDAO = new CategoryDAO(connection);
        this.publisherDAO = new PublisherDAO(connection);
        this.readerDAO = new ReaderDAO(connection);
        this.shelfDAO = new ShelfDAO(connection);
        this.staffDAO = new StaffDAO(connection);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    @Override
    public AuthorDAO getAuthorDAO() {
        return authorDAO;
    }



    @Override
    public BookDAO getBookDAO() {
        return bookDAO;
    }

    @Override
    public BorrowCardDAO getBorrowCardDAO() {
        return borrowCardDAO;
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    @Override
    public PublisherDAO getPublisherDAO() {
        return publisherDAO;
    }

    @Override
    public ReaderDAO getReaderDAO() {
        return readerDAO;
    }

    @Override
    public ShelfDAO getShelfDAO() {
        return shelfDAO;
    }

    @Override
    public StaffDAO getStaffDAO() {
        return staffDAO;
    }
}

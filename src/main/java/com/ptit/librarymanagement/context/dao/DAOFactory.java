package com.ptit.librarymanagement.context.dao;

import com.ptit.librarymanagement.dao.*;

import java.sql.Connection;

public interface DAOFactory {
    Connection getConnection ();
    AccountDAO getAccountDAO ();
    AuthorDAO getAuthorDAO ();
    BookDAO getBookDAO ();
    BorrowCardDAO getBorrowCardDAO ();
    CategoryDAO getCategoryDAO ();
    PublisherDAO getPublisherDAO ();
    ReaderDAO getReaderDAO ();
    ShelfDAO getShelfDAO ();
    StaffDAO getStaffDAO ();
}

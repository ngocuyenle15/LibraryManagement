package com.ptit.librarymanagement.context.controller;

import com.ptit.librarymanagement.presentation.contentview.controller.*;

public interface ControllerFactory {
    AuthorController getAuthorController ();
    BookController getBookController ();
    BookShelveController getBookShelveController ();
    BorrowCardController getBorrowCardController ();
    CategoryController getCategoryController ();
//    Home getHomePanel ();
    PublisherController getPublisherController ();
    ReaderController getReaderController ();
    RecycleBinController getRecycleBinController ();
    StaffController getStaffController ();

}

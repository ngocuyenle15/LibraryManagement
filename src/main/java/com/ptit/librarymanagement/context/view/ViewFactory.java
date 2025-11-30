package com.ptit.librarymanagement.context.view;

import com.ptit.librarymanagement.presentation.contentview.*;


public interface ViewFactory {
    AuthorPanel getAuthorPanel ();
    BookPanel getBookPanel ();
    BookShelvePanel getBookShelvePanel ();
    BorrowCardPanel getBorrowCardPanel ();
    CategoryPanel getCategoryPanel ();
    HomePanel getHomePanel ();
    PublisherPanel getPublisherPanel ();
    ReaderPanel getReaderPanel ();
    RecycleBinPanel getRecycleBinPanel ();
    StaffPanel getStaffPanel ();


}

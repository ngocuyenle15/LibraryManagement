package com.ptit.librarymanagement.context.service;

import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.service.*;

public interface ServiceFactory {
    BookService getBookService ();
    AccountService getAccountService();
    AuthorService getAuthorService();
    BorrowCardService getBorrowCardService ();
    CategoryService getCategoryService ();
    PublisherService getPublisherService ();
    ReaderService getReaderService ();
    ShelfService getShelfService ();
    StaffService getStaffService ();
    ValidationService getValidationService ();




}

package com.ptit.librarymanagement.context.controller;

import com.ptit.librarymanagement.context.service.ServiceFactory;
import com.ptit.librarymanagement.context.view.ViewFactory;
import com.ptit.librarymanagement.presentation.contentview.controller.*;


public class BaseController implements ControllerFactory {
    private final AuthorController authorController;
    private final BookController bookController;
    private final BookShelveController bookShelveController;
    private final BorrowCardController borrowCardController;
    private final CategoryController categoryController;
    private final ReaderController readerController;
    private final RecycleBinController recycleBinController;
    private final StaffController staffController;
    private final PublisherController publisherController;
    //    private //    Home getHomePanel ();

    public BaseController (ViewFactory viewFactory, ServiceFactory serviceFactory) {
        this.authorController = new AuthorController(viewFactory.getAuthorPanel(), serviceFactory);
        this.bookController = new BookController(viewFactory.getBookPanel(), serviceFactory);
        this.bookShelveController = new BookShelveController(viewFactory.getBookShelvePanel(), serviceFactory);
        this.borrowCardController = new BorrowCardController(viewFactory.getBorrowCardPanel(), serviceFactory);
        this.categoryController = new CategoryController(viewFactory.getCategoryPanel(), serviceFactory);
        this.publisherController = new PublisherController(viewFactory.getPublisherPanel(), serviceFactory);
        this.readerController = new ReaderController(viewFactory.getReaderPanel(), serviceFactory);
        this.recycleBinController = new RecycleBinController(viewFactory.getRecycleBinPanel(), serviceFactory);
        this.staffController = new StaffController(viewFactory.getStaffPanel(), serviceFactory);
    }


    @Override
    public AuthorController getAuthorController() {
        return authorController;
    }

    @Override
    public BookController getBookController() {
        return bookController;
    }

    @Override
    public BookShelveController getBookShelveController() {
        return bookShelveController;
    }

    @Override
    public BorrowCardController getBorrowCardController() {
        return borrowCardController;
    }

    @Override
    public CategoryController getCategoryController() {
        return categoryController;
    }

    @Override
    public ReaderController getReaderController() {
        return readerController;
    }

    @Override
    public RecycleBinController getRecycleBinController() {
        return recycleBinController;
    }

    @Override
    public StaffController getStaffController() {
        return staffController;
    }

    @Override
    public PublisherController getPublisherController() {
        return publisherController;
    }
}

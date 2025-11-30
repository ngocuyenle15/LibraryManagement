package com.ptit.librarymanagement.context.view;

import com.ptit.librarymanagement.presentation.contentview.*;

public class BaseView implements ViewFactory {
    private final AuthorPanel authorPanel;
    private final BookPanel bookPanel;
    private final BookShelvePanel bookShelvePanel;
    private final BorrowCardPanel borrowCardPanel;
    private final CategoryPanel categoryPanel;
    private final HomePanel homePanel;
    private final PublisherPanel publisherPanel;
    private final ReaderPanel readerPanel;
    private final RecycleBinPanel recycleBinPanel;
    private final StaffPanel staffPanel;
//    private LoginFrame loginFrame;

    public BaseView() {
        this.authorPanel = new AuthorPanel();
        this.bookPanel = new BookPanel();
        this.bookShelvePanel = new BookShelvePanel();
        this.borrowCardPanel = new BorrowCardPanel();
        this.categoryPanel = new CategoryPanel();
        this.homePanel = new HomePanel();
        this.publisherPanel = new PublisherPanel();
        this.readerPanel = new ReaderPanel();
        this.recycleBinPanel = new RecycleBinPanel();
        this.staffPanel = new StaffPanel();
//        this.loginFrame = new LoginFrame();
    }

    @Override
    public AuthorPanel getAuthorPanel() {
        return authorPanel;
    }

    @Override
    public BookPanel getBookPanel() {
        return bookPanel;
    }

    @Override
    public BookShelvePanel getBookShelvePanel() {
        return bookShelvePanel;
    }

    @Override
    public BorrowCardPanel getBorrowCardPanel() {
        return borrowCardPanel;
    }

    @Override
    public CategoryPanel getCategoryPanel() {
        return categoryPanel;
    }

    @Override
    public HomePanel getHomePanel() {
        return homePanel;
    }

    @Override
    public PublisherPanel getPublisherPanel() {
        return publisherPanel;
    }

    @Override
    public ReaderPanel getReaderPanel() {
        return readerPanel;
    }

    @Override
    public RecycleBinPanel getRecycleBinPanel() {
        return recycleBinPanel;
    }

    @Override
    public StaffPanel getStaffPanel() {
        return staffPanel;
    }

//    @Override
//    public LoginFrame getLoginFrame() {
//        return loginFrame;
//    }
}

package com.ptit.librarymanagement.presentation.mainview;



import com.ptit.librarymanagement.common.authentication.Session;
import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.common.enums.Role;
import com.ptit.librarymanagement.common.validation.ValidationService;
import com.ptit.librarymanagement.common.validation.ValidatorManager;
import com.ptit.librarymanagement.common.validation.groupvalidation.ChangePassword;
import com.ptit.librarymanagement.common.validation.groupvalidation.UpdateAccount;
import com.ptit.librarymanagement.context.BaseApplicationContext;
import com.ptit.librarymanagement.context.controller.ControllerFactory;
import com.ptit.librarymanagement.context.view.ViewFactory;
import com.ptit.librarymanagement.model.dto.AccountDTO;
import com.ptit.librarymanagement.presentation.contentview.*;
import com.ptit.librarymanagement.presentation.contentview.controller.BookController;
import com.ptit.librarymanagement.presentation.dialog.AccountDialog;
import com.ptit.librarymanagement.presentation.dialog.AccountPanel;
import com.ptit.librarymanagement.presentation.mainview.main.component.ContentPanel;
import com.ptit.librarymanagement.presentation.mainview.menu.ItemMenu;
import com.ptit.librarymanagement.presentation.mainview.menu.MenuPanel;
import com.ptit.librarymanagement.service.AccountService;



import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainFrame extends  JFrame {
    private HomePanel homePanel;
    private BookPanel bookPanel;
    private ReaderPanel readerPanel;
    private CategoryPanel categoryPanel;
    private AuthorPanel authorPanel;
    private PublisherPanel publisherPanel;
    private BookShelvePanel bookShelvePanel;
    private BorrowCardPanel borrowCardPanel;
    private RecycleBinPanel recycleBinPanel;
    private StaffPanel staffPanel;
    private ContentPanel contentPanel;
    private MenuPanel menuPanel;
    private BookController bookController;
    private ControllerFactory controllerFactory;
    private ViewFactory viewFactory;





    public MainFrame (ViewFactory viewFactory,ControllerFactory controllerFactory) {
        this.viewFactory = viewFactory;
        this.controllerFactory = controllerFactory;

        this.setSize(new Dimension(1400, 800));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle("Quản lý thư viện");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        init(viewFactory);
        contentPanel = new ContentPanel();
        addViewsForContentPanel(contentPanel);
        menuPanel = new MenuPanel();
        menuPanel.setPreferredSize(new Dimension(250, 1400));
        this.add(menuPanel, BorderLayout.WEST);

        this.addViewsForContentPanel(contentPanel);
        this.add(contentPanel, BorderLayout.CENTER);
        this.addWindowListener(closeJFrame());

        actionListenerMenu();


    }

    private WindowAdapter closeJFrame () {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DbConnection.closeConnection();
            }
        };
    }






    private void init (ViewFactory viewFactory) {
        authorPanel = viewFactory.getAuthorPanel();
        bookPanel = viewFactory.getBookPanel();
        bookShelvePanel = viewFactory.getBookShelvePanel();
        borrowCardPanel = viewFactory.getBorrowCardPanel();
        categoryPanel = viewFactory.getCategoryPanel();
        homePanel = viewFactory.getHomePanel();
        publisherPanel = viewFactory.getPublisherPanel();
        readerPanel = viewFactory.getReaderPanel();
        recycleBinPanel = viewFactory.getRecycleBinPanel();
        staffPanel = viewFactory.getStaffPanel();
    }

    private void actionListenerMenu() {
        ItemMenu[] listItem = menuPanel.getListItem();
        String[][] menuItemInformation = menuPanel.getMenuItemInformation();
        Color FontColor = new Color(96, 125, 139);
        Color DefaultColor = new Color(255, 255, 255);
        Color HowerFontColor = new Color(1, 87, 155);
        Color HowerBackgroundColor = new Color(187, 222, 251);
        listItem[0].setBackground(HowerBackgroundColor);
        listItem[0].setForeground(HowerFontColor);


        listItem[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MainFrame.this.controllerFactory.getBookController().loadAllDataInBookPanel();
                contentPanel.setPanelDisplay("homePanel");
            }
        });

        listItem[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MainFrame.this.controllerFactory.getBookController().loadAllDataInBookPanel();
                contentPanel.setPanelDisplay("bookPanel");
            }
        });

        listItem[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerFactory.getReaderController().loadAllDataInReaderPanel();
                contentPanel.setPanelDisplay(menuItemInformation[2][2]);
            }
        });

        listItem[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerFactory.getCategoryController().loadAllDataInCategoryPanel();
                contentPanel.setPanelDisplay(menuItemInformation[3][2]);
            }
        });

        listItem[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerFactory.getAuthorController().loadAllDataInAuthorPanel();
                contentPanel.setPanelDisplay(menuItemInformation[4][2]);
            }
        });

        listItem[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerFactory.getPublisherController().loadAllDataInPublisherPanel();
                contentPanel.setPanelDisplay(menuItemInformation[5][2]);
            }
        });

        listItem[6].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerFactory.getBookShelveController().loadAllDataInShelfPanel();
                contentPanel.setPanelDisplay(menuItemInformation[6][2]);
            }
        });

        listItem[7].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerFactory.getBorrowCardController().loadAllDataInBorrowCardPanel();
                contentPanel.setPanelDisplay(menuItemInformation[7][2]);
            }
        });

        listItem[8].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Session.getSession().getAccount().getRole() != Role.ADMIN) {
                    JOptionPane.showMessageDialog(bookPanel, "Bạn không có quyền truy cập trang này!");
                    return;
                }
                controllerFactory.getStaffController().loadAllDataInStaffPanel();
                contentPanel.setPanelDisplay(menuItemInformation[8][2]);
            }
        });

        listItem[9].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerFactory.getRecycleBinController().loadAllDataInBookRecycleBinPanel();
                contentPanel.setPanelDisplay(menuItemInformation[9][2]);
            }
        });

        listItem[10].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Đăng xuất",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    Session.getSession().setAccount(null);
                    MainFrame.this.dispose();
                }
                BaseApplicationContext baseApplicationContext = new BaseApplicationContext();
                baseApplicationContext.runApplication();
            }
        });

        menuPanel.getAccountInformationPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AccountPanel userNamePanel = new AccountPanel("Thay đổi tên đăng nhập", "changeUserName");
                AccountPanel passwordPanel = new AccountPanel("Thay đổi mật khẩu", "changePassword");

                AccountDialog dialog = new AccountDialog(MainFrame.this, userNamePanel, passwordPanel);

                userNamePanel.getCancelButton().addActionListener(l -> dialog.dispose());
                passwordPanel.getCancelButton().addActionListener(l -> dialog.dispose());

                AccountService accountService = new AccountService(DbConnection.getConnection());
                AccountDTO account = Session.getSession().getAccount();
                ValidationService validationService = new ValidationService(ValidatorManager.getValidator());
                userNamePanel.setDefaultTextInField(account);
                userNamePanel.getUpdateButton().addActionListener(listItem -> {
                    AccountDTO accountUpdate = userNamePanel.getObjectInField();
                    accountUpdate.setRole(account.getRole());
                    if (validationService.checkConstraint(accountUpdate, UpdateAccount.class)) {
                        try {// todo: Bọc thêm try catch check trùng name
                            accountService.updateAccount(accountUpdate);
                            MainFrame.this.getMenuTaskbar().getAccountInformationPanel().setUserNameAndRoleText(accountUpdate.getUserName(), accountUpdate.getRole().getRoleName());
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Đổi username thành công",
                                    "Lỗi",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            dialog.dispose();
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "UserName đã tồn tại trên hệ thống",
                                    "Message",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                });

                passwordPanel.getUpdateButton().addActionListener(listItem -> {

                    String oldPass = passwordPanel.getOldPasswordInput().getText();
                    String newPass = passwordPanel.getNewPasswordInput().getText();
                    account.setPassword(newPass);
                    if (validationService.checkConstraint(account, ChangePassword.class)) {
                        try {
                            accountService.changePassword(oldPass ,account);
                            MainFrame.this.getMenuTaskbar().getAccountInformationPanel().setUserNameAndRoleText(account.getUserName(), account.getRole().getRoleName());
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Đổi mật khẩu thành công",
                                    "Message",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            dialog.dispose();
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Sai mật khẩu cũ",
                                    "Lỗi",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }

                    }
                });

                dialog.setVisible(true);



            }
        });




    }



    private void addViewsForContentPanel(ContentPanel contentPanel) {
        contentPanel.add(homePanel, "homePanel");
        contentPanel.add(bookPanel, "bookPanel");
        contentPanel.add(readerPanel,"readerPanel");
        contentPanel.add(categoryPanel, "categoryPanel");
        contentPanel.add(authorPanel, "authorPanel");
        contentPanel.add(publisherPanel, "publisherPanel");
        contentPanel.add(bookShelvePanel,"bookShelvePanel");
        contentPanel.add(borrowCardPanel,"borrowSlipPanel");
        contentPanel.add(staffPanel, "staffPanel");
        contentPanel.add(recycleBinPanel, "recycleBinPanel");
        contentPanel.setPanelDisplay("homePanel");
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public void setHomePanel(HomePanel homePanel) {
        this.homePanel = homePanel;
    }

    public BookPanel getBookPanel() {
        return bookPanel;
    }

    public void setBookPanel(BookPanel bookPanel) {
        this.bookPanel = bookPanel;
    }

    public ReaderPanel getReaderPanel() {
        return readerPanel;
    }

    public void setReaderPanel(ReaderPanel readerPanel) {
        this.readerPanel = readerPanel;
    }

    public CategoryPanel getCategoryPanel() {
        return categoryPanel;
    }

    public void setCategoryPanel(CategoryPanel categoryPanel) {
        this.categoryPanel = categoryPanel;
    }

    public AuthorPanel getAuthorPanel() {
        return authorPanel;
    }

    public void setAuthorPanel(AuthorPanel authorPanel) {
        this.authorPanel = authorPanel;
    }

    public PublisherPanel getPublisherPanel() {
        return publisherPanel;
    }

    public void setPublisherPanel(PublisherPanel publisherPanel) {
        this.publisherPanel = publisherPanel;
    }

    public BookShelvePanel getBookShelvePanel() {
        return bookShelvePanel;
    }

    public void setBookShelvePanel(BookShelvePanel bookShelvePanel) {
        this.bookShelvePanel = bookShelvePanel;
    }

    public BorrowCardPanel getBorrowCardPanel() {
        return borrowCardPanel;
    }

    public void setBorrowCardPanel(BorrowCardPanel borrowCardPanel) {
        this.borrowCardPanel = borrowCardPanel;
    }

    public RecycleBinPanel getRecycleBinPanel() {
        return recycleBinPanel;
    }

    public void setRecycleBinPanel(RecycleBinPanel recycleBinPanel) {
        this.recycleBinPanel = recycleBinPanel;
    }

    public StaffPanel getStaffPanel() {
        return staffPanel;
    }

    public void setStaffPanel(StaffPanel staffPanel) {
        this.staffPanel = staffPanel;
    }

    public ContentPanel getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(ContentPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    public MenuPanel getMenuTaskbar() {
        return menuPanel;
    }

    public void setMenuTaskbar(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }

    public BookController getBookController() {
        return bookController;
    }

    public void setBookController(BookController bookController) {
        this.bookController = bookController;
    }

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public void setViewFactory(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }
}

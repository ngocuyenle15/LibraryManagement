package com.ptit.librarymanagement.context;

import com.ptit.librarymanagement.common.authentication.Session;
import com.ptit.librarymanagement.context.applicationcontext.ApplicationContext;
import com.ptit.librarymanagement.context.controller.ControllerFactory;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import com.ptit.librarymanagement.context.view.ViewFactory;
import com.ptit.librarymanagement.dto.AccountDTO;
import com.ptit.librarymanagement.presentation.loginview.LoginController;
import com.ptit.librarymanagement.presentation.loginview.LoginFrame;
import com.ptit.librarymanagement.presentation.mainview.MainFrame;

public class BaseApplication implements Application {
    private final ServiceFactory serviceFactory;
    private final DAOFactory daoFactory;
    private final ViewFactory viewFactory;
    private final ControllerFactory controllerFactory;
    private final Session session;

    public BaseApplication(ApplicationContext applicationContext) {
        this.serviceFactory = applicationContext.getServiceFactory();
        this.daoFactory = applicationContext.getDaoFactory();
        this.viewFactory = applicationContext.getViewFactory();
        this.controllerFactory = applicationContext.getControllerFactory();
        this.session = applicationContext.getSession();
    }

    @Override
    public void runApplication() {
        LoginFrame loginFrame = new LoginFrame();
        LoginController loginController = new LoginController(loginFrame, session, serviceFactory);
        loginController.setRunnable(() -> {
            MainFrame mainFrame = new MainFrame(BaseApplication.this.viewFactory, BaseApplication.this.controllerFactory);
            AccountDTO account = session.getAccount();
            mainFrame.getMenuTaskbar().getAccountInformationPanel().setUserNameAndRoleText(account.getUserName(), account.getRole().getRoleName());
//            serviceFactory.getBorrowCardService().loadStateAllBorrowCard();
            mainFrame.setVisible(true);
        });
        loginController.displayFormLogin();
    }


}

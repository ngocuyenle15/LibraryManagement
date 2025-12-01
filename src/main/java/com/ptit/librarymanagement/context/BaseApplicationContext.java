package com.ptit.librarymanagement.context;

import com.ptit.librarymanagement.common.authentication.Session;
import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.context.controller.BaseController;
import com.ptit.librarymanagement.context.controller.ControllerFactory;
import com.ptit.librarymanagement.context.dao.BaseDAOFactory;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import com.ptit.librarymanagement.context.service.TransactionManagerServiceFactory;
import com.ptit.librarymanagement.context.view.BaseView;
import com.ptit.librarymanagement.context.view.ViewFactory;
import com.ptit.librarymanagement.model.dto.AccountDTO;
import com.ptit.librarymanagement.presentation.loginview.LoginController;
import com.ptit.librarymanagement.presentation.loginview.LoginFrame;
import com.ptit.librarymanagement.presentation.mainview.MainFrame;

import java.sql.Connection;

public class BaseApplicationContext implements ApplicationContext {
    private final ServiceFactory serviceFactory;
    private final DAOFactory daoFactory;
    private final ViewFactory viewFactory;
    private final ControllerFactory controllerFactory;
    private final Session session;
    private final Connection connection;

    public BaseApplicationContext() {
        try {
            Class.forName("com.ptit.librarymanagement.common.system.EnvironmentVariableLoader");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.connection = DbConnection.getConnection();
        this.daoFactory = new BaseDAOFactory(connection);
        this.serviceFactory = new TransactionManagerServiceFactory(daoFactory);
        this.viewFactory = new BaseView();
        this.controllerFactory = new BaseController(viewFactory, serviceFactory);
        this.session = Session.getSession();
    }



    @Override
    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    @Override
    public DAOFactory getDaoFactory() {
        return daoFactory;
    }

    @Override
    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    @Override
    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void runApplication() {

        LoginFrame loginFrame = new LoginFrame();
        LoginController loginController = new LoginController(loginFrame, session, serviceFactory);
        loginController.setRunnable(() -> {
            MainFrame mainFrame = new MainFrame(this.viewFactory, this.controllerFactory);
            AccountDTO account = session.getAccount();
            mainFrame.getMenuTaskbar().getAccountInformationPanel().setUserNameAndRoleText(account.getUserName(), account.getRole().getRoleName());
            // todo: tự động cập nhật lại tất cả trạng thái của thẻ mượn
            serviceFactory.getBorrowCardService().loadStateAllBorrowCard();
            mainFrame.setVisible(true);
        });
        loginController.displayFormLogin();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}

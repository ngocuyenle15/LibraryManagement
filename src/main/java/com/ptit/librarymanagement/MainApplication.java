package com.ptit.librarymanagement;

import com.formdev.flatlaf.FlatLightLaf;
import com.ptit.librarymanagement.common.authentication.Session;
import com.ptit.librarymanagement.common.dbutils.DbConnection;
import com.ptit.librarymanagement.context.Application;
import com.ptit.librarymanagement.context.BaseApplication;
import com.ptit.librarymanagement.context.applicationcontext.ApplicationContext;
import com.ptit.librarymanagement.context.applicationcontext.ApplicationContextImpl;
import com.ptit.librarymanagement.context.controller.BaseController;
import com.ptit.librarymanagement.context.controller.ControllerFactory;
import com.ptit.librarymanagement.context.dao.BaseDAOFactory;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.context.service.BaseServiceFactory;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import com.ptit.librarymanagement.context.service.TransactionManagerServiceFactory;
import com.ptit.librarymanagement.context.view.BaseView;
import com.ptit.librarymanagement.context.view.ViewFactory;

import java.sql.Connection;

public class MainApplication {

    public static void main(String[] args) {
       runApp();
    }

    public static void runApp () {
        FlatLightLaf.setup();
        Connection connection = DbConnection.getConnection();
        DAOFactory daoFactory = new BaseDAOFactory(connection);
        ServiceFactory serviceFactory = new TransactionManagerServiceFactory(daoFactory);
        Session session = Session.getSession();
        ViewFactory viewFactory = new BaseView();
        ControllerFactory controllerFactory = new BaseController(viewFactory, serviceFactory);
        ApplicationContext applicationContext = new ApplicationContextImpl(serviceFactory, daoFactory, viewFactory ,controllerFactory, session);
        Application application = new BaseApplication(applicationContext);
        application.runApplication();
    }


}

package com.ptit.librarymanagement.context;

import com.ptit.librarymanagement.common.authentication.Session;
import com.ptit.librarymanagement.context.controller.ControllerFactory;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import com.ptit.librarymanagement.context.view.ViewFactory;

import java.sql.Connection;

public interface ApplicationContext {
    Connection getConnection ();
    ServiceFactory getServiceFactory ();
    DAOFactory getDaoFactory();
    ViewFactory getViewFactory();
    ControllerFactory getControllerFactory ();
    Session getSession ();
    void runApplication ();

}

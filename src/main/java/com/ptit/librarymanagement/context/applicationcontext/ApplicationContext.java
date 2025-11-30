package com.ptit.librarymanagement.context.applicationcontext;

import com.ptit.librarymanagement.common.authentication.Session;
import com.ptit.librarymanagement.context.controller.ControllerFactory;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import com.ptit.librarymanagement.context.view.ViewFactory;

public interface ApplicationContext {
    ServiceFactory getServiceFactory ();
    DAOFactory getDaoFactory();
    ViewFactory getViewFactory();
    ControllerFactory getControllerFactory ();
    Session getSession ();
}

package com.ptit.librarymanagement.context.applicationcontext;

import com.ptit.librarymanagement.context.applicationcontext.ApplicationContext;
import com.ptit.librarymanagement.common.authentication.Session;
import com.ptit.librarymanagement.context.controller.ControllerFactory;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.context.service.ServiceFactory;
import com.ptit.librarymanagement.context.view.ViewFactory;

public class ApplicationContextImpl implements ApplicationContext {
    private final ServiceFactory serviceFactory;
    private final DAOFactory daoFactory;
    private final ViewFactory viewFactory;
    private final ControllerFactory controllerFactory;
    private final Session session;

    public ApplicationContextImpl(ServiceFactory serviceFactory, DAOFactory daoFactory, ViewFactory viewFactory, ControllerFactory controllerFactory, Session session) {
        this.serviceFactory = serviceFactory;
        this.daoFactory = daoFactory;
        this.viewFactory = viewFactory;
        this.controllerFactory = controllerFactory;
        this.session = session;
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
}

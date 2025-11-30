package com.ptit.librarymanagement.common.transactionmanager;

import com.ptit.librarymanagement.context.dao.DAOFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;


public final class TransactionManager {
    // proxy pattern
    public static <T> T createProxy (final T realObject, final Connection connection) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(realObject);
        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
            Method method = invocation.getMethod();
            Transactional transactionalAnnotationMethod = method.getAnnotation(Transactional.class);
            Transactional transactionalAnnotationClass = realObject.getClass().getAnnotation(Transactional.class);
            if ((transactionalAnnotationMethod != null && transactionalAnnotationMethod.value()
                || (transactionalAnnotationClass != null && transactionalAnnotationClass.value()))) {
                connection.setAutoCommit(false);
                try {
                    Object retVal = invocation.proceed();
                    connection.commit();
                    return retVal;
                } catch (Exception e) {
                    System.out.println("Rollack transaction!");
                    connection.rollback();
                    throw e;
                }
            }
            return invocation.proceed();
        });
        return (T) proxyFactory.getProxy(realObject.getClass().getClassLoader());
    }


    public static <T> T createService(Class<T> serviceClass, Connection connection) {
        try {
            Constructor<T> constructor = serviceClass.getConstructor(Connection.class);
            T realService = constructor.newInstance(connection);
            return TransactionManager.createProxy(realService, connection);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create service proxy for " + serviceClass, e);
        }
    }

    public static <T> T createService(Class<T> serviceClass, DAOFactory daoFactory) {
        try {
            Constructor<T> constructor = serviceClass.getConstructor(DAOFactory.class);
            T realService = constructor.newInstance(daoFactory);
            return TransactionManager.createProxy(realService, daoFactory.getConnection());
        } catch (Exception e) {
            throw new RuntimeException("Cannot create service proxy for " + serviceClass, e);
        }
    }



}
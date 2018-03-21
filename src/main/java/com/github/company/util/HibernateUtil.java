package com.github.company.util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;
    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);

    static {
        try {
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            LOGGER.fatal(ex.toString());
            throw new ExceptionInInitializerError(ex);
        }
    }

    private HibernateUtil() {
    }

    public static Session getSession() throws HibernateException {
        return SESSION_FACTORY.openSession();
    }
}
package com.company.DAO.dao;

import com.company.util.HibernateUtil;
import org.hibernate.Session;

public interface GenericDao<T, E> {

    void create(T newInstance);

    T read(E id);

    default void update(T instance) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.update(instance);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    void delete(E id);
}

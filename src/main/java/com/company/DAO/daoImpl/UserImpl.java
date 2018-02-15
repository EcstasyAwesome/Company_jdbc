package com.company.DAO.daoImpl;

import com.company.DAO.dao.UserDao;
import com.company.DAO.entity.User;
import com.company.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserImpl implements UserDao {

    @Override
    public List<User> getAll() {
        List<User> result;
        try (Session session = HibernateUtil.getSession();) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root);
            Query<User> userQuery = session.createQuery(query);
            result = userQuery.getResultList();
        }
        return result;
    }

    @Override
    public void create(User newInstance) {
        Session session = HibernateUtil.getSession();
        String login = newInstance.getUserLogin();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("userLogin"), login));
            Query<User> userQuery = session.createQuery(query);
            if (!userQuery.list().isEmpty())
                throw new ConstraintViolationException("Логин '" + login + "' уже существует", null, login);
            session.beginTransaction();
            session.save(newInstance);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public User read(Integer id) {
        User user;
        try (Session session = HibernateUtil.getSession()) {
            user = session.get(User.class, id);
        }
        return user;
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            User user = new User(id);
            session.delete(user);
        }
    }
}

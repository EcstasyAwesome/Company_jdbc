package com.company.dao.impl;

import com.company.dao.model.UserDao;
import com.company.dao.entity.User;
import com.company.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserImpl implements UserDao {

    @Override
    public List<User> getAll() {
        List<User> result;
        try (Session session = HibernateUtil.getSession()) {
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
    public User getByLogin(String login) throws PersistenceException {
        User result;
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("login"), login));
            Query<User> userQuery = session.createQuery(query);
            result = userQuery.getSingleResult();
        } catch (PersistenceException e) {
            String noResult = "Пользователь %s не зарегистрирован";
            throw new PersistenceException(String.format(noResult, login));
        }
        return result;
    }

    @Override
    public void create(User newInstance) throws ConstraintViolationException {
        Session session = HibernateUtil.getSession();
        String login = newInstance.getLogin();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("login"), login));
            Query<User> userQuery = session.createQuery(query);
            if (!userQuery.list().isEmpty()) {
                String duplicate = "Логин %s уже существует";
                throw new ConstraintViolationException(String.format(duplicate, login), null, login);
            }
            session.beginTransaction();
            session.save(newInstance);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public User get(Integer id) {
        User user;
        try (Session session = HibernateUtil.getSession()) {
            user = session.get(User.class, id);
        }
        return user;
    }

    @Override
    public void update(User instance) {
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

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            User user = new User(id);
            session.delete(user);
        }
    }
}

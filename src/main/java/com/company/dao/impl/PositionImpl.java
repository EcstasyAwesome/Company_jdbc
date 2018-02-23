package com.company.dao.impl;

import com.company.dao.model.PositionDao;
import com.company.dao.entity.Position;
import com.company.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PositionImpl implements PositionDao {

    private final String DUPLICATE = "Должность '%s' уже существует";

    @Override
    public List<Position> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Position> query = builder.createQuery(Position.class);
            query.select(query.from(Position.class));
            Query<Position> positionQuery = session.createQuery(query);
            return positionQuery.getResultList();
        }
    }

    @Override
    public void create(Position newInstance) throws ConstraintViolationException {
        Session session = HibernateUtil.getSession();
        String name = newInstance.getName();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Position> query = builder.createQuery(Position.class);
            Root<Position> root = query.from(Position.class);
            query.select(root).where(builder.equal(root.get("name"), name));
            Query<Position> positionQuery = session.createQuery(query);
            if (!positionQuery.list().isEmpty())
                throw new ConstraintViolationException(String.format(DUPLICATE, name), null, name);
            session.beginTransaction();
            session.save(newInstance);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public Position get(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(Position.class, id);
        }
    }

    @Override
    public void update(Position instance) throws ConstraintViolationException {
        Session session = HibernateUtil.getSession();
        String name = instance.getName();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Position> query = builder.createQuery(Position.class);
            Root<Position> root = query.from(Position.class);
            query.select(root).where(builder.equal(root.get("name"), name));
            Query<Position> positionQuery = session.createQuery(query);
            if (!positionQuery.list().isEmpty()) {
                Position position = positionQuery.getSingleResult();
                if (position.getId() != instance.getId())
                    throw new ConstraintViolationException(String.format(DUPLICATE, name), null, name);
            }
            session.clear();
            session.beginTransaction();
            session.update(instance);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Position position = session.load(Position.class, id);
            session.delete(position);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public int countPages(int recordsOnPage) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            query.select(builder.count(query.from(Position.class)));
            Query<Long> longQuery = session.createQuery(query);
            long records = longQuery.getSingleResult();
            return (int) Math.ceil(records * 1.0 / recordsOnPage);
        }
    }

    @Override
    public List<Position> getPage(int page, int recordsOnPage) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Position> query = builder.createQuery(Position.class);
            query.select(query.from(Position.class));
            Query<Position> positionQuery = session.createQuery(query);
            positionQuery.setFirstResult(page * recordsOnPage - recordsOnPage);
            positionQuery.setMaxResults(recordsOnPage);
            return positionQuery.getResultList();
        }
    }
}
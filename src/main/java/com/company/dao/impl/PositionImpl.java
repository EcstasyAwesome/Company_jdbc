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

    private final String duplicate = "Должность %s уже существует";

    @Override
    public List<Position> getAll() {
        List<Position> result;
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Position> query = builder.createQuery(Position.class);
            Root<Position> root = query.from(Position.class);
            query.select(root);
            Query<Position> positionQuery = session.createQuery(query);
            result = positionQuery.getResultList();
        }
        return result;
    }

    @Override
    public void create(Position newInstance) throws ConstraintViolationException {
        Session session = HibernateUtil.getSession();
        String name = newInstance.getName();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Position> query = builder.createQuery(Position.class);
            Root<Position> root = query.from(Position.class);
            query.select(root).where(builder.equal(root.get("positionName"), name));
            Query<Position> positionQuery = session.createQuery(query);
            if (!positionQuery.list().isEmpty())
                throw new ConstraintViolationException(String.format(duplicate, name), null, name);
            session.beginTransaction();
            session.save(newInstance);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public Position read(Integer id) {
        Position position;
        try (Session session = HibernateUtil.getSession()) {
            position = session.get(Position.class, id);
        }
        return position;
    }

    @Override
    public void update(Position instance) throws ConstraintViolationException {
        Session session = HibernateUtil.getSession();
        String name = instance.getName();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Position> query = builder.createQuery(Position.class);
            Root<Position> root = query.from(Position.class);
            query.select(root).where(builder.equal(root.get("positionName"), name));
            Query<Position> positionQuery = session.createQuery(query);
            if (!positionQuery.list().isEmpty())
                throw new ConstraintViolationException(String.format(duplicate, name), null, name);
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
            Position position = new Position(id);
            session.delete(position);
        }
    }
}

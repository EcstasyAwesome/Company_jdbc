package com.company.dao.model;

public interface GenericDao<T, E> {

    void create(T newInstance);

    T read(E id);

    void update(T instance);

    void delete(E id);
}

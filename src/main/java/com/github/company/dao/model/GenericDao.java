package com.github.company.dao.model;

public interface GenericDao<T, E> {

    void create(T newInstance);

    T get(E id);

    void update(T instance);

    void delete(E id);
}

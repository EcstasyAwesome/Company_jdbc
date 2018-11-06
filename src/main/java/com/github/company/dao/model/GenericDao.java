package com.github.company.dao.model;

public interface GenericDao<T, E> {

    E create(T newInstance);

    T get(E id);

    E update(T instance);

    void delete(E id);
}
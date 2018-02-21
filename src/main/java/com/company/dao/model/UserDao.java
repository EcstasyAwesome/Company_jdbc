package com.company.dao.model;

import com.company.dao.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User, Long>, Pagination<User> {
    List<User> getAll();

    User getByLogin(String login);

    List<User> getByParam(String key, String value);
}
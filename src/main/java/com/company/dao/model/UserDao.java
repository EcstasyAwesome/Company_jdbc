package com.company.dao.model;

import com.company.dao.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User,Integer> {
    List<User> getAll();
    User getByLogin(String login);
}

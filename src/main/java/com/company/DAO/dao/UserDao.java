package com.company.DAO.dao;

import com.company.DAO.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User,Integer> {
    List<User> getAll();
}

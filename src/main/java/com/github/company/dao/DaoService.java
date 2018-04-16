package com.github.company.dao;

import com.github.company.dao.impl.GroupImpl;
import com.github.company.dao.model.GroupDao;
import com.github.company.dao.model.PositionDao;
import com.github.company.dao.model.UserDao;
import com.github.company.dao.impl.PositionImpl;
import com.github.company.dao.impl.UserImpl;

public class DaoService {

    private static DaoService instance = new DaoService();

    private DaoService() {
    }

    public static DaoService getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return UserImpl.getInstance();
    }

    public PositionDao getPositionDao() {
        return PositionImpl.getInstance();
    }

    public GroupDao getGroupDao() {
        return GroupImpl.getInstance();
    }
}
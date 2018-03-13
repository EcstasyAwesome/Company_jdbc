package com.company.dao;

import com.company.dao.impl.GroupImpl;
import com.company.dao.model.GroupDao;
import com.company.dao.model.PositionDao;
import com.company.dao.model.UserDao;
import com.company.dao.impl.PositionImpl;
import com.company.dao.impl.UserImpl;

public class DaoService {

    private static DaoService instance;

    private UserDao userDao = UserImpl.getInstance();
    private PositionDao positionDao = PositionImpl.getInstance();
    private GroupDao groupDao = GroupImpl.getInstance();

    private DaoService() {
    }

    public static DaoService getInstance() {
        if (instance == null) return instance = new DaoService();
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public PositionDao getPositionDao() {
        return positionDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }
}
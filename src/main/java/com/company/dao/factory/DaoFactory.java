package com.company.dao.factory;

import com.company.dao.model.PositionDao;
import com.company.dao.model.UserDao;
import com.company.dao.impl.PositionImpl;
import com.company.dao.impl.UserImpl;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (instance == null) return instance = new DaoFactory();
        return instance;
    }

    public UserDao getUserDao() {
        return new UserImpl();
    }

    public PositionDao getPositionDao() {
        return new PositionImpl();
    }
}

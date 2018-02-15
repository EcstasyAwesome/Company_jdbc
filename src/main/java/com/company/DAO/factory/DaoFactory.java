package com.company.DAO.factory;

import com.company.DAO.dao.PositionDao;
import com.company.DAO.dao.UserDao;
import com.company.DAO.daoImpl.PositionImpl;
import com.company.DAO.daoImpl.UserImpl;

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

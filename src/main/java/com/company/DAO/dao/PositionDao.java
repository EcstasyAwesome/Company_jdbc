package com.company.DAO.dao;

import com.company.DAO.entity.Position;

import java.util.List;

public interface PositionDao extends GenericDao<Position, Integer> {
    List<Position> getAll();
}

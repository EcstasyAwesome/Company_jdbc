package com.company.dao.model;

import com.company.dao.entity.Position;

import java.util.List;

public interface PositionDao extends GenericDao<Position, Integer> {
    List<Position> getAll();
}

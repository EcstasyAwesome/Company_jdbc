package com.github.company.dao.model;

import com.github.company.dao.entity.Position;

import java.util.List;

public interface PositionDao extends GenericDao<Position, Long>, Pagination<Position> {
    List<Position> getAll();
}
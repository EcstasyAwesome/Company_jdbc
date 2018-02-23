package com.company.dao.model;

import com.company.dao.entity.Group;

import java.util.List;

public interface GroupDao extends GenericDao<Group, Long> {
    List<Group> getAll();
}
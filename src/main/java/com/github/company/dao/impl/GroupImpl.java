package com.github.company.dao.impl;

import com.github.company.dao.entity.Group;
import com.github.company.dao.model.GroupDao;
import com.github.company.database.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupImpl implements GroupDao {

    private static GroupImpl instance = new GroupImpl();

    public static GroupImpl getInstance() {
        return instance;
    }

    private static final Logger LOGGER = Logger.getLogger(GroupImpl.class);

    private GroupImpl() {
    }

    private boolean isAvailable(Connection current, String name) throws SQLException {
        String request = "SELECT EXISTS(SELECT * FROM company.tbl_groups WHERE group_name=?)";
        try (PreparedStatement ps = current.prepareStatement(request)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && !rs.getBoolean(1);
            }
        }
    }

    private Group getFromResultSet(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getLong("group_id"));
        group.setName(resultSet.getString("group_name"));
        return group;
    }

    @Override
    public List<Group> getAll() {
        List<Group> result = new ArrayList<>();
        String request = "SELECT * FROM company.tbl_groups";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(request)) {
            while (rs.next()) {
                result.add(getFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
        return result;
    }

    @Override
    public Long create(Group newInstance) {
        String request = "INSERT INTO company.tbl_groups VALUES(NULL,?)";
        try (Connection connection = ConnectionPool.getConnection()) {
            if (isAvailable(connection, newInstance.getName())) {
                try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, newInstance.getName());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) return rs.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
        return 0L;
    }

    @Override
    public Group get(Long id) {
        String request = "SELECT * FROM company.tbl_groups WHERE group_id=?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(request)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return getFromResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    @Override
    public Long update(Group instance) {
        String request = "UPDATE company.tbl_groups SET group_name=? WHERE group_id=?";
        try (Connection connection = ConnectionPool.getConnection()) {
            if (isAvailable(connection, instance.getName())) {
                try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, instance.getName());
                    ps.setLong(2, instance.getId());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) return rs.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
        return 0L;
    }

    @Override
    public void delete(Long id) {
        String request = "DELETE FROM company.tbl_groups WHERE group_id=?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(request)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
    }
}
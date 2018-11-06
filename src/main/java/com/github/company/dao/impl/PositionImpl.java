package com.github.company.dao.impl;

import com.github.company.dao.model.PositionDao;
import com.github.company.dao.entity.Position;
import com.github.company.database.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionImpl implements PositionDao {

    private static PositionImpl instance = new PositionImpl();

    public static PositionImpl getInstance() {
        return instance;
    }

    private static final Logger LOGGER = Logger.getLogger(PositionImpl.class);

    private PositionImpl() {
    }

    private boolean isAvailable(Connection current, String name) throws SQLException {
        String request = "SELECT EXISTS(SELECT * FROM company.tbl_positions WHERE position_name=?)";
        try (PreparedStatement ps = current.prepareStatement(request)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && !rs.getBoolean(1);
            }
        }
    }

    private Position getFromResultSet(ResultSet resultSet) throws SQLException {
        Position position = new Position();
        position.setId(resultSet.getLong("position_id"));
        position.setName(resultSet.getString("position_name"));
        position.setDescription(resultSet.getString("position_description"));
        return position;
    }

    @Override
    public List<Position> getAll() {
        List<Position> result = new ArrayList<>();
        String request = "SELECT * FROM company.tbl_positions";
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
    public Long create(Position newInstance) {
        String request = "INSERT INTO company.tbl_positions VALUES(NULL,?,?)";
        try (Connection connection = ConnectionPool.getConnection()) {
            if (isAvailable(connection, newInstance.getName())) {
                try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, newInstance.getName());
                    ps.setString(2, newInstance.getDescription());
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
    public Position get(Long id) {
        String request = "SELECT * FROM company.tbl_positions WHERE position_id=?";
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
    public Long update(Position instance) {
        String request = "UPDATE company.tbl_positions SET position_name=?,position_description=? WHERE position_id=?";
        try (Connection connection = ConnectionPool.getConnection()) {
            if (isAvailable(connection, instance.getName())) {
                try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, instance.getName());
                    ps.setString(2, instance.getDescription());
                    ps.setLong(3, instance.getId());
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
        String request = "DELETE FROM company.tbl_positions WHERE position_id=?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(request)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
    }

    @Override
    public int countPages(int recordsOnPage) {
        String request = "SELECT COUNT(*) FROM company.tbl_positions";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(request)) {
            if (rs.next()) return (int) Math.ceil(rs.getInt(1) * 1.0 / recordsOnPage);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return 0;
    }

    @Override
    public List<Position> getPage(int page, int recordsOnPage) {
        List<Position> result = new ArrayList<>();
        String request = "SELECT * FROM company.tbl_positions LIMIT ?,?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(request)) {
            ps.setInt(1, page - 1);
            ps.setInt(2, recordsOnPage);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(getFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
        return result;
    }
}
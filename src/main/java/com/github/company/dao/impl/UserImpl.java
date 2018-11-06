package com.github.company.dao.impl;

import com.github.company.dao.entity.Group;
import com.github.company.dao.entity.Position;
import com.github.company.dao.entity.User;
import com.github.company.dao.model.UserDao;
import com.github.company.database.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserImpl implements UserDao {

    private static UserImpl instance = new UserImpl();

    public static UserImpl getInstance() {
        return instance;
    }

    private static final Logger LOGGER = Logger.getLogger(UserImpl.class);

    private UserImpl() {
    }

    private boolean isAvailable(Connection current, String login) throws SQLException {
        String request = "SELECT EXISTS(SELECT * FROM company.tbl_users WHERE user_login=?)";
        try (PreparedStatement ps = current.prepareStatement(request)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && !rs.getBoolean(1);
            }
        }
    }

    private User getFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setSurname(resultSet.getString("user_surname"));
        user.setFirstName(resultSet.getString("user_firstName"));
        user.setMiddleName(resultSet.getString("user_middleName"));
        user.setAvatar(resultSet.getString("user_avatar"));
        user.setPhone(resultSet.getLong("user_phone"));
        user.setLogin(resultSet.getString("user_login"));
        user.setPassword(resultSet.getString("user_password"));
        user.setRegisterDate(new Date(resultSet.getTimestamp("user_registerDate").getTime()));
        user.setGroup(new Group());
        user.getGroup().setId(resultSet.getLong("group_id"));
        user.getGroup().setName(resultSet.getString("group_name"));
        user.setPosition(new Position());
        user.getPosition().setId(resultSet.getLong("position_id"));
        user.getPosition().setName(resultSet.getString("position_name"));
        user.getPosition().setDescription(resultSet.getString("position_description"));
        return user;
    }

    private void setToPreparedStatementAndExecute(PreparedStatement ps, User instance) throws SQLException {
        ps.setString(1, instance.getSurname());
        ps.setString(2, instance.getFirstName());
        ps.setString(3, instance.getMiddleName());
        ps.setString(4, instance.getAvatar());
        ps.setLong(5, instance.getPhone());
        ps.setString(6, instance.getLogin());
        ps.setString(7, instance.getPassword());
        ps.setDate(8, new java.sql.Date(instance.getRegisterDate().getTime()));
        ps.setLong(9, instance.getGroup().getId());
        ps.setLong(10, instance.getPosition().getId());
        ps.executeUpdate();
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        String request = "SELECT * FROM company.tbl_users " +
                "INNER JOIN company.tbl_groups ON tbl_users.group_id=tbl_groups.group_id " +
                "INNER JOIN company.tbl_positions ON tbl_users.position_id=tbl_positions.position_id";
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
    public User getByLogin(String login) {
        String request = "SELECT * FROM company.tbl_users " +
                "INNER JOIN company.tbl_groups ON tbl_users.group_id=tbl_groups.group_id " +
                "INNER JOIN company.tbl_positions ON tbl_users.position_id=tbl_positions.position_id " +
                "WHERE user_login=?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(request)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return getFromResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    @Override
    public Long create(User newInstance) {
        String user = "INSERT INTO company.tbl_users VALUES(NULL,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = ConnectionPool.getConnection()) {
            if (isAvailable(connection, newInstance.getLogin())) {
                try (PreparedStatement ps = connection.prepareStatement(user, Statement.RETURN_GENERATED_KEYS)) {
                    setToPreparedStatementAndExecute(ps, newInstance);
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
    public User get(Long id) {
        String request = "SELECT * FROM company.tbl_users WHERE user_id=?";
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
    public Long update(User instance) {
        String request = "UPDATE company.tbl_users SET user_surname=?,user_firstName=?,user_middleName=?," +
                "user_avatar=?,user_phone=?,user_login=?,user_password=?, user_registerDate=?," +
                "group_id=?,position_id=? WHERE user_id=?";
        try (Connection connection = ConnectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                setToPreparedStatementAndExecute(ps, instance);
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
        return 0L;
    }

    @Override
    public void delete(Long id) {
        String request = "DELETE FROM company.tbl_users WHERE user_id=?";
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
        String request = "SELECT COUNT(*) FROM company.tbl_users";
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
    public List<User> getPage(int page, int recordsOnPage) {
        List<User> result = new ArrayList<>();
        String request = "SELECT * FROM company.tbl_users LIMIT ?,?";
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
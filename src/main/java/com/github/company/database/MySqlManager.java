package com.github.company.database;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlManager implements DatabaseManager {

    private static final Logger LOGGER = Logger.getLogger(MySqlManager.class);

    private Connection connection;
    private boolean stopOnError;

    public MySqlManager(Connection connection, boolean stopOnError) {
        this.connection = connection;
        this.stopOnError = stopOnError;
    }

    private void execCommand(String command) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(command);
        }
    }

    @Override
    public void runScript(InputStream inputStream) {
        String pattern = "(--.*?$)|(#.*?$)|(/\\*.*?\\*/)";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.replaceAll(pattern, ""));
                int position = sb.lastIndexOf(";");
                if (position == 0) sb.delete(0, sb.capacity());
                if (position > 0) {
                    this.execCommand(sb.toString().trim().replaceAll(pattern, ""));
                    sb.delete(0, sb.capacity());
                }
            }
            LOGGER.info("Database successfully created");
        } catch (IOException | SQLException e) {
            this.errorAction(stopOnError, e);
        }
    }

    @Override
    public boolean isCreated(String databaseName) {
        String request = String.format("SHOW DATABASES LIKE '%s'", databaseName);
        try (Connection connection = ConnectionPool.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(request)) {
            return rs.next();
        } catch (SQLException e) {
            this.errorAction(stopOnError, e);
        }
        return false;
    }

    private void errorAction(boolean stopOnError, Exception exception) {
        if (stopOnError) {
            LOGGER.fatal(exception.toString());
            throw new RuntimeException();
        } else LOGGER.error(exception.toString());
    }
}

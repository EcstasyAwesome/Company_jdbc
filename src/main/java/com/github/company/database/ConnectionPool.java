package com.github.company.database;

import com.github.company.util.PropUtil;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static final DataSource DATA_SOURCE;
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static final String IP = PropUtil.getProperty("app.database.ip");
    private static final String PORT = PropUtil.getProperty("app.database.port");
    private static final String PROPERTIES = PropUtil.getProperty("app.database.properties");
    private static final String USERNAME = PropUtil.getProperty("app.database.username");
    private static final String PASSWORD = PropUtil.getProperty("app.database.password");

    static {
        try {
            PoolProperties prop = new PoolProperties();
            prop.setDriverClassName("com.mysql.cj.jdbc.Driver");
            prop.setUrl(String.format("jdbc:mysql://%s:%s/", IP, PORT));
            prop.setConnectionProperties(PROPERTIES);
            prop.setUsername(USERNAME);
            prop.setPassword(PASSWORD);
            prop.setJmxEnabled(true);
            prop.setTestWhileIdle(false);
            prop.setTestOnBorrow(true);
            prop.setValidationQuery("SELECT 1");
            prop.setTestOnReturn(false);
            prop.setValidationInterval(30000);
            prop.setTimeBetweenEvictionRunsMillis(30000);
            prop.setMaxActive(100);
            prop.setInitialSize(10);
            prop.setMaxWait(10000);
            prop.setRemoveAbandonedTimeout(60);
            prop.setMinEvictableIdleTimeMillis(30000);
            prop.setMinIdle(10);
            prop.setLogAbandoned(true);
            prop.setRemoveAbandoned(true);
            prop.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                    "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            DATA_SOURCE = new DataSource();
            DATA_SOURCE.setPoolProperties(prop);
        } catch (Throwable x) {
            LOGGER.fatal(x.toString());
            throw new ExceptionInInitializerError(x);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

    public static void close() {
        if (DATA_SOURCE != null) DATA_SOURCE.close(true);
    }
}
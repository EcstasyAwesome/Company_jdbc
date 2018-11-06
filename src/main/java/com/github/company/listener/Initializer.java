package com.github.company.listener;

import com.github.company.database.ConnectionPool;
import com.github.company.database.MySqlManager;
import com.github.company.security.SecurityImpl;
import com.github.company.servlet.positions.PositionCreate;
import com.github.company.servlet.positions.PositionDelete;
import com.github.company.servlet.positions.PositionSearch;
import com.github.company.servlet.positions.PositionUpdate;
import com.github.company.servlet.root.About;
import com.github.company.servlet.root.Authorization;
import com.github.company.servlet.root.EditProfile;
import com.github.company.servlet.root.Profile;
import com.github.company.servlet.users.UserCreate;
import com.github.company.servlet.users.UserDelete;
import com.github.company.servlet.users.UserSearch;
import com.github.company.servlet.users.UserUpdate;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.io.InputStream;
import java.sql.Connection;

import static com.github.company.security.Security.*;

@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(Initializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection connection = ConnectionPool.getConnection();
             InputStream is = Initializer.class.getClassLoader().getResourceAsStream("dump.sql")) {
            MySqlManager mySqlManager = new MySqlManager(connection, true);
            if (!mySqlManager.isCreated("company")) mySqlManager.runScript(is);
            new SecurityImpl.Configurator(GUEST)
                    .configureServlet(PositionCreate.class, ADMIN)
                    .configureServlet(PositionSearch.class, USER)
                    .configureServlet(PositionUpdate.class, ADMIN)
                    .configureServlet(PositionDelete.class, ADMIN)
                    .configureServlet(UserCreate.class, ADMIN)
                    .configureServlet(UserSearch.class, USER)
                    .configureServlet(UserUpdate.class, ADMIN)
                    .configureServlet(UserDelete.class, ADMIN)
                    .configureServlet(Profile.class, USER)
                    .configureServlet(EditProfile.class, USER)
                    .configureServlet(Authorization.class, GUEST)
                    .configureServlet(About.class, GUEST)
                    .configureResource("/resources/")
                    .configureResource("/storage/");
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        LOGGER.info("Start server");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.close();
        LOGGER.info("Stop server");
    }
}
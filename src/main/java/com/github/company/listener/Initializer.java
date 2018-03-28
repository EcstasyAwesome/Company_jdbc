package com.github.company.listener;

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
import com.github.company.security.Security;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static com.github.company.security.Security.ADMIN;
import static com.github.company.security.Security.GUEST;
import static com.github.company.security.Security.USER;

@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(Initializer.class);
    private Security security = Security.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        security.configureIndexPage(GUEST);
        security.configureServlet(PositionCreate.class, ADMIN);
        security.configureServlet(PositionSearch.class, USER);
        security.configureServlet(PositionUpdate.class, ADMIN);
        security.configureServlet(PositionDelete.class, ADMIN);
        security.configureServlet(UserCreate.class, ADMIN);
        security.configureServlet(UserSearch.class, USER);
        security.configureServlet(UserUpdate.class, ADMIN);
        security.configureServlet(UserDelete.class, ADMIN);
        security.configureServlet(Profile.class, USER);
        security.configureServlet(EditProfile.class, USER);
        security.configureServlet(Authorization.class, GUEST);
        security.configureServlet(About.class, GUEST);
        security.configureResource("/resources/");
        security.configureResource("/storage/");
        LOGGER.info("Start server");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Stop server");
    }
}
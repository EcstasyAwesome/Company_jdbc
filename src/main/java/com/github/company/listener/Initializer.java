package com.github.company.listener;

import com.github.company.servlet.positions.PositionCreate;
import com.github.company.servlet.positions.PositionDelete;
import com.github.company.servlet.positions.PositionSearch;
import com.github.company.servlet.positions.PositionUpdate;
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
import static com.github.company.security.Security.USER;

@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(Initializer.class);
    private Security security = Security.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        security.add(PositionCreate.class, ADMIN);
        security.add(PositionSearch.class, USER);
        security.add(PositionUpdate.class, ADMIN);
        security.add(PositionDelete.class, ADMIN);
        security.add(UserCreate.class, ADMIN);
        security.add(UserSearch.class, USER);
        security.add(UserUpdate.class, ADMIN);
        security.add(UserDelete.class, ADMIN);
        security.add(Profile.class, USER);
        security.add(EditProfile.class, USER);
        LOGGER.info("Start server");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Stop server");
    }
}
package com.github.company.listener;

import com.github.company.util.PropertiesReader;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    public static final String ENCODING;
    public static final String DEFAULT_AVATAR;
    public static final String STORAGE_PATH;

    private static final Logger LOGGER = Logger.getLogger(Initializer.class);

    static {
        try {
            DEFAULT_AVATAR = PropertiesReader.getProperty("avatar");
            STORAGE_PATH = PropertiesReader.getProperty("storage");
            ENCODING = PropertiesReader.getProperty("encoding");
        } catch (NullPointerException e) {
            LOGGER.fatal(e.toString());
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Start server");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Stop server");
    }
}
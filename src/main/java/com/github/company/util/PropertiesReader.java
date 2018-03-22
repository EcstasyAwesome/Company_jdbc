package com.github.company.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger LOGGER = Logger.getLogger(PropertiesReader.class);
    private static Properties PROPERTIES = new Properties();

    static {
        try {
            String file = PropertiesReader.class.getClassLoader().getResource("/").getPath() + "config.properties";
            PROPERTIES.load(new FileInputStream(file));
        } catch (Exception e) {
            LOGGER.fatal(e.toString());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getProperty(String property) {
        String result;
        if ((result = PROPERTIES.getProperty(property)) == null) {
            String error = "not found '" + property + "' in config.properties";
            LOGGER.fatal(error);
            throw new ExceptionInInitializerError(error);
        }
        return result;
    }
}
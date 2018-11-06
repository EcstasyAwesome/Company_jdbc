package com.github.company.database;

import java.io.InputStream;

public interface DatabaseManager {
    boolean isCreated(String databaseName);
    void runScript(InputStream inputStream);
}
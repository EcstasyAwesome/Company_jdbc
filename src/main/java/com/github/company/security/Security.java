package com.github.company.security;

import com.github.company.dao.entity.User;
import com.sun.istack.Nullable;

import javax.servlet.annotation.WebServlet;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class Security {

    public static final int SUCCESS = 1;
    public static final int UNAUTHORIZED = 2;
    public static final int FORBIDDEN = 3;

    public static final int USER = 2;
    public static final int ADMIN = 3;

    private Map<String, Integer> container = new HashMap<>();

    private static Security instance = new Security();

    private Security() {
    }

    public static Security getInstance() {
        return instance;
    }

    public void add(@NotNull Class<?> servlet, @NotNull int access) {
        for (String url : servlet.getAnnotation(WebServlet.class).urlPatterns()) {
            container.put(url, access);
        }
    }

    public int check(@Nullable User user, @NotNull String link) {
        if (container.containsKey(link)) {
            if (user == null) return UNAUTHORIZED;
            else if (user.getGroup().getId() >= container.get(link)) return SUCCESS;
            else return FORBIDDEN;
        }
        return SUCCESS;
    }
}
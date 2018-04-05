package com.github.company.security;

import com.github.company.dao.entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.annotation.WebServlet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Security {

    public static final int SUCCESS = 1;
    public static final int UNAUTHORIZED = 2;
    public static final int FORBIDDEN = 3;
    public static final int NOT_FOUND = 4;

    public static final int GUEST = 1;
    public static final int USER = 2;
    public static final int ADMIN = 3;

    private Map<String, Integer> links = new HashMap<>();
    private Set<String> resources = new HashSet<>();

    private static Security instance = new Security();

    private Security() {
    }

    public static Security getInstance() {
        return instance;
    }

    public Security configureIndexPage(int access) {
        links.put("/", access);
        return this;
    }

    public Security configureServlet(@NotNull Class<?> annotatedClass, int access) {
        if (annotatedClass.isAnnotationPresent(WebServlet.class)) {
            for (String url : annotatedClass.getAnnotation(WebServlet.class).urlPatterns()) {
                links.put(url, access);
            }
        }
        return this;
    }

    public Security configureResource(@NotNull String resourceRootFolder) {
        if (resourceRootFolder.matches("^/\\w+/$")) resources.add(resourceRootFolder);
        return this;
    }

    public int verify(@Nullable User user, @NotNull String link) {
        if (links.containsKey(link)) {
            if (links.get(link) == GUEST) return SUCCESS;
            else if (user == null) return UNAUTHORIZED;
            else if (user.getGroup().getId() >= links.get(link)) return SUCCESS;
            else return FORBIDDEN;
        } else if (resources.contains(link.substring(0, link.substring(1).indexOf("/") + 2))) return SUCCESS;
        return NOT_FOUND;
    }
}
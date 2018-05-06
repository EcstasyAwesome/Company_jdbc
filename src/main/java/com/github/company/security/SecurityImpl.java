package com.github.company.security;

import com.github.company.dao.entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.annotation.WebServlet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SecurityImpl implements Security {

    public static final int GUEST = 1;
    public static final int USER = 2;
    public static final int ADMIN = 3;

    private Map<String, Integer> links = new HashMap<>();
    private Set<String> resources = new HashSet<>();

    private static SecurityImpl instance = new SecurityImpl();

    private SecurityImpl() {
    }

    public static SecurityImpl getInstance() {
        return instance;
    }

    @Override
    public Security configureIndexPage(int access) {
        links.put("/", access);
        return this;
    }

    @Override
    public Security configureServlet(@NotNull Class<?> annotatedClass, int access) {
        if (annotatedClass.isAnnotationPresent(WebServlet.class)) {
            for (String url : annotatedClass.getAnnotation(WebServlet.class).urlPatterns()) {
                links.put(url, access);
            }
        }
        return this;
    }

    @Override
    public Security configureResource(@NotNull String resourceRootFolder) {
        if (resourceRootFolder.matches("^/\\w+/$")) resources.add(resourceRootFolder);
        return this;
    }

    @Override
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
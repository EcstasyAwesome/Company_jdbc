package com.company.util;

import java.util.HashMap;
import java.util.Map;

public class LinkManager {

    private Map<String, Page> list = new HashMap<>();
    private static LinkManager instance;

    public static final String RESOURCES_FOLDER = "/resources/";
    public static final String AUTHORIZATION_LINK = "/authorization";
    public static final String MAIN_LINK = "/";
    public static final String PROFILE_LINK = "/profile";
    public static final String EDIT_LINK = "/edit";
    public static final String ABOUT_LINK = "/about";
    public static final String LOGIN_PAGE = "/pages/login.jsp";
    public static final String REGISTER_PAGE = "/pages/register.jsp";
    public static final String ACCESS_PAGE = "/pages/access.jsp";
    public static final String NOT_FOUND_PAGE = "/pages/404.jsp";

    private LinkManager() {
        list.put(MAIN_LINK, new Page("/pages/main.jsp", true));
        list.put(PROFILE_LINK, new Page("/pages/profile.jsp", true));
        list.put(EDIT_LINK, new Page("/pages/edit.jsp", true));
        list.put(ABOUT_LINK, new Page("/pages/about.jsp", true));
        list.put("/users", new Page("/pages/users_search.jsp", true));
        list.put("/users/add", new Page("/pages/users_add.jsp", false));
        list.put("/users/update", new Page("/pages/users_update.jsp", false));
        list.put("/users/delete", new Page("/pages/users_delete.jsp", false));
        list.put("/positions", new Page("/pages/positions_search.jsp", true));
        list.put("/positions/add", new Page("/pages/positions_add.jsp", false));
        list.put("/positions/update", new Page("/pages/positions_update.jsp", false));
        list.put("/positions/delete", new Page("/pages/positions_delete.jsp", false));
    }

    public static LinkManager getInstance() {
        if (instance == null)
            return instance = new LinkManager();
        return instance;
    }

    public Map<String, Page> getList() {
        return list;
    }

    public class Page {

        private String path;
        private boolean access;

        private Page(String path, boolean access) {
            this.path = path;
            this.access = access;
        }

        public String getPath() {
            return path;
        }

        public boolean getAccess() {
            return access;
        }
    }
}

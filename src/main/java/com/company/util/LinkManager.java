package com.company.util;

import java.util.HashMap;
import java.util.Map;

public class LinkManager {

    private Map<String, Page> list = new HashMap<>();
    private static LinkManager instance;

    public static final String RESOURCES_FOLDER = "/resources/";
    public static final String AUTHORIZATION_LINK = "/company/authorization";
    public static final String MAIN_LINK = "/company";
    public static final String LOGIN_PAGE = "/pages/login.jsp";
    public static final String REGISTER_PAGE = "/pages/register.jsp";
    public static final String ACCESS_PAGE = "/pages/access.jsp";
    public static final String NOT_FOUND_PAGE = "/pages/404.jsp";

    private LinkManager() {
        list.put("/company", new Page("/pages/main.jsp", true));
        list.put("/company/profile", new Page("/pages/profile.jsp", true));
        list.put("/company/edit", new Page("/pages/edit.jsp", true));
        list.put("/company/about", new Page("/pages/about.jsp", true));
        list.put("/company/access", new Page("/pages/access.jsp", true));
        list.put("/company/users", new Page("/pages/users_search.jsp", true));
        list.put("/company/users/add", new Page("/pages/users_add.jsp", false));
        list.put("/company/users/update", new Page("/pages/users_update.jsp", false));
        list.put("/company/users/delete", new Page("/pages/users_delete.jsp", false));
        list.put("/company/positions", new Page("/pages/positions_search.jsp", true));
        list.put("/company/positions/add", new Page("/pages/positions_add.jsp", false));
        list.put("/company/positions/update", new Page("/pages/positions_update.jsp", false));
        list.put("/company/positions/delete", new Page("/pages/positions_delete.jsp", false));
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

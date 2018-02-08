package com.company.util;

import java.util.HashMap;
import java.util.Map;

public class LinkManager {

    private Map<String, Page> list = new HashMap<>();
    private static LinkManager instance;

    public static final String RESOURCES_FOLDER = "/resources/";
    public static final String RESOURCES_EXTERNAL_FOLDER = "/storage/";
    public static final String AUTHORIZATION_LINK = "/authorization";
    public static final String MAIN_LINK = "/";
    public static final String PROFILE_LINK = "/profile";
    public static final String EDIT_LINK = "/edit";
    public static final String ABOUT_LINK = "/about";
    public static final String USERS_LINK = "/users";
    public static final String POSITIONS_LINK = "/positions";
    public static final String LOGIN_PAGE = "/WEB-INF/login.jsp";
    public static final String REGISTER_PAGE = "/WEB-INF/register.jsp";
    public static final String ACCESS_PAGE = "/WEB-INF/access.jsp";
    public static final String NOT_FOUND_PAGE = "/WEB-INF/404.jsp";

    private LinkManager() {
        list.put(MAIN_LINK, new Page("/index.jsp", true));
        list.put(PROFILE_LINK, new Page("/WEB-INF/profile.jsp", true));
        list.put(EDIT_LINK, new Page("/WEB-INF/edit.jsp", true));
        list.put(ABOUT_LINK, new Page("/WEB-INF/about.jsp", true));
        list.put(USERS_LINK, new Page("/WEB-INF/users_search.jsp", true));
        list.put(USERS_LINK + "/add", new Page("/WEB-INF/users_add.jsp", false));
        list.put(USERS_LINK + "/update", new Page("/WEB-INF/users_update.jsp", false));
        list.put(USERS_LINK + "/delete", new Page("/WEB-INF/users_delete.jsp", false));
        list.put(POSITIONS_LINK, new Page("/WEB-INF/positions_search.jsp", true));
        list.put(POSITIONS_LINK + "/add", new Page("/WEB-INF/positions_add.jsp", false));
        list.put(POSITIONS_LINK + "/update", new Page("/WEB-INF/positions_update.jsp", false));
        list.put(POSITIONS_LINK + "/delete", new Page("/WEB-INF/positions_delete.jsp", false));
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
        private boolean access;  // access for users

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

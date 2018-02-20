package com.company.util;

import com.company.servlet.Authorization;
import com.company.servlet.Main;
import com.company.servlet.Positions;

import java.util.HashMap;
import java.util.Map;

public class LinkManager {

    private static LinkManager instance;
    private final Map<String, Page> list = new HashMap<>();

    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE_REGISTER = "/WEB-INF/jsp/registration.jsp";

    private LinkManager() {
        list.put(Authorization.AUTHORIZATION, new Page(null, Page.ACCESS_ALL));
        list.put(Main.MAIN, new Page("/index.jsp", Page.ACCESS_ALL));
        list.put(Main.PROFILE, new Page("/WEB-INF/jsp/profile.jsp", Page.ACCESS_USER));
        list.put(Main.EDIT, new Page("/WEB-INF/jsp/edit.jsp", Page.ACCESS_USER));
        list.put(Main.ABOUT, new Page("/WEB-INF/jsp/about.jsp", Page.ACCESS_ALL));
//        list.put("/users", new Page("/WEB-INF/jsp/users_search.jsp", Page.ACCESS_USER));
//        list.put("/users/add", new Page("/WEB-INF/jsp/users_add.jsp", Page.ACCESS_ADMIN));
//        list.put("/users/update", new Page("/WEB-INF/jsp/users_update.jsp", Page.ACCESS_ADMIN));
//        list.put("/users/delete", new Page("/WEB-INF/jsp/users_delete.jsp", Page.ACCESS_ADMIN));
        list.put(Positions.MAIN, new Page("/WEB-INF/jsp/positions_search.jsp", Page.ACCESS_USER));
        list.put(Positions.ADD, new Page("/WEB-INF/jsp/positions_add.jsp", Page.ACCESS_ADMIN));
        list.put(Positions.UPDATE, new Page("/WEB-INF/jsp/positions_update.jsp", Page.ACCESS_ADMIN));
        list.put(Positions.DELETE, new Page("/WEB-INF/jsp/positions_delete.jsp", Page.ACCESS_ADMIN));
    }

    public static LinkManager getInstance() {
        if (instance == null) return instance = new LinkManager();
        return instance;
    }

    /**
     * @return returns map of available links
     * @see #getList()
     */

    public Map<String, Page> getList() {
        return list;
    }

    /**
     * @param link RequestURI
     * @return returns true if requestURI is a resource link
     * @see #isResources(String)
     */

    public boolean isResources(String link) {
        String path1 = "/resources/";
        String path2 = "/storage/";
        return link.startsWith(path1) | link.startsWith(path2);
    }

    public class Page {

        public static final int ACCESS_ALL = 0;
        public static final int ACCESS_USER = 1;
        public static final int ACCESS_ADMIN = 2;

        private String path;
        private int access;

        /**
         * available codes is here {{@link #ACCESS_ALL}{@link #ACCESS_USER}{@link #ACCESS_ADMIN}}
         *
         * @param path   path to jsp file
         * @param access access code
         * @see #Page(String, int)
         */

        private Page(String path, int access) {
            this.path = path;
            this.access = access;
        }

        public String getPath() {
            return path;
        }

        public int getAccess() {
            return access;
        }
    }
}

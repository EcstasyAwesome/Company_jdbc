package com.company.util;

import java.util.HashMap;
import java.util.Map;

public class LinkManager {

    private static LinkManager instance;
    private Map<String, Page> list = new HashMap<>();

    public static final int ACCESS_ALL = 0;
    public static final int ACCESS_USER = 1;
    public static final int ACCESS_ADMIN = 2;

    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE_REGISTER = "/WEB-INF/jsp/registration.jsp";

    private LinkManager() {
        list.put("/authorization", new Page(null, ACCESS_ALL));
        list.put("/", new Page("/index.jsp", ACCESS_ALL));
        list.put("/profile", new Page("/WEB-INF/jsp/profile.jsp", ACCESS_USER));
        list.put("/edit", new Page("/WEB-INF/jsp/edit.jsp", ACCESS_USER));
        list.put("/about", new Page("/WEB-INF/jsp/about.jsp", ACCESS_ALL));
        list.put("/users", new Page("/WEB-INF/jsp/users_search.jsp", ACCESS_USER));
        list.put("/users/add", new Page("/WEB-INF/jsp/users_add.jsp", ACCESS_ADMIN));
        list.put("/users/update", new Page("/WEB-INF/jsp/users_update.jsp", ACCESS_ADMIN));
        list.put("/users/delete", new Page("/WEB-INF/jsp/users_delete.jsp", ACCESS_ADMIN));
        list.put("/positions", new Page("/WEB-INF/jsp/positions_search.jsp", ACCESS_USER));
        list.put("/positions/add", new Page("/WEB-INF/jsp/positions_add.jsp", ACCESS_ADMIN));
        list.put("/positions/update", new Page("/WEB-INF/jsp/positions_update.jsp", ACCESS_ADMIN));
        list.put("/positions/delete", new Page("/WEB-INF/jsp/positions_delete.jsp", ACCESS_ADMIN));
    }

    public static LinkManager getInstance() {
        if (instance == null)
            return instance = new LinkManager();
        return instance;
    }

    public Map<String, Page> getList() {
        return list;
    }

    public boolean isResources(String link) {
        String path1 = "/resources/";
        String path2 = "/storage/";
        return link.startsWith(path1) | link.startsWith(path2);
    }

    public class Page {

        private String path;
        private int access;

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

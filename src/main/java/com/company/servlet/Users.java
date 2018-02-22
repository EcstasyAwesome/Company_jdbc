package com.company.servlet;

import com.company.dao.factory.DaoFactory;
import com.company.dao.model.UserDao;
import com.company.filter.Dispatcher;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
/*
 * in developing
 */

@WebServlet(
        name = "Users",
        description = "Users servlet",
        urlPatterns = {Users.MAIN, Users.ADD, Users.UPDATE, Users.DELETE}
)
public class Users extends HttpServlet {

    public static final String MAIN = "/users";
    public static final String ADD = "/users/add";
    public static final String UPDATE = "/users/update";
    public static final String DELETE = "/users/delete";

    private final String ID = "id";
    private final String NAME = "name";
    private final String USER = "user";
    private final String MESSAGE = "userError";

    private LinkManager linkManager = LinkManager.getInstance();
    private Map<String, LinkManager.Page> list = linkManager.getList();
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.getUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String link = Dispatcher.getLink();
        String query;
        if (link.equals(MAIN)) {
            int currentPage = 1;
            int recordsOnPage = 10;
            query = req.getQueryString();
            if (query != null) {
                if (query.matches("page=\\d+")) {
                    currentPage = Integer.parseInt(req.getParameter("page"));
                    int availablePages = userDao.countPages(recordsOnPage);
                    if (currentPage <= availablePages) {
                        req.setAttribute("availablePages", availablePages);
                        req.setAttribute("currentPage", currentPage);
                        req.setAttribute("users", userDao.getPage(currentPage, recordsOnPage));
                        req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
                    } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                } else if (query.matches("key=\\w+&value=\\w+")) {
                    // in developing
                }
            } else {
                req.setAttribute("availablePages", userDao.countPages(recordsOnPage));
                req.setAttribute("currentPage", currentPage);
                req.setAttribute("users", userDao.getPage(currentPage, recordsOnPage));
                req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
            }
        } else if (link.equals(UPDATE) | link.equals(DELETE)) {
            query = req.getQueryString();
            if (query != null && query.matches("id=\\d+")) {
                long id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute(USER, userDao.get(id));
                req.getRequestDispatcher(list.get(link).getPath()).forward(req, resp);
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else req.getRequestDispatcher(list.get(link).getPath()).forward(req, resp);
    }
}

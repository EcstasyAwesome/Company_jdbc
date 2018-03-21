package com.github.company.servlet.users;

import com.github.company.dao.DaoService;
import com.github.company.dao.model.UserDao;
import com.github.company.filter.Dispatcher;
import com.github.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Users search", urlPatterns = UserSearch.MAIN)
public class UserSearch extends HttpServlet {

    public static final String MAIN = "/users";

    private Map<String, LinkManager.Page> list = LinkManager.getInstance().getList();
    private UserDao userDao = DaoService.getInstance().getUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query;
        int currentPage = 1;
        int recordsOnPage = 10;
        query = req.getQueryString();
        if (query != null && query.matches("^page=\\d+$")) {
            currentPage = Integer.parseInt(req.getParameter("page"));
            int availablePages = userDao.countPages(recordsOnPage);
            if (currentPage <= availablePages) {
                req.setAttribute("availablePages", availablePages);
                req.setAttribute("currentPage", currentPage);
                req.setAttribute("users", userDao.getPage(currentPage, recordsOnPage));
                req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            req.setAttribute("availablePages", userDao.countPages(recordsOnPage));
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("users", userDao.getPage(currentPage, recordsOnPage));
            req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
        }
    }
}
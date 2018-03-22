package com.github.company.servlet.users;

import com.github.company.dao.DaoService;
import com.github.company.dao.model.UserDao;
import com.github.company.util.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Users search", urlPatterns = "/users")
public class UserSearch extends HttpServlet {

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
                Dispatcher.dispatch(req, resp, "users_search");
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            req.setAttribute("availablePages", userDao.countPages(recordsOnPage));
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("users", userDao.getPage(currentPage, recordsOnPage));
            Dispatcher.dispatch(req, resp, "users_search");
        }
    }
}
package com.github.company.servlet.users;

import com.github.company.dao.DaoService;
import com.github.company.dao.model.UserDao;
import com.github.company.util.Avatar;
import com.github.company.util.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Users delete", urlPatterns = "/users/delete")
public class UserDelete extends HttpServlet {

    private UserDao userDao = DaoService.getInstance().getUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        if (query != null && query.matches("^id=\\d+$")) {
            long id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("user", userDao.get(id));
            Dispatcher.dispatch(req, resp, "users_delete");
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userDao.delete(Long.parseLong(req.getParameter("id")));
        Avatar.delete(req.getParameter("avatar"));
        resp.sendRedirect("/users");
    }
}
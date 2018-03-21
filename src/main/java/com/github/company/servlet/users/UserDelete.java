package com.github.company.servlet.users;

import com.github.company.dao.DaoService;
import com.github.company.dao.model.UserDao;
import com.github.company.filter.Dispatcher;
import com.github.company.util.AvatarUtil;
import com.github.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Users delete", urlPatterns = UserDelete.DELETE)
public class UserDelete extends HttpServlet {

    public static final String DELETE = "/users/delete";

    private Map<String, LinkManager.Page> list = LinkManager.getInstance().getList();
    private UserDao userDao = DaoService.getInstance().getUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        if (query != null && query.matches("^id=\\d+$")) {
            long id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("user", userDao.get(id));
            req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            userDao.delete(Long.parseLong(req.getParameter("id")));
            AvatarUtil.deleteFromStorage(req.getParameter("avatar"));
            resp.sendRedirect(UserSearch.MAIN);
        } catch (Exception e) {
            resp.sendRedirect(Dispatcher.getLink() + "?" + req.getQueryString());
        }
    }
}
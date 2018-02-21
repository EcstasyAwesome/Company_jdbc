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

    private LinkManager linkManager = LinkManager.getInstance();
    private Map<String,LinkManager.Page> list = linkManager.getList();
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.getUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("users",userDao.getPage(3,2));
        req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
    }

}

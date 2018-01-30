package com.company.servlet;

import com.company.filter.Dispatcher;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Users extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("positions", null);
//        if (req.getQueryString() == null) {
//            req.setAttribute("button", false);
//        } else req.setAttribute("button", true);
        req.getRequestDispatcher(LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, resp);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String form = req.getParameter("form");
        int id;
        String surname;
        String firstName;
        String secondName;
        long phoneNumber;
        int position;
        String login;
        String password;
        boolean admin;
        String name;
        String description;
        if (form != null) {
            switch (form) {
                case "addUser":
                    surname = req.getParameter("user_surname");
                    firstName = req.getParameter("user_firstName");
                    secondName = req.getParameter("user_secondName");
                    phoneNumber = Long.parseLong(req.getParameter("user_phoneNumber"));
                    String positionId = req.getParameter("position_id");
                    position = positionId != null ? Integer.parseInt(positionId) : 5;
                    login = req.getParameter("user_login");
                    password = req.getParameter("user_password");
                    String isAdmin = req.getParameter("user_isAdmin");
                    admin = isAdmin != null && Boolean.parseBoolean(isAdmin);
//                    new UserDAO(connection).addUser(surname, firstName, secondName, phoneNumber, position, login, password, admin);
                    resp.sendRedirect("/company/users");
                    break;
                case "updateUser":
                    surname = req.getParameter("user_surname");
                    firstName = req.getParameter("user_firstName");
                    secondName = req.getParameter("user_secondName");
                    phoneNumber = Long.parseLong(req.getParameter("user_phoneNumber"));
                    position = Integer.parseInt(req.getParameter("position_id"));
                    id = Integer.parseInt(req.getParameter("user_id"));
                    password = req.getParameter("user_password");
                    admin = Boolean.parseBoolean(req.getParameter("user_isAdmin"));
//                    new UserDAO(connection).updateUser(id, surname, firstName, secondName, phoneNumber, position, password, admin);
                    resp.sendRedirect("/company/users?key=user_id&value=" + id);
                    break;
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String form = req.getParameter("form");
        int id;
        if (form != null) {
            switch (form) {
                case "deleteUser":
                    id = Integer.parseInt(req.getParameter("user_id"));
//                    new UserDAO(connection).deleteUser(id);
                    resp.sendRedirect("/company/users");
                    break;
            }
        }
    }
}

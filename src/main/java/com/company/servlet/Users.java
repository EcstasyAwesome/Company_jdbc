package com.company.servlet;

import com.company.filter.Dispatcher;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
* in developing
*/

@WebServlet(
        name = "Users",
        description = "Users servlet",
        urlPatterns = "/users/*"
)
public class Users extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, resp);
    }

}

package com.company.servlet;

import com.company.filter.Dispatcher;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebServlet(
        name = "Main",
        description = "main servlet",
        urlPatterns = {LinkManager.PROFILE_LINK, LinkManager.EDIT_LINK, LinkManager.ABOUT_LINK}
)
public class Main extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, res);
    }
}

package com.company.servlet;

import com.company.filter.Dispatcher;
import com.company.util.AvatarUtil;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(
        name = "Main",
        description = "main servlet",
        urlPatterns = {LinkManager.PROFILE_LINK, LinkManager.EDIT_LINK, LinkManager.ABOUT_LINK}
)
@MultipartConfig
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null && method.equals("UPDATE")) {
            updateProfile(req, resp);
            resp.sendRedirect(LinkManager.PROFILE_LINK);
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String surname = req.getParameter("user_surname");
        String firstName = req.getParameter("user_firstName");
        String secondName = req.getParameter("user_secondName");
        long phoneNumber = Long.parseLong(req.getParameter("user_phoneNumber"));
        String password = req.getParameter("user_password");
        String deleteAvatar = req.getParameter("deleteAvatar");
        AvatarUtil avatar = new AvatarUtil(req);
        if (deleteAvatar != null && deleteAvatar.equals("true")) avatar.delete();
        // in developing
    }
}

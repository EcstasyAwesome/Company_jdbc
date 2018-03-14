package com.company.servlet;

import com.company.dao.DaoService;
import com.company.dao.model.UserDao;
import com.company.filter.Dispatcher;
import com.company.dao.entity.User;
import com.company.util.AvatarUtil;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Main", urlPatterns = {Main.PROFILE, Main.EDIT, Main.ABOUT})
@MultipartConfig
public class Main extends HttpServlet {

    public static final String MAIN = "/";
    public static final String PROFILE = "/profile";
    public static final String EDIT = "/edit";
    public static final String ABOUT = "/about";

    private Map<String, LinkManager.Page> list = LinkManager.getInstance().getList();
    private UserDao userDao = DaoService.getInstance().getUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null) {
            switch (method) {
                case "UPDATE":
                    updateProfile(req, resp);
                    break;
                case "DELETE":
                    new AvatarUtil().delete(req);
                    resp.sendRedirect(Dispatcher.getLink());
                    break;
            }
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        AvatarUtil avatarUtil = new AvatarUtil();
        HttpSession httpSession = req.getSession(false);
        String sessionUser = "sessionUser";
        String message = "profileError";
        User user = (User) httpSession.getAttribute(sessionUser);
        try {
            String surname = req.getParameter("surname");
            String firstName = req.getParameter("firstName");
            String middleName = req.getParameter("middleName");
            long phone = Long.parseLong(req.getParameter("phone"));
            String password = req.getParameter("password");
            user.setSurname(surname);
            user.setFirstName(firstName);
            user.setMiddleName(middleName);
            user.setPhone(phone);
            user.setPassword(password);
            user.setAvatar(avatarUtil.save(req));
            userDao.update(user);
            httpSession.setAttribute(sessionUser, user);
            avatarUtil.clean();
            resp.sendRedirect(PROFILE);
        } catch (Exception e) {
            avatarUtil.rollBack();
            req.setAttribute(message, e.getMessage());
            req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
        }
    }
}

package com.github.company.servlet.root;

import com.github.company.dao.DaoService;
import com.github.company.dao.entity.User;
import com.github.company.dao.model.UserDao;
import com.github.company.util.Avatar;
import com.github.company.util.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.github.company.util.Avatar.DEFAULT_AVATAR;

@WebServlet(name = "Edit profile", urlPatterns = "/edit")
@MultipartConfig
public class EditProfile extends HttpServlet {

    private UserDao userDao = DaoService.getInstance().getUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher.dispatch(req, resp, "edit");
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
                    deleteAvatar(req, resp);
                    break;
            }
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Avatar avatar = new Avatar();
        HttpSession httpSession = req.getSession(false);
        User user = (User) httpSession.getAttribute("sessionUser");
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
            String img = avatar.upload(req.getPart("avatar"));
            if (img != null) {
                Avatar.delete(user.getAvatar());
                user.setAvatar(img);
            }
            userDao.update(user);
            httpSession.setAttribute("sessionUser", user);
            resp.sendRedirect("/profile");
        } catch (Exception e) {
            avatar.rollBack();
            req.setAttribute("profileError", e.getMessage());
            Dispatcher.dispatch(req, resp, "edit");
        }
    }

    private void deleteAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession(false);
        User user = (User) httpSession.getAttribute("sessionUser");
        if (!user.getAvatar().equals(DEFAULT_AVATAR)) {
            Avatar.delete(user.getAvatar());
            user.setAvatar(DEFAULT_AVATAR);
            httpSession.setAttribute("sessionUser", user);
            userDao.update(user);
        }
        resp.sendRedirect("/edit");
    }
}
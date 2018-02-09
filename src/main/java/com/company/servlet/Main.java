package com.company.servlet;

import com.company.filter.Dispatcher;
import com.company.pojo.User;
import com.company.util.AvatarUtil;
import com.company.util.HibernateUtil;
import com.company.util.LinkManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        if (method != null) {
            switch (method) {
                case "UPDATE":
                    updateProfile(req, resp);
                    break;
                case "DELETE":
                    new AvatarUtil(req).delete(true);
                    resp.sendRedirect(LinkManager.PROFILE_LINK);
                    break;
            }
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String surname = req.getParameter("user_surname");
        String firstName = req.getParameter("user_firstName");
        String secondName = req.getParameter("user_secondName");
        long phoneNumber = Long.parseLong(req.getParameter("user_phoneNumber"));
        String password = req.getParameter("user_password");
        AvatarUtil avatarUtil = new AvatarUtil(req);
        HttpSession httpSession = req.getSession(false);
        Session session = HibernateUtil.getSession();
        try {
            User sessionUser = (User) httpSession.getAttribute("sessionUser");
            String avatar = avatarUtil.saveOrUpdate();
            sessionUser.setUserSurname(surname);
            sessionUser.setUserFirstName(firstName);
            sessionUser.setUserSecondName(secondName);
            sessionUser.setUserPhoneNumber(phoneNumber);
            sessionUser.setUserPassword(password);
            sessionUser.setUserAvatar(avatar);
            session.beginTransaction();
            session.update(sessionUser);
            session.getTransaction().commit();
            httpSession.setAttribute("sessionUser", sessionUser);
            resp.sendRedirect(LinkManager.PROFILE_LINK);
        } catch (Exception e) {
            if (e instanceof HibernateException)
                req.setAttribute("profileError", "Ошибка при изменении профиля");
            if (e instanceof IllegalStateException)
                req.setAttribute("profileError", "Загружаемый файл слишком большой");
            req.getRequestDispatcher(LinkManager.getInstance().getList().get(LinkManager.EDIT_LINK).getPath()).forward(req, resp);
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }
}

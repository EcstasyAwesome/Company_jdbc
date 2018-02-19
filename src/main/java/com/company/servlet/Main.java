package com.company.servlet;

import com.company.filter.Dispatcher;
import com.company.dao.entity.User;
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
        urlPatterns = {"/profile", "/edit", "/about"}
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
                    resp.sendRedirect(Dispatcher.getLink());
                    break;
            }
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String surname = req.getParameter("surname");
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("middleName");
        long phoneNumber = Long.parseLong(req.getParameter("phone"));
        String password = req.getParameter("password");
        AvatarUtil avatarUtil = new AvatarUtil(req);
        HttpSession httpSession = req.getSession(false);
        Session session = HibernateUtil.getSession();
        try {
            User sessionUser = (User) httpSession.getAttribute("sessionUser");
            String avatar = avatarUtil.save();
            sessionUser.setSurname(surname);
            sessionUser.setFirstName(firstName);
            sessionUser.setMiddleName(secondName);
            sessionUser.setPhone(phoneNumber);
            sessionUser.setPassword(password);
            sessionUser.setAvatar(avatar);
            session.beginTransaction();
            session.update(sessionUser);
            session.getTransaction().commit();
            httpSession.setAttribute("sessionUser", sessionUser);
            resp.sendRedirect("/profile");
        } catch (Exception e) {
            if (e instanceof HibernateException)
                req.setAttribute("profileError", "Ошибка при изменении профиля");
            if (e instanceof IllegalStateException)
                req.setAttribute("profileError", "Загружаемый файл слишком большой");
            req.getRequestDispatcher(LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, resp);
        } finally {
            avatarUtil.clean();
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }
}

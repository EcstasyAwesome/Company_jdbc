package com.company.servlet;

import com.company.pojo.Position;
import com.company.pojo.User;
import com.company.util.HibernateUtil;
import com.company.util.LinkManager;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet(
        name = "Authorization",
        description = "responsible for authorization and registration",
        urlPatterns = LinkManager.AUTHORIZATION_LINK
)
public class Authorization extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LinkManager.LOGIN_PAGE).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null) {
            switch (method) {
                case "GO_REGISTER":
                    req.getRequestDispatcher(LinkManager.REGISTER_PAGE).forward(req, resp);
                    break;
                case "GO_LOGIN":
                    req.getRequestDispatcher(LinkManager.LOGIN_PAGE).forward(req, resp);
                    break;
                case "LOGIN":
                    login(req, resp);
                    break;
                case "REGISTER":
                    register(req, resp);
                    break;
            }
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession = request.getSession(false);
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("userLogin"), login));
            Query<User> query1 = session.createQuery(query);
            User user = query1.getSingleResult();
            session.getTransaction().commit();
            if (user.getUserPassword().equals(password)) {
                httpSession.setAttribute("sessionUser", user);
                System.out.println(String.format("-> [%s] Выполнен вход: %s", getClass().getSimpleName(), user));
                response.sendRedirect(LinkManager.MAIN_LINK);
            } else {
                request.setAttribute("message", "Ошибка доступа. Не правильный пароль");
                request.setAttribute("login", login);
                request.getRequestDispatcher(LinkManager.LOGIN_PAGE).forward(request, response);
            }
        } catch (PersistenceException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.err.println(e.getMessage());
            System.err.println(e.getLocalizedMessage());
            request.setAttribute("message", "Пользователь '" + login + "' не зарегистрирован");
            request.getRequestDispatcher(LinkManager.LOGIN_PAGE).forward(request, response);
        } finally {
            session.close();
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("user_login");
        String password = request.getParameter("user_password");
        String surname = request.getParameter("user_surname");
        String firstName = request.getParameter("user_firstName");
        String secondName = request.getParameter("user_secondName");
        long phoneNumber = Long.parseLong(request.getParameter("user_phoneNumber")); // have pattern on page
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Position position = session.get(Position.class, 6);
            User user = new User(surname, firstName, secondName, phoneNumber, login, password, new Date(), false, position);
            session.save(user);
            session.getTransaction().commit();
            request.setAttribute("message", "Регистрация успешно завершена");
            request.getRequestDispatcher(LinkManager.LOGIN_PAGE).forward(request, response);
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.err.println(e.getMessage());
            request.setAttribute("surname", surname);
            request.setAttribute("firstName", firstName);
            request.setAttribute("secondName", secondName);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("message", "Логин '" + login + "' уже зарегистрирован");
            request.getRequestDispatcher(LinkManager.REGISTER_PAGE).forward(request, response);
        } finally {
            session.close();
        }
    }
}

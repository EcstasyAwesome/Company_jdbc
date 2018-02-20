package com.company.servlet;

import com.company.dao.entity.Position;
import com.company.dao.entity.User;
import com.company.dao.factory.DaoFactory;
import com.company.dao.model.UserDao;
import com.company.util.AvatarUtil;
import com.company.util.LinkManager;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.Date;

@WebServlet(name = "Authorization", urlPatterns = Authorization.AUTHORIZATION)
@MultipartConfig
public class Authorization extends HttpServlet {

    public static final String AUTHORIZATION = "/authorization";

    private final String userLogin = "login";
    private final String userPassword = "password";
    private final String userSurname = "surname";
    private final String userFirstName = "firstName";
    private final String userMiddleName = "middleName";
    private final String userPhone = "phone";
    private final String message = "message";
    private final String sessionUser = "sessionUser";
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.getUserDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LinkManager.PAGE_LOGIN).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null) {
            switch (method) {
                case "GO_REGISTER":
                    req.getRequestDispatcher(LinkManager.PAGE_REGISTER).forward(req, resp);
                    break;
                case "GO_LOGIN":
                    req.getRequestDispatcher(LinkManager.PAGE_LOGIN).forward(req, resp);
                    break;
                case "LOGIN":
                    login(req, resp);
                    break;
                case "REGISTER":
                    register(req, resp);
                    break;
                case "LOGOUT":
                    logout(req, resp);
                    break;
            }
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession = request.getSession(false);
        try {
            String login = request.getParameter(userLogin);
            String password = request.getParameter(userPassword);
            User user = userDao.getByLogin(login);
            if (user.getPassword().equals(password)) {
                httpSession.setAttribute(sessionUser, user);
                String info = "-> [%s] Выполнен вход: %s\n";
                System.out.printf(info, getClass().getSimpleName(), user.basicInfo());
                response.sendRedirect("/");
            } else {
                request.setAttribute(message, "Ошибка доступа. Не правильный пароль");
                request.setAttribute(userLogin, login);
                request.getRequestDispatcher(LinkManager.PAGE_LOGIN).forward(request, response);
            }
        } catch (PersistenceException e) {
            request.setAttribute(message, e.getMessage());
            request.getRequestDispatcher(LinkManager.PAGE_LOGIN).forward(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AvatarUtil avatar = new AvatarUtil(request);
        User user = new User();
        Position position = new Position();
        position.setId(5); // default
        String login = null;
        String surname = null;
        String firstName = null;
        String middleName = null;
        long phone = 0;
        try {
            user.setLogin(login = request.getParameter(userLogin));
            user.setPassword(request.getParameter(userPassword));
            user.setSurname(surname = request.getParameter(userSurname));
            user.setFirstName(firstName = request.getParameter(userFirstName));
            user.setMiddleName(middleName = request.getParameter(userMiddleName));
            user.setPhone(phone = Long.parseLong(request.getParameter(userPhone)));
            user.setRegisterDate(new Date());
            user.setStatus(LinkManager.Page.ACCESS_USER);
            user.setAvatar(avatar.save());
            user.setPosition(position);
            userDao.create(user);
            request.setAttribute(message, "Регистрация успешно завершена");
            request.getRequestDispatcher(LinkManager.PAGE_LOGIN).forward(request, response);
        } catch (Exception e) {
            avatar.rollBack();
            request.setAttribute(userSurname, surname);
            request.setAttribute(userFirstName, firstName);
            request.setAttribute(userMiddleName, middleName);
            request.setAttribute(userPhone, phone);
            if (e instanceof IllegalStateException) request.setAttribute(userLogin, login);
            request.setAttribute(message, e.getMessage());
            request.getRequestDispatcher(LinkManager.PAGE_REGISTER).forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(sessionUser);
        String info = "-> [%s] Выполнен выход: %s\n";
        System.out.printf(info, getClass().getSimpleName(), user.basicInfo());
        session.invalidate();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

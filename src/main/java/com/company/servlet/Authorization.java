package com.company.servlet;

import com.company.dao.DaoService;
import com.company.dao.entity.Group;
import com.company.dao.entity.Position;
import com.company.dao.entity.User;
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

    private final String LOGIN = "login";
    private final String PASSWORD = "password";
    private final String MESSAGE = "message";
    private final String USER = "sessionUser";
    private UserDao userDao = DaoService.getInstance().getUserDao();

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
            String login = request.getParameter(LOGIN);
            String password = request.getParameter(PASSWORD);
            User user = userDao.getByLogin(login);
            if (user.getPassword().equals(password)) {
                httpSession.setAttribute(USER, user);
                String info = "-> [%s] Выполнен вход: %s\n";
                System.out.printf(info, getClass().getSimpleName(), user.basicInfo());
                response.sendRedirect("/");
            } else {
                request.setAttribute(MESSAGE, "Ошибка доступа. Не правильный пароль");
                request.setAttribute(LOGIN, login);
                request.getRequestDispatcher(LinkManager.PAGE_LOGIN).forward(request, response);
            }
        } catch (PersistenceException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            request.getRequestDispatcher(LinkManager.PAGE_LOGIN).forward(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AvatarUtil avatar = new AvatarUtil();
        User user = new User();
        Position position = new Position();
        position.setId(5); // default
        Group group = new Group();
        group.setId(LinkManager.Page.USER); // default
        String login = null;
        String surname = null;
        String firstName = null;
        String middleName = null;
        long phone = 0;
        try {
            user.setLogin(login = request.getParameter(LOGIN));
            user.setPassword(request.getParameter(PASSWORD));
            user.setSurname(surname = request.getParameter("surname"));
            user.setFirstName(firstName = request.getParameter("firstName"));
            user.setMiddleName(middleName = request.getParameter("middleName"));
            user.setPhone(phone = Long.parseLong(request.getParameter("phone")));
            user.setRegisterDate(new Date());
            user.setGroup(group);
            user.setAvatar(avatar.save(request));
            user.setPosition(position);
            userDao.create(user);
            request.setAttribute(MESSAGE, "Регистрация успешно завершена");
            request.getRequestDispatcher(LinkManager.PAGE_LOGIN).forward(request, response);
        } catch (Exception e) {
            avatar.rollBack();
            request.setAttribute("surname", surname);
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("phone", phone);
            if (e instanceof IllegalStateException) request.setAttribute(LOGIN, login);
            request.setAttribute(MESSAGE, e.getMessage());
            request.getRequestDispatcher(LinkManager.PAGE_REGISTER).forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(USER);
        String info = "-> [%s] Выполнен выход: %s\n";
        System.out.printf(info, getClass().getSimpleName(), user.basicInfo());
        session.invalidate();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
package com.github.company.servlet.root;

import com.github.company.dao.DaoService;
import com.github.company.dao.entity.Group;
import com.github.company.dao.entity.Position;
import com.github.company.dao.entity.User;
import com.github.company.dao.model.UserDao;
import com.github.company.util.Avatar;
import com.github.company.util.Dispatcher;
import org.apache.log4j.Logger;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.Date;

import static com.github.company.security.Security.USER;

@WebServlet(name = "Authorization", urlPatterns = "/authorization")
@MultipartConfig
public class Authorization extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Authorization.class);

    private final String login = "login";
    private final String password = "password";
    private final String message = "message";
    private final String user = "sessionUser";
    private UserDao userDao = DaoService.getInstance().getUserDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher.dispatch(req, resp, "login");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null) {
            switch (method) {
                case "GO_REGISTER":
                    Dispatcher.dispatch(req, resp, "registration");
                    break;
                case "GO_LOGIN":
                    Dispatcher.dispatch(req, resp, "login");
                    break;
                case "LOGIN":
                    login(req, resp);
                    break;
                case "REGISTER":
                    registration(req, resp);
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
            String login = request.getParameter(this.login);
            String password = request.getParameter(this.password);
            User user = userDao.getByLogin(login);
            if (user.getPassword().equals(password)) {
                httpSession.setAttribute(this.user, user);
                LOGGER.info("Login - " + user.basicInfo());
                response.sendRedirect("/");
            } else {
                LOGGER.info(login + " - incorrect password");
                request.setAttribute(message, "Ошибка доступа. Не правильный пароль");
                request.setAttribute(this.login, login);
                Dispatcher.dispatch(request, response, "login");
            }
        } catch (PersistenceException e) {
            request.setAttribute(message, e.getMessage());
            Dispatcher.dispatch(request, response, "login");
        }
    }

    private void registration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Avatar avatar = new Avatar();
        User user = new User();
        Position position = new Position();
        position.setId(5); // default
        Group group = new Group();
        group.setId(USER); // default
        String login = null;
        String surname = null;
        String firstName = null;
        String middleName = null;
        long phone = 0;
        try {
            user.setLogin(login = request.getParameter(this.login));
            user.setPassword(request.getParameter(password));
            user.setSurname(surname = request.getParameter("surname"));
            user.setFirstName(firstName = request.getParameter("firstName"));
            user.setMiddleName(middleName = request.getParameter("middleName"));
            user.setPhone(phone = Long.parseLong(request.getParameter("phone")));
            user.setRegisterDate(new Date());
            user.setGroup(group);
            user.setAvatar(avatar.save(request));
            user.setPosition(position);
            userDao.create(user);
            request.setAttribute(message, "Регистрация успешно завершена");
            Dispatcher.dispatch(request,response,"login");
        } catch (Exception e) {
            avatar.rollBack();
            request.setAttribute("surname", surname);
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("phone", phone);
            if (e instanceof IllegalStateException) request.setAttribute(this.login, login);
            request.setAttribute(message, e.getMessage());
            Dispatcher.dispatch(request,response,"registration");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(this.user);
        LOGGER.info("Logout - " + user.basicInfo());
        session.invalidate();
        response.sendRedirect("/");
    }
}
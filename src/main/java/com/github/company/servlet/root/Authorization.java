package com.github.company.servlet.root;

import com.github.company.dao.DaoService;
import com.github.company.dao.entity.Group;
import com.github.company.dao.entity.Position;
import com.github.company.dao.entity.User;
import com.github.company.dao.model.UserDao;
import com.github.company.util.Avatar;
import com.github.company.util.Dispatcher;
import com.github.company.util.Uploader;
import org.apache.log4j.Logger;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.github.company.security.Security.USER;
import static com.github.company.util.Avatar.DEFAULT_AVATAR;

@WebServlet(name = "Authorization", urlPatterns = "/authorization")
@MultipartConfig
public class Authorization extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Authorization.class);

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
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            User user = userDao.getByLogin(login);
            if (user.getPassword().equals(password)) {
                httpSession.setAttribute("sessionUser", user);
                LOGGER.info("Login - " + user);
                response.sendRedirect("/");
            } else {
                LOGGER.info(login + " - incorrect password");
                request.setAttribute("message", "Ошибка доступа. Не правильный пароль");
                request.setAttribute("login", login);
                Dispatcher.dispatch(request, response, "login");
            }
        } catch (PersistenceException e) {
            request.setAttribute("message", e.getMessage());
            Dispatcher.dispatch(request, response, "login");
        }
    }

    private void registration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Uploader avatar = new Avatar();
        User user = new User();
        try {
            user.setLogin(request.getParameter("login"));
            user.setPassword(request.getParameter("password"));
            user.setSurname(request.getParameter("surname"));
            user.setFirstName(request.getParameter("firstName"));
            user.setMiddleName(request.getParameter("middleName"));
            user.setPhone(Long.parseLong(request.getParameter("phone")));
            user.setGroup(new Group(USER)); // default group
            user.setPosition(new Position(5)); // default position
            String img = avatar.upload(request.getPart("avatar"));
            user.setAvatar(img != null ? img : DEFAULT_AVATAR);
            userDao.create(user);
            request.setAttribute("message", "Регистрация успешно завершена");
            Dispatcher.dispatch(request, response, "login");
        } catch (Exception e) {
            avatar.rollBack();
            request.setAttribute("user", user);
            request.setAttribute("message", e.getMessage());
            Dispatcher.dispatch(request, response, "registration");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("sessionUser");
        LOGGER.info("Logout - " + user);
        session.invalidate();
        response.sendRedirect("/");
    }
}
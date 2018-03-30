package com.github.company.servlet.users;

import com.github.company.dao.DaoService;
import com.github.company.dao.entity.Group;
import com.github.company.dao.entity.Position;
import com.github.company.dao.entity.User;
import com.github.company.dao.model.GroupDao;
import com.github.company.dao.model.PositionDao;
import com.github.company.dao.model.UserDao;
import com.github.company.util.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.company.util.Avatar.DEFAULT_AVATAR;

@WebServlet(name = "Users create", urlPatterns = "/users/add")
public class UserCreate extends HttpServlet {

    private UserDao userDao = DaoService.getInstance().getUserDao();
    private PositionDao positionDao = DaoService.getInstance().getPositionDao();
    private GroupDao groupDao = DaoService.getInstance().getGroupDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("positions", positionDao.getAll());
        req.setAttribute("groups", groupDao.getAll());
        Dispatcher.dispatch(req, resp, "users_add");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = new User();
        Position position = new Position();
        Group group = new Group();
        String surname = null;
        String firstName = null;
        String middleName = null;
        long positionId = 0;
        long groupId = 0;
        long phone = 0;
        try {
            positionId = Long.parseLong(req.getParameter("position"));
            groupId = Long.parseLong(req.getParameter("group"));
            position.setId(positionId);
            group.setId(groupId);
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setSurname(surname = req.getParameter("surname"));
            user.setFirstName(firstName = req.getParameter("firstName"));
            user.setMiddleName(middleName = req.getParameter("middleName"));
            user.setPhone(phone = Long.parseLong(req.getParameter("phone")));
            user.setAvatar(DEFAULT_AVATAR);
            user.setGroup(group);
            user.setPosition(position);
            userDao.create(user);
            resp.sendRedirect("/users");
        } catch (Exception e) {
            req.setAttribute("surname", surname);
            req.setAttribute("firstName", firstName);
            req.setAttribute("middleName", middleName);
            req.setAttribute("phone", phone);
            req.setAttribute("position", positionId);
            req.setAttribute("group", groupId);
            req.setAttribute("userError", e.getMessage());
            Dispatcher.dispatch(req, resp, "users_add");
        }
    }
}
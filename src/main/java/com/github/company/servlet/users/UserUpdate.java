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
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(name = "Users update", urlPatterns = "/users/update")
public class UserUpdate extends HttpServlet {

    private UserDao userDao = DaoService.getInstance().getUserDao();
    private PositionDao positionDao = DaoService.getInstance().getPositionDao();
    private GroupDao groupDao = DaoService.getInstance().getGroupDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        if (query != null && query.matches("^id=\\d+$")) {
            long id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("positions", positionDao.getAll());
            req.setAttribute("groups", groupDao.getAll());
            req.setAttribute("user", userDao.get(id));
            Dispatcher.dispatch(req, resp, "users_update");
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = new User();
        Position position = new Position();
        Group group = new Group();
        try {
            position.setId(Long.parseLong(req.getParameter("position")));
            group.setId(Long.parseLong(req.getParameter("group")));
            user.setId(Long.parseLong(req.getParameter("id")));
            user.setSurname(req.getParameter("surname"));
            user.setFirstName(req.getParameter("firstName"));
            user.setMiddleName(req.getParameter("middleName"));
            user.setPhone(Long.parseLong(req.getParameter("phone")));
            user.setPosition(position);
            user.setGroup(group);
            if (userDao.update(user) == 0)
                throw new SQLIntegrityConstraintViolationException("Логин зянят");
            resp.sendRedirect("/users");
        } catch (Exception e) {
            req.setAttribute("userError", e.getLocalizedMessage());
            req.setAttribute("user", userDao.get(user.getId()));
            req.setAttribute("position", positionDao.getAll());
            req.setAttribute("group", groupDao.getAll());
            Dispatcher.dispatch(req, resp, "users_update");
        }
    }
}
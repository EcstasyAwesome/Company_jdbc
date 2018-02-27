package com.company.servlet;

import com.company.dao.entity.Group;
import com.company.dao.entity.Position;
import com.company.dao.entity.User;
import com.company.dao.factory.DaoFactory;
import com.company.dao.model.GroupDao;
import com.company.dao.model.PositionDao;
import com.company.dao.model.UserDao;
import com.company.filter.Dispatcher;
import com.company.util.AvatarUtil;
import com.company.util.HibernateUtil;
import com.company.util.LinkManager;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet(
        name = "Users",
        description = "Users servlet",
        urlPatterns = {Users.MAIN, Users.ADD, Users.UPDATE, Users.DELETE}
)
public class Users extends HttpServlet {

    public static final String MAIN = "/users";
    public static final String ADD = "/users/add";
    public static final String UPDATE = "/users/update";
    public static final String DELETE = "/users/delete";

    private final String LOGIN = "login";
    private final String PASSWORD = "password";
    private final String ID = "id";
    private final String SURNAME = "surname";
    private final String FIRST_NAME = "firstName";
    private final String MIDDLE_NAME = "middleName";
    private final String PHONE = "phone";
    private final String POSITION = "position";
    private final String GROUP = "group";
    private final String USER = "user";
    private final String MESSAGE = "userError";

    private LinkManager linkManager = LinkManager.getInstance();
    private Map<String, LinkManager.Page> list = linkManager.getList();
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.getUserDao();
    private PositionDao positionDao = daoFactory.getPositionDao();
    private GroupDao groupDao = daoFactory.getGroupDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String link = Dispatcher.getLink();
        String query;
        if (link.equals(MAIN)) {
            int currentPage = 1;
            int recordsOnPage = 10;
            query = req.getQueryString();
            if (query != null && query.matches("^page=\\d+$")) {
                currentPage = Integer.parseInt(req.getParameter("page"));
                int availablePages = userDao.countPages(recordsOnPage);
                if (currentPage <= availablePages) {
                    req.setAttribute("availablePages", availablePages);
                    req.setAttribute("currentPage", currentPage);
                    req.setAttribute("users", userDao.getPage(currentPage, recordsOnPage));
                    req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
                } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                req.setAttribute("availablePages", userDao.countPages(recordsOnPage));
                req.setAttribute("currentPage", currentPage);
                req.setAttribute("users", userDao.getPage(currentPage, recordsOnPage));
                req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
            }
        } else if (link.equals(UPDATE) | link.equals(DELETE)) {
            query = req.getQueryString();
            if (query != null && query.matches("^id=\\d+$")) {
                long id = Integer.parseInt(req.getParameter("id"));
                if (link.equals(UPDATE)) {
                    req.setAttribute("positions", positionDao.getAll());
                    req.setAttribute("groups", groupDao.getAll());
                }
                req.setAttribute(USER, userDao.get(id));
                req.getRequestDispatcher(list.get(link).getPath()).forward(req, resp);
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else if (link.equals(ADD)) {
            req.setAttribute("positions", positionDao.getAll());
            req.setAttribute("groups", groupDao.getAll());
            req.getRequestDispatcher(list.get(link).getPath()).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String method = req.getParameter("method");
        if (method != null) {
            switch (method) {
                case "ADD":
                    addUser(req, resp);
                    break;
                case "UPDATE":
                    updateUser(req, resp);
                    break;
                case "DELETE":
                    deleteUser(req, resp);
                    break;
            }
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException {
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
            positionId = Long.parseLong(request.getParameter(POSITION));
            groupId = Long.parseLong(request.getParameter(GROUP));
            position.setId(positionId);
            group.setId(groupId);
            user.setLogin(request.getParameter(LOGIN));
            user.setPassword(request.getParameter(PASSWORD));
            user.setSurname(surname = request.getParameter(SURNAME));
            user.setFirstName(firstName = request.getParameter(FIRST_NAME));
            user.setMiddleName(middleName = request.getParameter(MIDDLE_NAME));
            user.setPhone(phone = Long.parseLong(request.getParameter(PHONE)));
            user.setRegisterDate(new Date());
            user.setAvatar(AvatarUtil.DEFAULT_AVATAR);
            user.setGroup(group);
            user.setPosition(position);
            userDao.create(user);
            response.sendRedirect(MAIN);
        } catch (Exception e) {
            request.setAttribute(SURNAME, surname);
            request.setAttribute(FIRST_NAME, firstName);
            request.setAttribute(MIDDLE_NAME, middleName);
            request.setAttribute(PHONE, phone);
            request.setAttribute(POSITION, positionId);
            request.setAttribute(GROUP, groupId);
            request.setAttribute(MESSAGE, e.getMessage());
            request.getRequestDispatcher(list.get(ADD).getPath()).forward(request, response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException {
        User user = new User();
        Position position = new Position();
        Group group = new Group();
        long userId = 0;
        long groupId = 0;
        long positionId = 0;
        try {
            position.setId(positionId = Long.parseLong(request.getParameter(POSITION)));
            group.setId(groupId = Long.parseLong(request.getParameter(GROUP)));
            user.setId(userId = Long.parseLong(request.getParameter(ID)));
            user.setSurname(request.getParameter(SURNAME));
            user.setFirstName(request.getParameter(FIRST_NAME));
            user.setMiddleName(request.getParameter(MIDDLE_NAME));
            user.setPhone(Long.parseLong(request.getParameter(PHONE)));
            user.setPosition(position);
            user.setGroup(group);
            userDao.update(user);
            response.sendRedirect(MAIN);
        } catch (Exception e) {
            request.setAttribute(MESSAGE, e.getLocalizedMessage());
            request.setAttribute(USER, userDao.get(userId));
            request.setAttribute(POSITION, positionDao.get(positionId));
            request.setAttribute(GROUP, groupDao.get(groupId));
            request.getRequestDispatcher(list.get(UPDATE).getPath()).forward(request, response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            AvatarUtil avatarUtil = new AvatarUtil(request);
            userDao.delete(Long.parseLong(request.getParameter(ID)));
            avatarUtil.deleteFromStorage(request.getParameter("avatar"));
            response.sendRedirect(MAIN);
        } catch (Exception e) {
            response.sendRedirect(Dispatcher.getLink() + "?" + request.getQueryString());
        }
    }
}

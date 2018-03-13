package com.company.servlet.users;

import com.company.dao.DaoService;
import com.company.dao.entity.Group;
import com.company.dao.entity.Position;
import com.company.dao.entity.User;
import com.company.dao.model.GroupDao;
import com.company.dao.model.PositionDao;
import com.company.dao.model.UserDao;
import com.company.filter.Dispatcher;
import com.company.util.AvatarUtil;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet(
        name = "Users create",
        urlPatterns = UserCreate.ADD
)
public class UserCreate extends HttpServlet {

    public static final String ADD = "/users/add";

    private Map<String, LinkManager.Page> list = LinkManager.getInstance().getList();
    private UserDao userDao = DaoService.getInstance().getUserDao();
    private PositionDao positionDao = DaoService.getInstance().getPositionDao();
    private GroupDao groupDao = DaoService.getInstance().getGroupDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("positions", positionDao.getAll());
        req.setAttribute("groups", groupDao.getAll());
        req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
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
            user.setRegisterDate(new Date());
            user.setAvatar(AvatarUtil.DEFAULT_AVATAR);
            user.setGroup(group);
            user.setPosition(position);
            userDao.create(user);
            resp.sendRedirect(UserSearch.MAIN);
        } catch (Exception e) {
            req.setAttribute("surname", surname);
            req.setAttribute("firstName", firstName);
            req.setAttribute("middleName", middleName);
            req.setAttribute("phone", phone);
            req.setAttribute("position", positionId);
            req.setAttribute("group", groupId);
            req.setAttribute("userError", e.getMessage());
            req.getRequestDispatcher(list.get(ADD).getPath()).forward(req, resp);
        }
    }
}
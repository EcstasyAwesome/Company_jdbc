package com.company.servlet.positions;

import com.company.dao.DaoService;
import com.company.dao.model.PositionDao;
import com.company.filter.Dispatcher;
import com.company.dao.entity.Position;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Positions create", urlPatterns = PositionCreate.ADD)
public class PositionCreate extends HttpServlet {

    public static final String ADD = "/positions/add";

    private Map<String, LinkManager.Page> list = LinkManager.getInstance().getList();
    private PositionDao positionDao = DaoService.getInstance().getPositionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String description = null;
        Position position = new Position();
        try {
            String name = req.getParameter("name");
            description = req.getParameter("description");
            position.setName(name);
            position.setDescription(description);
            positionDao.create(position);
            resp.sendRedirect(PositionSearch.MAIN);
        } catch (Exception e) {
            req.setAttribute("positionError", e.getLocalizedMessage());
            req.setAttribute("description", description);
            req.getRequestDispatcher(list.get(ADD).getPath()).forward(req, resp);
        }
    }
}
package com.github.company.servlet.positions;

import com.github.company.dao.DaoService;
import com.github.company.dao.model.PositionDao;
import com.github.company.dao.entity.Position;
import com.github.company.util.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Positions create", urlPatterns = "/positions/add")
public class PositionCreate extends HttpServlet {

    private PositionDao positionDao = DaoService.getInstance().getPositionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher.dispatch(req, resp, "positions_add");
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
            resp.sendRedirect("/positions");
        } catch (Exception e) {
            req.setAttribute("positionError", e.getLocalizedMessage());
            req.setAttribute("description", description);
            Dispatcher.dispatch(req, resp, "position_add");
        }
    }
}
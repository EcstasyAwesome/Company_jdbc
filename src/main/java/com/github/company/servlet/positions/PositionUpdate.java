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
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(name = "Positions update", urlPatterns = "/positions/update")

public class PositionUpdate extends HttpServlet {

    private PositionDao positionDao = DaoService.getInstance().getPositionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        if (query != null && query.matches("^id=\\d+$")) {
            long id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("position", positionDao.get(id));
            Dispatcher.dispatch(req, resp, "positions_update");
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Position position = new Position();
        try {
            position.setId(Long.parseLong(req.getParameter("id")));
            position.setName(req.getParameter("name"));
            position.setDescription(req.getParameter("description"));
            if (positionDao.update(position) == 0)
                throw new SQLIntegrityConstraintViolationException("Должность уже существует");
            resp.sendRedirect("/positions");
        } catch (Exception e) {
            req.setAttribute("positionError", e.getLocalizedMessage());
            req.setAttribute("position", positionDao.get(position.getId()));
            Dispatcher.dispatch(req, resp, "positions_update");
        }
    }
}
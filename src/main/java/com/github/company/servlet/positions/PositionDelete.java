package com.github.company.servlet.positions;

import com.github.company.dao.DaoService;
import com.github.company.dao.model.PositionDao;
import com.github.company.util.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Positions delete", urlPatterns = "/positions/delete")
public class PositionDelete extends HttpServlet {

    private PositionDao positionDao = DaoService.getInstance().getPositionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        if (query != null && query.matches("^id=\\d+$")) {
            long id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("position", positionDao.get(id));
            Dispatcher.dispatch(req, resp, "positions_delete");
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        positionDao.delete(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect("/positions");
    }
}
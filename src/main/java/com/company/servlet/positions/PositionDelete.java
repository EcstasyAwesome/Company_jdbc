package com.company.servlet.positions;

import com.company.dao.DaoService;
import com.company.dao.model.PositionDao;
import com.company.filter.Dispatcher;
import com.company.util.LinkManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(
        name = "Positions delete",
        urlPatterns = PositionDelete.DELETE
)

public class PositionDelete extends HttpServlet {

    public static final String DELETE = "/positions/delete";

    private Map<String, LinkManager.Page> list = LinkManager.getInstance().getList();
    private PositionDao positionDao = DaoService.getInstance().getPositionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        if (query != null && query.matches("^id=\\d+$")) {
            long id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("position", positionDao.get(id));
            req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            positionDao.delete(Long.parseLong(req.getParameter("id")));
            resp.sendRedirect(PositionSearch.MAIN);
        } catch (Exception e) {
            resp.sendRedirect(Dispatcher.getLink() + "?" + req.getQueryString());
        }
    }
}
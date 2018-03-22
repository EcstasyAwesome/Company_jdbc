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

@WebServlet(name = "Positions search", urlPatterns = "/positions")
public class PositionSearch extends HttpServlet {

    private PositionDao positionDao = DaoService.getInstance().getPositionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentPage = 1;
        int recordsOnPage = 5;
        String query = req.getQueryString();
        if (query != null && query.matches("^page=\\d+$")) {
            currentPage = Integer.parseInt(req.getParameter("page"));
            int availablePages = positionDao.countPages(recordsOnPage);
            if (currentPage <= availablePages) {
                req.setAttribute("availablePages", availablePages);
                req.setAttribute("currentPage", currentPage);
                req.setAttribute("positions", positionDao.getPage(currentPage, recordsOnPage));
                Dispatcher.dispatch(req, resp, "positions_search");
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            req.setAttribute("availablePages", positionDao.countPages(recordsOnPage));
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("positions", positionDao.getPage(currentPage, recordsOnPage));
            Dispatcher.dispatch(req, resp, "positions_search");
        }
    }
}
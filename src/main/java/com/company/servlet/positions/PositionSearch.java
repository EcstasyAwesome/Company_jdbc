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

@WebServlet(name = "Positions search", urlPatterns = PositionSearch.MAIN)
public class PositionSearch extends HttpServlet {

    public static final String MAIN = "/positions";

    private Map<String, LinkManager.Page> list = LinkManager.getInstance().getList();
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
                req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            req.setAttribute("availablePages", positionDao.countPages(recordsOnPage));
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("positions", positionDao.getPage(currentPage, recordsOnPage));
            req.getRequestDispatcher(list.get(Dispatcher.getLink()).getPath()).forward(req, resp);
        }
    }
}
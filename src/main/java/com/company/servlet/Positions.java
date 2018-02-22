package com.company.servlet;

import com.company.dao.factory.DaoFactory;
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

@WebServlet(
        name = "Positions",
        description = "Positions servlet",
        urlPatterns = {Positions.MAIN, Positions.ADD, Positions.UPDATE, Positions.DELETE}
)

public class Positions extends HttpServlet {

    public static final String MAIN = "/positions";
    public static final String ADD = "/positions/add";
    public static final String UPDATE = "/positions/update";
    public static final String DELETE = "/positions/delete";

    private final String ID = "id";
    private final String NAME = "name";
    private final String DESCRIPTION = "description";
    private final String ATTRIBUTE = "position";
    private final String MESSAGE = "positionError";

    private LinkManager linkManager = LinkManager.getInstance();
    private Map<String, LinkManager.Page> list = linkManager.getList();
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private PositionDao positionDao = daoFactory.getPositionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String link = Dispatcher.getLink();
        if (link.equals(MAIN)) {
            req.setAttribute("positions", positionDao.getAll());
            req.getRequestDispatcher(list.get(link).getPath()).forward(req, resp);
        } else if (link.equals(UPDATE) | link.equals(DELETE)) {
            String query = req.getQueryString();
            if (query != null && query.matches("id=\\d+")) {
                long id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute(ATTRIBUTE, positionDao.get(id));
                req.getRequestDispatcher(list.get(link).getPath()).forward(req, resp);
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else req.getRequestDispatcher(list.get(link).getPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String method = req.getParameter("method");
        if (method != null) {
            switch (method) {
                case "ADD":
                    addPosition(req, resp);
                    break;
                case "UPDATE":
                    updatePosition(req, resp);
                    break;
                case "DELETE":
                    deletePosition(req, resp);
                    break;
            }
        }
    }

    private void addPosition(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String description = null;
        Position position = new Position();
        try {
            String name = request.getParameter(NAME);
            description = request.getParameter(DESCRIPTION);
            position.setName(name);
            position.setDescription(description);
            positionDao.create(position);
            response.sendRedirect(MAIN);
        } catch (Exception e) {
            request.setAttribute(MESSAGE, e.getLocalizedMessage());
            request.setAttribute(DESCRIPTION, description);
            request.getRequestDispatcher(list.get(ADD).getPath()).forward(request, response);
        }
    }

    private void updatePosition(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Position position = new Position();
        long id = 0;
        try {
            id = Integer.parseInt(request.getParameter(ID));
            String name = request.getParameter(NAME);
            String description = request.getParameter(DESCRIPTION);
            position.setId(id);
            position.setName(name);
            position.setDescription(description);
            positionDao.update(position);
            response.sendRedirect(MAIN);
        } catch (Exception e) {
            request.setAttribute(MESSAGE, e.getLocalizedMessage());
            request.setAttribute(ATTRIBUTE, positionDao.get(id));
            request.getRequestDispatcher(list.get(UPDATE).getPath()).forward(request, response);
        }
    }

    private void deletePosition(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long id = 0;
        try {
            id = Integer.parseInt(request.getParameter(ID));
            positionDao.delete(id);
            response.sendRedirect(MAIN);
        } catch (Exception e) {
            request.setAttribute(MESSAGE, e.getMessage());
            request.setAttribute(ATTRIBUTE, positionDao.get(id));
            request.getRequestDispatcher(list.get(DELETE).getPath()).forward(request, response);
        }
    }
}

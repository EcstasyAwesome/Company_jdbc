package com.company.servlet;

import com.company.filter.Dispatcher;
import com.company.dao.entity.Position;
import com.company.util.HibernateUtil;
import com.company.util.LinkManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "Positions",
        description = "Positions servlet",
        urlPatterns = "/positions/*"
)

public class Positions extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null) {
            req.setAttribute("positions", getPositions());
            req.getRequestDispatcher(LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, resp);
        } else if (req.getQueryString() != null) {
            if (req.getQueryString().matches("id=\\d+")) {
                req.setAttribute("position", getPosition(req));
                req.getRequestDispatcher(LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, resp);
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else
            req.getRequestDispatcher(LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, resp);
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

    private Position getPosition(HttpServletRequest request) {
        Session session = HibernateUtil.getSession();
        Position result;
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            session.beginTransaction();
            result = session.get(Position.class, id);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
        return result;
    }

    private List getPositions() {
        Session session = HibernateUtil.getSession();
        List result;
        try {
            session.beginTransaction();
            result = session.createQuery("from Position").getResultList();
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
        return result;
    }

    private void addPosition(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Position where name = :name");
            query.setParameter("name", name);
            if (!query.list().isEmpty()) throw new HibernateException("Duplicate entry");
            Position position = new Position();
            session.save(position);
            session.getTransaction().commit();
            response.sendRedirect("/position");
        } catch (HibernateException e) {
            request.setAttribute("positionError", "Должность '" + name + "' уже существует");
            request.setAttribute("position_description", description);
            request.getRequestDispatcher(LinkManager.getInstance().getList().get("/position/add").getPath()).forward(request, response);
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    private void updatePosition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Position position = session.get(Position.class, id);
            position.setName(name);
            position.setDescription(description);
            session.update(position);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
        response.sendRedirect("/position");
    }

    private void deletePosition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Position position = session.get(Position.class, id);
            session.delete(position);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
        response.sendRedirect("/position");
    }
}

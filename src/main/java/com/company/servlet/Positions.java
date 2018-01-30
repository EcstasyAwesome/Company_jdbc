package com.company.servlet;

import com.company.filter.Dispatcher;
import com.company.pojo.Position;
import com.company.util.HibernateUtil;
import com.company.util.LinkManager;
import org.hibernate.Session;

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
        if (req.getQueryString() != null) req.setAttribute("position", getPosition(req));
        else req.setAttribute("positions", getPositions());
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
        int id = Integer.parseInt(request.getParameter("id"));
        Position result = null;
        try {
            session.beginTransaction();
            result = session.get(Position.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    private List getPositions() {
        Session session = HibernateUtil.getSession();
        List result = null;
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Position").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    private void addPosition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("position_name");
        String description = request.getParameter("position_description");
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Position position = new Position(name, description);
            session.save(position);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        response.sendRedirect(LinkManager.POSITIONS_LINK);
    }

    private void updatePosition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("position_id"));
        String name = request.getParameter("position_name");
        String description = request.getParameter("position_description");
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Position position = session.get(Position.class, id);
            position.setPositionName(name);
            position.setPositionDescription(description);
            session.update(position);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        response.sendRedirect(LinkManager.POSITIONS_LINK);
    }

    private void deletePosition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("position_id"));
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Position position = session.get(Position.class, id);
            session.delete(position);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        response.sendRedirect(LinkManager.POSITIONS_LINK);
    }
}

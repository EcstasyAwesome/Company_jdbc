//
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.sql.Connection;
//
//public class UsersServlet extends HttpServlet {
//
//    private Connection connection;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String key = req.getParameter("key");
//        String value = req.getParameter("value");
//        req.setAttribute("users", new UserDAO(connection).getUsersList(key, value));
//        req.setAttribute("positions", new PositionDAO(connection).getPositionsList(key, value));
//        if (req.getQueryString() == null) {
//            req.setAttribute("button", false);
//        } else req.setAttribute("button", true);
//        req.getRequestDispatcher(com.company.util.LinkManager.getInstance().getList().get(Dispatcher.getLink()).getPath()).forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String method = req.getParameter("method");
//        if (method != null) {
//            switch (method) {
//                case "PUT":
//                    doPut(req, resp);
//                    break;
//                case "DELETE":
//                    doDelete(req, resp);
//                    break;
//                case "LOGOUT":
//                    HttpSession session = req.getSession(false);
//                    System.out.println("-> Выполнен выход: " + session.getAttribute("sessionUser"));
//                    session.invalidate();
//                    resp.sendRedirect("/company/login");
//                    break;
//            }
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String form = req.getParameter("form");
//        int id;
//        String surname;
//        String firstName;
//        String secondName;
//        long phoneNumber;
//        int position;
//        String login;
//        String password;
//        boolean admin;
//        String name;
//        String description;
//        if (form != null) {
//            switch (form) {
//                case "addUser":
//                    surname = req.getParameter("user_surname");
//                    firstName = req.getParameter("user_firstName");
//                    secondName = req.getParameter("user_secondName");
//                    phoneNumber = Long.parseLong(req.getParameter("user_phoneNumber"));
//                    String positionId = req.getParameter("position_id");
//                    position = positionId != null ? Integer.parseInt(positionId) : 5;
//                    login = req.getParameter("user_login");
//                    password = req.getParameter("user_password");
//                    String isAdmin = req.getParameter("user_isAdmin");
//                    admin = isAdmin != null && Boolean.parseBoolean(isAdmin);
//                    new UserDAO(connection).addUser(surname, firstName, secondName, phoneNumber, position, login, password, admin);
//                    resp.sendRedirect("/company/users");
//                    break;
//                case "updateUser":
//                    surname = req.getParameter("user_surname");
//                    firstName = req.getParameter("user_firstName");
//                    secondName = req.getParameter("user_secondName");
//                    phoneNumber = Long.parseLong(req.getParameter("user_phoneNumber"));
//                    position = Integer.parseInt(req.getParameter("position_id"));
//                    id = Integer.parseInt(req.getParameter("user_id"));
//                    password = req.getParameter("user_password");
//                    admin = Boolean.parseBoolean(req.getParameter("user_isAdmin"));
//                    new UserDAO(connection).updateUser(id, surname, firstName, secondName, phoneNumber, position, password, admin);
//                    resp.sendRedirect("/company/users?key=user_id&value=" + id);
//                    break;
//                case "addPosition":
//                    name = req.getParameter("position_name");
//                    description = req.getParameter("position_description");
//                    new PositionDAO(connection).addPosition(name, description);
//                    resp.sendRedirect("/company/positions");
//                    break;
//                case "updatePosition":
//                    id = Integer.parseInt(req.getParameter("position_id"));
//                    name = req.getParameter("position_name");
//                    description = req.getParameter("position_description");
//                    new PositionDAO(connection).updatePosition(id, name, description);
//                    resp.sendRedirect("/company/positions");
//                    break;
//            }
//        }
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String form = req.getParameter("form");
//        int id;
//        if (form != null) {
//            switch (form) {
//                case "deleteUser":
//                    id = Integer.parseInt(req.getParameter("user_id"));
//                    new UserDAO(connection).deleteUser(id);
//                    resp.sendRedirect("/company/users");
//                    break;
//                case "deletePosition":
//                    id = Integer.parseInt(req.getParameter("position_id"));
//                    new PositionDAO(connection).deletePosition(id);
//                    resp.sendRedirect("/company/positions");
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public void init() {
//        System.out.println("-> Сервлет запущен");
//        connection = DBConnection.getInstance().getConnection();
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("-> Сервлет остановлен");
//        DBConnection.getInstance().closeConnection();
//    }
//
//}

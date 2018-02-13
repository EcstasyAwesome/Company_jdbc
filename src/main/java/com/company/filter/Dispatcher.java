package com.company.filter;

import com.company.util.LinkManager;
import com.company.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebFilter(
        filterName = "Dispatcher",
        urlPatterns = "/*"
)
public class Dispatcher implements Filter {

    private LinkManager linkManager = LinkManager.getInstance();
    private Map<String, LinkManager.Page> list = linkManager.getList();
    private static String link;

    /**
     * @return a String containing the value of the request link
     * @see #getLink()
     */

    public static String getLink() {
        return link;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession httpSession = req.getSession(true);
        User user = (User) httpSession.getAttribute("sessionUser");
        link = processLink(req);
        if (link.equals(req.getRequestURI())) { // if link valid
            if (linkManager.isResources(link)) chain.doFilter(request, response); // its resource, goes next
            else if (list.containsKey(link)) { // if link exist
                int status = user != null ? user.getUserStatus() : LinkManager.ACCESS_ALL; // get status
                if (status >= list.get(link).getAccess()) chain.doFilter(request, response); // have access
                else if (status != LinkManager.ACCESS_ALL) resp.sendError(HttpServletResponse.SC_FORBIDDEN); //ERROR 403
                else resp.sendError(HttpServletResponse.SC_UNAUTHORIZED); //ERROR 401
            } else resp.sendError(HttpServletResponse.SC_NOT_FOUND); //ERROR 404
        } else resp.sendRedirect(link); // goes on correct link
    }

    /**
     * Example:
     * //something/test// or /something//test -> /something/test
     *
     * @return a String containing the value of the correct request link
     * @see #processLink(HttpServletRequest)
     */
    private String processLink(HttpServletRequest request) {
        StringBuilder link;
        String[] temp;
        String tempLink = request.getRequestURI();
        if (tempLink.length() > 1) {
            link = new StringBuilder();
            temp = tempLink.split("[/]+");
            for (String x : temp) {
                if (!x.isEmpty()) {
                    link.append("/").append(x.replaceAll("[/]", ""));
                }
            }
            return link.toString().isEmpty() ? "/" : link.toString();
        }
        return tempLink;
    }
}

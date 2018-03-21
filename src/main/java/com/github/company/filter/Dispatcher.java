package com.github.company.filter;

import com.github.company.util.LinkManager;
import com.github.company.dao.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "Dispatcher", urlPatterns = "/*")
public class Dispatcher implements Filter {

    private Map<String, LinkManager.Page> list = LinkManager.getInstance().getList();
    private static String link;

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
            if (isResources(link)) chain.doFilter(request, response); // its resource, goes next
            else {
                int group = user != null ? (int) user.getGroup().getId() : LinkManager.Page.GUEST;
                int pageAccess = list.get(link).getAccess();
                if (group >= pageAccess) chain.doFilter(request, response); // have access
                else if (group > LinkManager.Page.GUEST) resp.sendError(HttpServletResponse.SC_FORBIDDEN); //ERROR 403
                else resp.sendError(HttpServletResponse.SC_UNAUTHORIZED); //ERROR 401
            }
        } else resp.sendRedirect(link); // goes on correct link
    }

    /**
     * @param link RequestURI
     * @return returns true if requestURI is a resource link
     * @see #isResources(String)
     */

    private boolean isResources(@NotNull String link) {
        String path1 = "/resources/";
        String path2 = "/storage/";
        return link.startsWith(path1) | link.startsWith(path2);
    }

    /**
     * Example:
     * //something/test// or /something//test -> /something/test
     *
     * @return a String containing the value of the correct request link
     * @see #processLink(HttpServletRequest)
     */

    private String processLink(@NotNull HttpServletRequest request) {
        StringBuilder link;
        String[] temp;
        String tempLink = request.getRequestURI();
        if (tempLink.length() > 1) {
            link = new StringBuilder();
            temp = tempLink.split("[/]+");
            for (String x : temp) {
                if (!x.isEmpty()) {
                    link.append("/").append(x.replaceAll("/", ""));
                }
            }
            return link.toString().isEmpty() ? "/" : link.toString();
        }
        return tempLink;
    }
}
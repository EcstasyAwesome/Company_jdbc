package com.company.filter;

import com.company.util.LinkManager;
import com.company.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        filterName = "Dispatcher",
        description = "link dispatcher",
        urlPatterns = "/*"
)
public class Dispatcher implements Filter {

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
        link = req.getRequestURI();
        if (link.endsWith("/") & link.length() > 1) {
            link = link.substring(0, link.length() - 1); // get rid of '/' at the end
            resp.sendRedirect(link); // goes on correct link
        } else if (link.startsWith(LinkManager.RESOURCES_FOLDER))
            chain.doFilter(request, response); // its resource, goes next
        else if (link.equals(LinkManager.AUTHORIZATION_LINK))
            chain.doFilter(request, response); // goes to authorization servlet
        else if (LinkManager.getInstance().getList().containsKey(link)) { // if link valid
            if (user != null) { // session user
                if (user.getUserIsAdmin() || LinkManager.getInstance().getList().get(link).getAccess()) { // if have access
                    httpSession.setAttribute("previousPage", link); // for button 'previous page'
                    chain.doFilter(request, response); // goes next
                } else req.getRequestDispatcher(LinkManager.ACCESS_PAGE).forward(req, resp); // no entry
            } else resp.sendRedirect(LinkManager.AUTHORIZATION_LINK); // redirect to authorization page
        } else if (link.equals("/")) chain.doFilter(req, resp); // if request url is 'http://localhost:8080/'
        else req.getRequestDispatcher(LinkManager.NOT_FOUND_PAGE).forward(req, resp); // wrong link, get 404
    }
}

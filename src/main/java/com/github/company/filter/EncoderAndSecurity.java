package com.github.company.filter;

import com.github.company.dao.entity.User;
import com.github.company.security.Security;
import com.github.company.security.SecurityImpl;
import com.github.company.util.PropUtil;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.github.company.security.SecurityImpl.*;

@WebFilter(filterName = "Encoder and Security", urlPatterns = "/*")
public class EncoderAndSecurity implements Filter {

    private static final Logger LOGGER = Logger.getLogger(EncoderAndSecurity.class);

    private Security security = SecurityImpl.getInstance();
    private final String encoding = PropUtil.getProperty("app.encoding");

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Set encoding - '" + encoding + "'");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession httpSession = req.getSession(true);
        User user = (User) httpSession.getAttribute("sessionUser");
        String link = processLink(req.getRequestURI());
        if (link.equals(req.getRequestURI())) {
            switch (security.verify(user, link)) {
                case SUCCESS:
                    request.setCharacterEncoding(encoding);
                    response.setCharacterEncoding(encoding);
                    chain.doFilter(req, resp);
                    break;
                case FORBIDDEN:
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    break;
                case UNAUTHORIZED:
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    break;
                case NOT_FOUND:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } else resp.sendRedirect(link);
    }

    /**
     * Example:
     * //something/test// or /something//test -> /something/test
     *
     * @return a String containing the value of the correct request link
     * @see #processLink(String)
     */

    private String processLink(@NotNull String uri) {
        StringBuilder link;
        String[] temp;
        if (uri.length() > 1) {
            link = new StringBuilder();
            temp = uri.split("/+");
            for (String x : temp) {
                if (!x.isEmpty()) {
                    link.append("/").append(x.replaceAll("/", ""));
                }
            }
            return link.toString().isEmpty() ? "/" : link.toString();
        }
        return uri;
    }
}
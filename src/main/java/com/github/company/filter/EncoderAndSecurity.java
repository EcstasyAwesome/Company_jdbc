package com.github.company.filter;

import com.github.company.dao.entity.User;
import com.github.company.security.Security;
import com.github.company.util.PropertiesReader;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static com.github.company.security.Security.FORBIDDEN;
import static com.github.company.security.Security.SUCCESS;
import static com.github.company.security.Security.UNAUTHORIZED;

@WebFilter(filterName = "Encoder and Security", urlPatterns = "/*")
public class EncoderAndSecurity implements Filter {

    private static final Logger LOGGER = Logger.getLogger(EncoderAndSecurity.class);

    private Security security = Security.getInstance();
    private final String encoding = PropertiesReader.getProperty("encoding");

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
        String link = processLink(req);
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
            }
        } else resp.sendRedirect(link);
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
package com.github.company.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import static com.github.company.listener.Initializer.ENCODING;

@WebFilter(filterName = "Encoder", urlPatterns = "/*")
public class Encoder implements Filter {

    private static final Logger LOGGER = Logger.getLogger(Encoder.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Set encoding - '" + ENCODING + "'");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);
        response.setContentType("text/html; charset=" + ENCODING);
        chain.doFilter(request, response);
    }
}
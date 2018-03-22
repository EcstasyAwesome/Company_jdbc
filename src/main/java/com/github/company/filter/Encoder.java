package com.github.company.filter;

import com.github.company.util.PropertiesReader;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "Encoder", urlPatterns = "/*")
public class Encoder implements Filter {

    private static final Logger LOGGER = Logger.getLogger(Encoder.class);
    private final String encoding = PropertiesReader.getProperty("encoding");

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Set encoding - '" + encoding + "'");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=" + encoding);
        chain.doFilter(request, response);
    }
}
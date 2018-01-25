package com.company.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(
        filterName = "Encoder",
        description = "sets the encoding for the resulting strings",
        initParams = @WebInitParam(name = "ENCODING_TYPE", value = "UTF-8"), // change the type here
        urlPatterns = "/*"
)
public class Encoder implements Filter {
    private String encoding = "UTF-8"; // default

    @Override
    public void init(FilterConfig filterConfig) {
        String encoding = filterConfig.getInitParameter("ENCODING_TYPE");
        if (encoding != null) {
            this.encoding = encoding;
        }
        System.out.println(String.format("-> [%s] encoding = %s", getClass().getSimpleName(), this.encoding));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}

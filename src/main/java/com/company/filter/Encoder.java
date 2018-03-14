package com.company.filter;

import com.company.util.SettingsXmlReader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(
        filterName = "Encoder",
        urlPatterns = "/*"
)
public class Encoder implements Filter {

    private static final String ENCODING;

    static {
        try {
            ENCODING = SettingsXmlReader.getValue("encoding");
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        System.out.println(String.format("-> [%s] ENCODING = %s", Encoder.class.getSimpleName(), ENCODING));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);
        response.setContentType("text/html; charset=" + ENCODING);
        chain.doFilter(request, response);
    }
}

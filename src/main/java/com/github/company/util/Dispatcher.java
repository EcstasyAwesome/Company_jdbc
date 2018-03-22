package com.github.company.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dispatcher {

    private Dispatcher(){
    }

    public static void dispatch(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        String prefix = "/WEB-INF/jsp/";
        String suffix = ".jsp";
        request.getRequestDispatcher(prefix + page + suffix).forward(request,response);
    }
}
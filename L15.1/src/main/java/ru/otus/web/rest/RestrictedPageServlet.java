package ru.otus.web.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Artem Gabbasov on 10.08.2017.
 * Сервлет, который перенаправляет пользователя на страницу авторизации, если он не авторизован
 */
public class RestrictedPageServlet extends HttpServlet {
    private static final String urlPattern = "/restricted";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirectPath = ServerContext.getRedirectPath(req.getRequestURI(), urlPattern);

        if (ServerContext.isAuthorized(req.getSession())) {
            // пользователь авторизован - пробрасываем его на запрашиваемую страницу
            resp.sendRedirect(redirectPath);
        } else {
            // пользователь не авторизован - выкидываем его на страницу авторизации
            resp.sendRedirect("/" + ServerContext.LOGIN_PAGE + redirectPath);
        }
    }
}

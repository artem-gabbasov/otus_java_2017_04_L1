package ru.otus.web.rest;

import ru.otus.web.ServerContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * Created by Artem Gabbasov on 19.07.2017.
 * Сервлет, обслуживающий админскую страничку
 */
public class AdminServlet extends HttpServlet {
    private static final String ACTION_LOGOUT = "logout";

    private final AdminDataProvider dataProvider;

    public AdminServlet() {
        dataProvider = ServerContext.getSpringBean("adminDataProvider", AdminDataProvider.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter(ACTION_LOGOUT) != null) {
            logout(req.getSession(), resp);
        } else {
            if (ServerContext.isAuthorized(req.getSession())) {
                dataProvider.loadPage(resp);
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    private void logout(HttpSession session, HttpServletResponse resp) throws IOException {
        ServerContext.setAuthorized(session, false);
        resp.sendRedirect(ServerContext.INDEX_PAGE);
    }
}

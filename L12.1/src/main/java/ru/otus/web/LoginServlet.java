package ru.otus.web;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.security.Credential;
import ru.otus.datasets.LoginDataSet;
import ru.otus.db.dbservices.DBServiceNamed;
import ru.otus.jpa.JPAException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Artem Gabbasov on 25.07.2017.
 * <p>
 */
@SuppressWarnings("FieldCanBeLocal")
public class LoginServlet extends HttpServlet {
    private final String LOGIN_PAGE_TEMPLATE = "login.html";

    private final String UNDEFINED_MESSAGE = "DBService is undefined";

    private final String PARAMETER_USERNAME = "username";
    private final String PARAMETER_PASSWORD = "password";

    private final DBServiceNamed dbService;

    /**
     * Флаг, указывающий, что попытка авторизации не удалась (для вывода красивого сообщения на странице авторизации)
     */
    private boolean isLastLoginIncorrect;

    public LoginServlet(DBServiceNamed dbService) {
        this.dbService = dbService;
        isLastLoginIncorrect = false;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("isLastLoginIncorrect", isLastLoginIncorrect);

        resp.getWriter().println(TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables));

        isLastLoginIncorrect = false;

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (dbService == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, UNDEFINED_MESSAGE);
        } else {
            try {
                processRequest(req.getParameter(PARAMETER_USERNAME), req.getParameter(PARAMETER_PASSWORD), req.getSession(), resp);
            } catch (SQLException | JPAException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
            }
        }
    }

    /**
     * Обрабатывает http-запрос на авторизацию
     * @param username                  имя пользователя из http-запроса
     * @param password                  пароль из http-запроса
     * @param session                   сессия для авторизации
     * @param resp                      http-ответ для реакции на запрос
     * @throws SQLException
     * @throws JPAException
     * @throws IOException
     * @throws ServletException
     */
    private void processRequest(String username, String password, HttpSession session, HttpServletResponse resp) throws SQLException, JPAException, IOException, ServletException {
        String passwordMD5 = Credential.MD5.digest(password);
        passwordMD5 = passwordMD5.startsWith("MD5:")?passwordMD5.substring("MD5:".length()):passwordMD5;
        processLoginData(username, passwordMD5, session, resp);
    }

    /**
     * Обрабатывает учётные данные, пришедшие в запросе
     * @param username              имя пользователя
     * @param passwordMD5           MD5-хеш пароля
     * @param session               сессия для авторизации
     * @param resp                  http-ответ для реакции на запрос
     * @throws JPAException
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     */
    private void processLoginData(String username, String passwordMD5, HttpSession session, HttpServletResponse resp) throws JPAException, SQLException, IOException, ServletException {
        LoginDataSet loginDataSet = dbService.loadByName(username, LoginDataSet.class);
        if (loginDataSet != null && loginDataSet.getPasswordMD5().equals(passwordMD5)) {
            Object authorizedSessions = ContextHandler.getCurrentContext().getAttribute(ServerManager.AUTHORIZED_SESSIONS);
            Set<HttpSession> authorizedSessionsSet;
            if (authorizedSessions == null) {
                authorizedSessionsSet = new HashSet<>();
            } else {
                authorizedSessionsSet = (Set<HttpSession>) authorizedSessions;
            }
            authorizedSessionsSet.add(session);
            ContextHandler.getCurrentContext().setAttribute(ServerManager.AUTHORIZED_SESSIONS, authorizedSessionsSet);

            String redirectPage;
            Object redirectPageObj = ContextHandler.getCurrentContext().getAttribute(ServerManager.REDIRECT_PAGE);
            if (redirectPageObj != null) {
                redirectPage = redirectPageObj.toString();
            } else {
                redirectPage = ServerManager.INDEX_PAGE;
            }

            resp.sendRedirect(redirectPage);
        } else {
            isLastLoginIncorrect = true;
            doGet(null, resp);
        }
    }
}
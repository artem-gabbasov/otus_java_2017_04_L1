package ru.otus.web;

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
import java.util.Map;

/**
 * Created by Artem Gabbasov on 25.07.2017.
 * <p>
 */
@SuppressWarnings("FieldCanBeLocal")
public class LoginServlet extends HttpServlet {
    private final String LOGIN_PAGE_TEMPLATE = "login.html";

    private final String UNDEFINED_MESSAGE = "DBService is undefined";
    private final String MULTIPLE_USERNAMES_MESSAGE = "Multiple username values found";
    private final String MULTIPLE_PASSWORDS_MESSAGE = "Multiple password values found";

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

    public LoginServlet() {
        this(ServerContext.getSpringBean("dbServiceNamed", DBServiceNamed.class));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("isLastLoginIncorrect", isLastLoginIncorrect);

        resp.getWriter().println(ServerContext.getSpringBean("templateProcessor", TemplateProcessor.class).getPage(LOGIN_PAGE_TEMPLATE, pageVariables));

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
                processRequest(req.getParameterMap(), req.getSession(), resp);
            } catch (SQLException | JPAException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
            }
        }
    }

    /**
     * Обрабатывает http-запрос на авторизацию
     * @param parameterMap              параметры http-запроса
     * @param session                   сессия для проверки авторизации
     * @param resp                      http-ответ для реакции на запрос
     * @throws SQLException
     * @throws JPAException
     * @throws IOException
     * @throws ServletException
     */
    private void processRequest(Map<String, String[]> parameterMap, HttpSession session, HttpServletResponse resp) throws SQLException, JPAException, IOException, ServletException {
        String[] values = parameterMap.get(PARAMETER_USERNAME);
        if (values.length == 1) { // у нас подразумевается единственное значение имени пользователя
            String username = values[0];
            values = parameterMap.get(PARAMETER_PASSWORD);
            if (values.length == 1) { // у нас подразумевается единственное значение пароля
                String passwordMD5 = Credential.MD5.digest(values[0]);

                passwordMD5 = passwordMD5.startsWith("MD5:")?passwordMD5.substring("MD5:".length()):passwordMD5;

                processLoginData(username, passwordMD5, session, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, MULTIPLE_PASSWORDS_MESSAGE);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, MULTIPLE_USERNAMES_MESSAGE);
        }
    }

    /**
     * Обрабатывает учётные данные, пришедшие в запросе
     * @param username              имя пользователя
     * @param passwordMD5           MD5-хеш пароля
     * @param session               сессия для проверки авторизации
     * @param resp                  http-ответ для реакции на запрос
     * @throws JPAException
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     */
    private void processLoginData(String username, String passwordMD5, HttpSession session, HttpServletResponse resp) throws JPAException, SQLException, IOException, ServletException {
        LoginDataSet loginDataSet = dbService.loadByName(username, LoginDataSet.class);
        if (loginDataSet != null && loginDataSet.getPasswordMD5().equals(passwordMD5)) {
            ServerContext.setAuthorized(session, true);
            resp.sendRedirect(ServerContext.getRedirectPage());
        } else {
            isLastLoginIncorrect = true;
            doGet(null, resp);
        }
    }
}
package ru.otus.web.rest;

import org.eclipse.jetty.util.security.Credential;
import ru.otus.orm.datasets.instances.LoginDataSet;
import ru.otus.db.dbservices.DBServiceNamed;
import ru.otus.orm.jpa.JPAException;
import ru.otus.web.ServerContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private static final String URL_PATTERN = "/login";

    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    private static final String UNDEFINED_MESSAGE = "DBService is undefined";
    private static final String MULTIPLE_USERNAMES_MESSAGE = "Multiple username values found";
    private static final String MULTIPLE_PASSWORDS_MESSAGE = "Multiple password values found";

    private static final String PARAMETER_USERNAME = "username";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_REDIRECTPAGE = "redirectPage";

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
        pageVariables.put("redirectPage", ServerContext.getRedirectPath(req.getRequestURI(), URL_PATTERN));

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
                processRequest(req, resp);
            } catch (SQLException | JPAException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
            }
        }
    }

    /**
     * Обрабатывает http-запрос на авторизацию
     * @param req                       http-запрос
     * @param resp                      http-ответ для реакции на запрос
     * @throws SQLException
     * @throws JPAException
     * @throws IOException
     * @throws ServletException
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws SQLException, JPAException, IOException, ServletException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        String[] values = parameterMap.get(PARAMETER_USERNAME);
        if (values.length == 1) { // у нас подразумевается единственное значение имени пользователя
            String username = values[0];
            values = parameterMap.get(PARAMETER_PASSWORD);
            if (values.length == 1) { // у нас подразумевается единственное значение пароля
                // в проде недопустимо было бы вообще передавать пароль в чистом виде, но о защите информации я особо не заботился
                String passwordMD5 = Credential.MD5.digest(values[0]);
                passwordMD5 = passwordMD5.startsWith("MD5:")?passwordMD5.substring("MD5:".length()):passwordMD5;

                String redirectPage = "";
                values = parameterMap.get(PARAMETER_REDIRECTPAGE);
                if (values.length == 1) {
                    redirectPage = values[0];
                }

                processLoginData(username, passwordMD5, redirectPage, req, resp);
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
     * @param redirectPage          страница для перенаправления в случае успешной авторизации
     * @param req                   http-запрос
     * @param resp                  http-ответ для реакции на запрос
     * @throws JPAException
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     */
    private void processLoginData(String username, String passwordMD5, String redirectPage, HttpServletRequest req, HttpServletResponse resp) throws JPAException, SQLException, IOException {
        LoginDataSet loginDataSet = dbService.loadByName(username, LoginDataSet.class);
        if (loginDataSet != null && loginDataSet.getPasswordMD5().equals(passwordMD5)) {
            ServerContext.setAuthorized(req.getSession(), true);
            resp.sendRedirect(redirectPage);
        } else {
            isLastLoginIncorrect = true;
            resp.sendRedirect(req.getRequestURI());
        }
    }
}
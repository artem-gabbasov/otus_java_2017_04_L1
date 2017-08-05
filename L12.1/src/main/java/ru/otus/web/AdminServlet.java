package ru.otus.web;

import org.eclipse.jetty.server.handler.ContextHandler;
import ru.otus.datasets.UserDataSet;
import ru.otus.db.dbservices.DBServiceCacheEngine;
import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.jpa.JPAException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Artem Gabbasov on 19.07.2017.
 * <p>
 */
public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    private static final String UNDEFINED_MESSAGE = "DBService or CacheEngine is undefined.";

    private static final String ACTION_LOGOUT = "logout";
    private static final String ACTION_USER_SAVE = "save";
    private static final String ACTION_USER_LOAD = "load";
    private static final String ACTION_CACHE_CLEAR = "clear";
    private static final String PARAMETER_USER_ID = "userID";

    private final Supplier<DBServiceCached> dbServiceSupplier;

    /**
     * @param dbServiceSupplier объект, который поставляет DBService, по которому требуется информация.
     *                          Использовал Supplier, а не просто ссылку на DBService, т.к. нам не важно,
     *                          за одним ли мы объектом следим или за разными - в общем случае мы можем их переключать
     */
    public AdminServlet(Supplier<DBServiceCached> dbServiceSupplier) {
        this.dbServiceSupplier = dbServiceSupplier;
    }

    /**
     * Проверяет, авторизован ли пользователь. Если не авторизован, выкидывает его на домашнюю страницу
     * @param session       текущая сессия для проверки авторизации
     * @param resp          http-ответ для перенаправления
     * @return              True, если пользователь авторизован. Иначе, false
     * @throws IOException  в случае проблемы при перенаправлении
     */
    private boolean checkAuthorization(HttpSession session, HttpServletResponse resp) throws IOException {
        Object authorizedSessions = ContextHandler.getCurrentContext().getAttribute(ServerManager.AUTHORIZED_SESSIONS);
        if (authorizedSessions != null && ((Set<HttpSession>) authorizedSessions).contains(session)) {
            return true;
        } else {
            ContextHandler.getCurrentContext().setAttribute(ServerManager.REDIRECT_PAGE, "admin");
            resp.sendRedirect(ServerManager.LOGIN_PAGE);
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter(ACTION_LOGOUT) != null) {
            logout(req.getSession(), resp);
        } else {
            if (checkAuthorization(req.getSession(), resp)) {
                showPage(resp);
            }
        }
    }

    private void logout(HttpSession session, HttpServletResponse resp) throws IOException {
        Object authorizedSessions = ContextHandler.getCurrentContext().getAttribute(ServerManager.AUTHORIZED_SESSIONS);
        if (authorizedSessions != null) {
            ((Set<HttpSession>) authorizedSessions).remove(session);
        }
        resp.sendRedirect(ServerManager.INDEX_PAGE);
    }

    /**
     * Отображает страницу с параметрами кеша и с управлением dbService'ом
     * @param resp              http-ответ (метод GET)
     * @throws IOException
     */
    private void showPage(HttpServletResponse resp) throws IOException {
        DBServiceCached dbService = dbServiceSupplier.get();
        if (checkObject(dbService, resp)) {
            DBServiceCacheEngine cacheEngine = dbService.getCacheEngine();
            if (checkObject(cacheEngine, resp)) {
                Map<String, Object> pageVariables = createPageVariablesMap(cacheEngine);

                resp.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }

    /**
     * Заполняет значения переменных для Template'а
     * @param cacheEngine   объект, информация по которому выводится на странице
     * @return              Map со значениями переменных для вывода на админскую страничку
     */
    private Map<String, Object> createPageVariablesMap(DBServiceCacheEngine cacheEngine) {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("maxElements", cacheEngine.getMaxElements());
        pageVariables.put("elementsCount", cacheEngine.getElementsCount());
        pageVariables.put("lifeTime", cacheEngine.getLifeTimeMs());
        pageVariables.put("idleTime", cacheEngine.getIdleTimeMs());
        pageVariables.put("hitCount", cacheEngine.getHitCount());
        pageVariables.put("missCount", cacheEngine.getMissCount());

        return pageVariables;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkAuthorization(req.getSession(), resp)) {
            DBServiceCached dbService = dbServiceSupplier.get();
            if (checkObject(dbService, resp)) {
                try {
                    dispatchParameters(dbService, req.getParameterMap());
                } catch (IllegalAccessException | SQLException | JPAException e) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
                }
                doGet(req, resp);
            }
        }
    }

    /**
     * Обрабатывает действия, приходящие с админской странички
     * @param dbService                 объект, за которым мы наблюдаем
     * @param parameterMap              параметры запроса, пришедшие с html-формы
     * @throws IllegalAccessException   при проблемах в DBService
     * @throws SQLException             при проблемах в DBService
     * @throws JPAException             при проблемах в DBService
     */
    private void dispatchParameters(DBServiceCached dbService, Map<String, String[]> parameterMap) throws IllegalAccessException, SQLException, JPAException {
        if (parameterMap.containsKey(ACTION_USER_SAVE)) {
            Long id = getUserID(parameterMap);
            if (id != null) {
                saveUser(dbService, id);
            }
        }

        if (parameterMap.containsKey(ACTION_USER_LOAD)) {
            Long id = getUserID(parameterMap);
            if (id != null) {
                loadUser(dbService, id);
            }
        }

        if (parameterMap.containsKey(ACTION_CACHE_CLEAR)) {
            DBServiceCacheEngine cacheEngine = dbService.getCacheEngine();
            if (cacheEngine != null) {
                clearCache(cacheEngine);
            }
        }
    }

    /**
     * Сохраняет в БД фиктивного пользователя с указанным идентификатором
     * @param dbService                 объект, через который происходит сохранение
     * @param id                        идентификатор сохраняемого пользователя
     * @throws IllegalAccessException   при проблемах в DBService
     * @throws SQLException             при проблемах в DBService
     * @throws JPAException             при проблемах в DBService
     */
    private void saveUser(DBServiceCached dbService, long id) throws IllegalAccessException, SQLException, JPAException {
        dbService.save(new UserDataSet(id, "user_test", (int)(id % 100)));
    }

    /**
     * Загружает из БД пользователя с указанным идентификатором
     * @param dbService         объект, через который происходит загрузка
     * @param id                идентификатор загружаемого пользователя
     * @throws JPAException     при проблемах в DBService
     * @throws SQLException     при проблемах в DBService
     */
    private void loadUser(DBServiceCached dbService, long id) throws JPAException, SQLException {
        dbService.load(id, UserDataSet.class);
    }

    /**
     * Очищает кеш
     * @param cacheEngine   объект кеша, который нужно очистить
     */
    private void clearCache(DBServiceCacheEngine cacheEngine) {
        cacheEngine.clear();
    }

    /**
     * Извлекает из запроса идентификатор пользователя (если он там имеется) для действий над DBService
     * @param parameterMap  параметры запроса, пришедшие с html-формы
     * @return              Идентификатор пользователя, если найден соответствующий ключ и по нему лежит корректное число.
     *                      Иначе, null
     */
    private Long getUserID(Map<String, String[]> parameterMap) {
        String[] values = parameterMap.get(PARAMETER_USER_ID);
        if (values.length == 1) { // у нас подразумевается единственное значение идентификатора
            try {
                return Long.parseLong(values[0]);
            } catch (NumberFormatException e) {
                // в этом случае мы просто ничего не делаем, а потом возвращаем null
            }
        }
        return null;
    }

    /**
     * Проверяет, определён ли переданный объект. Если получен null, перенаправляет на страницу с соответствующим оповещением
     * @param obj           объект, передаваемый для проверки
     * @param resp          html-response для перенаправления
     * @return              True, если переданный объект не null. Иначе, false
     * @throws IOException  в случае проблемы при перенаправлении
     */
    private boolean checkObject(Object obj, HttpServletResponse resp) throws IOException {
        if (obj == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, UNDEFINED_MESSAGE);
            return false;
        } else {
            return true;
        }
    }
}

package ru.otus.web;

import ru.otus.cache.CacheEngine;
import ru.otus.datasets.UserDataSet;
import ru.otus.db.DBServiceCacheEngine;
import ru.otus.db.DBServiceCached;
import ru.otus.jpa.JPAException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by Artem Gabbasov on 19.07.2017.
 * <p>
 */
public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String UNDEFINED_PAGE_TEMPLATE = "undefined.html";

    private static final String ACTION_USER_SAVE = "save";
    private static final String ACTION_USER_LOAD = "load";
    private static final String ACTION_CACHE_CLEAR = "clear";
    private static final String PARAMETER_USER_ID = "userID";

    private final String resourceBase;
    private final Supplier<DBServiceCached> dbServiceSupplier;

    public AdminServlet(String resourceBase, Supplier<DBServiceCached> dbServiceSupplier) {
        this.resourceBase = resourceBase;
        this.dbServiceSupplier = dbServiceSupplier;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBServiceCached dbService = dbServiceSupplier.get();
        if (checkObject(dbService, resp)) {
            DBServiceCacheEngine cacheEngine = dbService.getCacheEngine();
            if (checkObject(cacheEngine, resp)) {
                Map<String, Object> pageVariables = createPageVariablesMap(req, cacheEngine);

                resp.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request, DBServiceCacheEngine cacheEngine) {
        Map<String, Object> pageVariables = new HashMap<>();
        /*pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());

        //let's get login from session
        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);*/

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

    private void saveUser(DBServiceCached dbService, long id) throws IllegalAccessException, SQLException, JPAException {
        dbService.save(new UserDataSet(id, "user_test", (int)(id % 100)));
    }

    private void loadUser(DBServiceCached dbService, long id) throws JPAException, SQLException {
        dbService.load(id, UserDataSet.class);
    }

    private void clearCache(DBServiceCacheEngine cacheEngine) {
        cacheEngine.clear();
    }

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

    private boolean checkObject(Object obj, HttpServletResponse resp) throws IOException {
        if (obj == null) {
            resp.sendRedirect(UNDEFINED_PAGE_TEMPLATE);
            return false;
        } else {
            return true;
        }
    }
}

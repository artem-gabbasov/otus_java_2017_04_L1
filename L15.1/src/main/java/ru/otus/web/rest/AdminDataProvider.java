package ru.otus.web.rest;

import ru.otus.db.dbservices.DBServiceCacheEngine;
import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.web.ServerContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by Artem Gabbasov on 10.08.2017.
 * Вспомогательный класс для админской странички, который непосредственно работает с dbService
 */
class AdminDataProvider {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    private static final String UNDEFINED_MESSAGE = "DBService or CacheEngine is undefined.";

    private final Supplier<DBServiceCached> dbServiceSupplier;

    /**
     * @param dbServiceSupplier объект, который поставляет DBService, по которому требуется информация.
     *                          Использовал Supplier, а не просто ссылку на DBService, т.к. нам не важно,
     *                          за одним ли мы объектом следим или за разными - в общем случае мы можем их переключать
     */
    public AdminDataProvider(Supplier<DBServiceCached> dbServiceSupplier) {
        this.dbServiceSupplier = dbServiceSupplier;
    }

    public AdminDataProvider() {
        this(() -> ServerContext.getSpringBean("dbServiceCached", DBServiceCached.class));
    }

    /**
     * Отображает страницу с параметрами кеша и с управлением dbService'ом
     * @param resp              http-ответ (метод GET)
     * @throws IOException
     */
    public void loadPage(HttpServletResponse resp) throws IOException {
        DBServiceCached dbService = dbServiceSupplier.get();
        if (checkObject(dbService, resp)) {
            DBServiceCacheEngine cacheEngine = dbService.getCacheEngine();
            if (checkObject(cacheEngine, resp)) {
                Map<String, Object> pageVariables = createPageVariablesMap(cacheEngine);

                resp.getWriter().println(ServerContext.getSpringBean("templateProcessor", TemplateProcessor.class).getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

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

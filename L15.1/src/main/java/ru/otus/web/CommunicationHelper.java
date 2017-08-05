package ru.otus.web;

import ru.otus.db.dbservices.DBServiceCacheEngine;
import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.orm.datasets.instances.UserDataSet;
import ru.otus.orm.jpa.JPAException;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 04.08.2017.
 */
public class CommunicationHelper {
    private static final String DBSERVICE_PAGE_DATA = "db.data";
    private static final String DBSERVICE_USER_SAVE = "db.save";
    private static final String DBSERVICE_USER_LOAD = "db.load";
    private static final String DBSERVICE_CACHE_CLEAR = "db.clear";
    private static final String DBSERVICE_PARAMETER_USER_ID = "db.param.userID";

    private static final String FRONTEND_REFRESH = "fr.refresh";

    /**
     * Обрабатывает действия, приходящие с админской странички
     * @param dbService                 объект, за которым мы наблюдаем
     * @param parameterMap              параметры запроса, пришедшие с html-формы
     * @throws IllegalAccessException   при проблемах в DBService
     * @throws SQLException             при проблемах в DBService
     * @throws JPAException             при проблемах в DBService
     */
    public static void dispatchParameters(DBServiceCached dbService, Map<String, String[]> parameterMap) throws IllegalAccessException, SQLException, JPAException {
        if (parameterMap.containsKey(DBSERVICE_USER_SAVE)) {
            Long id = getUserID(parameterMap);
            if (id != null) {
                saveUser(dbService, id);
            }
        }

        if (parameterMap.containsKey(DBSERVICE_USER_LOAD)) {
            Long id = getUserID(parameterMap);
            if (id != null) {
                loadUser(dbService, id);
            }
        }

        if (parameterMap.containsKey(DBSERVICE_CACHE_CLEAR)) {
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
    private static void saveUser(DBServiceCached dbService, long id) throws IllegalAccessException, SQLException, JPAException {
        dbService.save(new UserDataSet(id, "user_test", (int)(id % 100)));
    }

    /**
     * Загружает из БД пользователя с указанным идентификатором
     * @param dbService         объект, через который происходит загрузка
     * @param id                идентификатор загружаемого пользователя
     * @throws JPAException     при проблемах в DBService
     * @throws SQLException     при проблемах в DBService
     */
    private static void loadUser(DBServiceCached dbService, long id) throws JPAException, SQLException {
        dbService.load(id, UserDataSet.class);
    }

    /**
     * Очищает кеш
     * @param cacheEngine   объект кеша, который нужно очистить
     */
    private static void clearCache(DBServiceCacheEngine cacheEngine) {
        cacheEngine.clear();
    }

    /**
     * Извлекает из запроса идентификатор пользователя (если он там имеется) для действий над DBService
     * @param parameterMap  параметры запроса, пришедшие с html-формы
     * @return              Идентификатор пользователя, если найден соответствующий ключ и по нему лежит корректное число.
     *                      Иначе, null
     */
    private static Long getUserID(Map<String, String[]> parameterMap) {
        String[] values = parameterMap.get(DBSERVICE_PARAMETER_USER_ID);
        if (values.length == 1) { // у нас подразумевается единственное значение идентификатора
            try {
                return Long.parseLong(values[0]);
            } catch (NumberFormatException e) {
                // в этом случае мы просто ничего не делаем, а потом возвращаем null
            }
        }
        return null;
    }
}

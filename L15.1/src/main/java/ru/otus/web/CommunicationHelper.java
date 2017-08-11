package ru.otus.web;

import ru.otus.db.dbservices.DBServiceCacheEngine;
import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.orm.datasets.instances.UserDataSet;
import ru.otus.orm.jpa.JPAException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 04.08.2017.
 */
public class CommunicationHelper {
    /**
     * Сохраняет в БД фиктивного пользователя с указанным идентификатором
     * @param dbService                 объект, через который происходит сохранение
     * @param id                        идентификатор сохраняемого пользователя
     * @throws IllegalAccessException   при проблемах в DBService
     * @throws SQLException             при проблемах в DBService
     * @throws JPAException             при проблемах в DBService
     */
    public static void saveUser(DBServiceCached dbService, long id) throws IllegalAccessException, SQLException, JPAException {
        dbService.save(new UserDataSet(id, "user_test", (int)(id % 100)));
    }

    /**
     * Загружает из БД пользователя с указанным идентификатором
     * @param dbService         объект, через который происходит загрузка
     * @param id                идентификатор загружаемого пользователя
     * @throws JPAException     при проблемах в DBService
     * @throws SQLException     при проблемах в DBService
     */
    public static void loadUser(DBServiceCached dbService, long id) throws JPAException, SQLException {
        dbService.load(id, UserDataSet.class);
    }

    /**
     * Очищает кеш
     * @param cacheEngine   объект кеша, который нужно очистить
     */
    public static void clearCache(DBServiceCacheEngine cacheEngine) {
        cacheEngine.clear();
    }
}

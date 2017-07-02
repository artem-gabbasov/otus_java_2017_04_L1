package ru.otus.db.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 * Интерфейс вспомогательного класса, содержащего запрос для изменения объектов
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface SQLUpdateHelper {
    void execUpdate(Connection connection, String tableName, Map<String, Object> fieldMap, String idColumnName) throws SQLException;
}

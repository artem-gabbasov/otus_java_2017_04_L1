package ru.otus.db.sql;

import ru.otus.datasets.DataSet;
import ru.otus.db.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 08.06.2017.
 * <p>
 * Интерфейс вспомогательного класса, содержащего запрос для получения объектов
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface SQLQueryHelper {
    <T extends DataSet> T execQuery(Connection connection, String tableName, String idColumnName, long id, ResultHandler<T> handler) throws SQLException;
}

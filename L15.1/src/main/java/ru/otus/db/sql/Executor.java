package ru.otus.db.sql;

import ru.otus.orm.datasets.DataSet;
import ru.otus.orm.datasets.NamedDataSet;
import ru.otus.db.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 08.07.2017.
 * <p>
 * Интерфейс вспомогательного класса, содержащего запросы для получения и изменения объектов
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface Executor {
    <T extends DataSet> T execQuery(Connection connection, String tableName, String whereColumnName, long id, ResultHandler<T> handler) throws SQLException;
    <T extends NamedDataSet> T execQueryNamed(Connection connection, String tableName, String whereColumnName, String name, ResultHandler<T> handler) throws SQLException;
    void execUpdate(Connection connection, String tableName, Map<String, Object> fieldMap, String idColumnName) throws SQLException;
}

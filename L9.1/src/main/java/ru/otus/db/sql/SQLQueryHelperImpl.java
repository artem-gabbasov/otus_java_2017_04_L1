package ru.otus.db.sql;

import ru.otus.datasets.DataSet;
import ru.otus.db.ResultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 08.06.2017.
 * <p>
 */
public class SQLQueryHelperImpl implements SQLQueryHelper {
    @Override
    public <T extends DataSet> T execQuery(Connection connection, String tableName, String idColumnName, long id, ResultHandler<T> handler) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(getQueryString(tableName, idColumnName))) {
            stmt.setLong(1, id);
            stmt.executeQuery();
            return handler.handle(stmt.getResultSet());
        } catch (SQLException e) {
            throw e;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public String getQueryString(String tableName, String idColumnName) {
        return "SELECT * FROM " + tableName + " WHERE " + SQLCommons.escapeColumnName(idColumnName) + " = ?;";
    }
}

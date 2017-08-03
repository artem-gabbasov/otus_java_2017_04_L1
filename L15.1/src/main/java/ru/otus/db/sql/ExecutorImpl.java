package ru.otus.db.sql;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;
import ru.otus.anytype.ValueSetHelper;
import ru.otus.anytype.setters.GeneralValueSetter;
import ru.otus.orm.datasets.DataSet;
import ru.otus.orm.datasets.NamedDataSet;
import ru.otus.db.PreparedStatementValueSetter;
import ru.otus.db.ResultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 08.07.2017.
 * <p>
 */
public class ExecutorImpl implements Executor {
    @Override
    public <T extends DataSet> T execQuery(Connection connection, String tableName, String whereColumnName, long id, ResultHandler<T> handler) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DAO.getQueryString(tableName, whereColumnName))) {
            stmt.setLong(1, id);
            stmt.executeQuery();
            return handler.handle(stmt.getResultSet());
        }
    }

    @Override
    public <T extends NamedDataSet> T execQueryNamed(Connection connection, String tableName, String whereColumnName, String name, ResultHandler<T> handler) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DAO.getQueryString(tableName, whereColumnName))) {
            stmt.setString(1, name);
            stmt.executeQuery();
            return handler.handle(stmt.getResultSet());
        }
    }

    @Override
    public void execUpdate(Connection connection, String tableName, Map<String, Object> fieldMap, String idColumnName) throws SQLException {
        int arraySize = fieldMap.size();

        String[] columnNames = new String[arraySize];
        Object[] fieldValues = new Object[arraySize];

        int arrayIndex = 0;
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            columnNames[arrayIndex] = entry.getKey();
            fieldValues[arrayIndex] = entry.getValue();
            arrayIndex++;
        }

        try (PreparedStatement stmt = connection.prepareStatement(DAO.getUpdateString(tableName, columnNames, idColumnName))) {
            prepareUpdate(stmt, fieldValues);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    /**
     * Задаёт значения параметров для запроса
     * @param stmt      объект запроса
     * @param values    массив параметров, которые следует задать
     */
    @SuppressWarnings("WeakerAccess")
    public void prepareUpdate(PreparedStatement stmt, Object[] values) {
        GeneralValueSetter valueSetter = new PreparedStatementValueSetter(stmt);
        ValueSetHelper helper = new ValueSetHelper();
        try {
            for(Object value : values) {
                helper.accept(valueSetter, value);
            }
        } catch (ValueException |UnsupportedTypeException e) {
            e.printStackTrace();
        }
    }
}

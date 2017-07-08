package ru.otus.db;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.getters.AbstractGeneralValueGetter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 18.06.2017.
 * <p>
 * Вспомогательный класс для получения значений из результатов запроса
 */
public class ResultSetValueGetter extends AbstractGeneralValueGetter {
    private final ResultSet resultSet;
    private final String columnName;

    public ResultSetValueGetter(ResultSet resultSet, String columnName) {
        this.resultSet = resultSet;
        this.columnName = columnName;
    }

    @Override
    public int getInt() throws UnsupportedTypeException, SQLValueException {
        try {
            return resultSet.getInt(columnName);
        } catch (SQLException e) {
            throw new SQLValueException(e);
        }
    }

    @Override
    public long getLong() throws UnsupportedTypeException, SQLValueException {
        try {
            return resultSet.getLong(columnName);
        } catch (SQLException e) {
            throw new SQLValueException(e);
        }
    }

    @Override
    public String getString() throws UnsupportedTypeException, SQLValueException {
        try {
            return resultSet.getString(columnName);
        } catch (SQLException e) {
            throw new SQLValueException(e);
        }
    }
}

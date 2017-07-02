package ru.otus.db;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.getters.AbstractGeneralValueGetter;

import java.sql.ResultSet;

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
    public int getInt() throws UnsupportedTypeException, Exception {
        return resultSet.getInt(columnName);
    }

    @Override
    public long getLong() throws UnsupportedTypeException, Exception {
        return resultSet.getLong(columnName);
    }

    @Override
    public String getString() throws UnsupportedTypeException, Exception {
        return resultSet.getString(columnName);
    }
}

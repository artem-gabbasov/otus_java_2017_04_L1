package ru.otus.db;

import ru.otus.anytype.setters.AbstractGeneralValueSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 * Вспомогательный класс для задания значений в запросе на изменение
 */
public class PreparedStatementValueSetter extends AbstractGeneralValueSetter {
    private final PreparedStatement stmt;
    private int index = 0;

    public PreparedStatementValueSetter(PreparedStatement stmt) {
        this.stmt = stmt;
    }

    @Override
    public void setInt(int value) throws SQLException {
        stmt.setInt(++index, value);
    }

    @Override
    public void setLong(long value) throws SQLException {
        stmt.setLong(++index, value);
    }

    @Override
    public void setString(String value) throws SQLException {
        stmt.setString(++index, value);
    }
}

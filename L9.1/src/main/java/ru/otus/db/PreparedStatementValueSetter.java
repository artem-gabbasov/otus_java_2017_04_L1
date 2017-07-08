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
    public void setInt(int value) throws SQLValueException {
        try {
            stmt.setInt(++index, value);
        } catch (SQLException e) {
            throw new SQLValueException(e);
        }
    }

    @Override
    public void setLong(long value) throws SQLValueException {
        try {
            stmt.setLong(++index, value);
        } catch (SQLException e) {
            throw new SQLValueException(e);
        }
    }

    @Override
    public void setString(String value) throws SQLValueException {
        try {
            stmt.setString(++index, value);
        } catch (SQLException e) {
            throw new SQLValueException(e);
        }
    }
}

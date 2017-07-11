package ru.otus.db;

import ru.otus.anytype.ValueException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 08.07.2017.
 * <p>
 */
public class SQLValueException extends ValueException {
    public SQLValueException(SQLException e) {
        super(e);
    }
}

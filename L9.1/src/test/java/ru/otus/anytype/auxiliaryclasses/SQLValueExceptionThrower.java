package ru.otus.anytype.auxiliaryclasses;

import ru.otus.anytype.getters.ValueGetter;
import ru.otus.db.SQLValueException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 08.07.2017.
 * <p>
 */
public class SQLValueExceptionThrower implements ValueGetter{
    @Override
    public Object getObject() throws SQLValueException {
        throw new SQLValueException(new SQLException("test"));
    }
}

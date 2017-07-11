package ru.otus.anytype.auxiliaryclasses;

import ru.otus.anytype.ValueException;
import ru.otus.anytype.getters.ValueGetter;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 08.07.2017.
 * <p>
 */
public class SQLExceptionThrower implements ValueGetter {

    @Override
    public Object getObject() throws ValueException {
        throw new ValueException(new SQLException("test"));
    }
}

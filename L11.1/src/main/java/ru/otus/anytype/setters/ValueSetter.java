package ru.otus.anytype.setters;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 * Базовый интерфейс, подразумевающий только обработку типа Object
 */
@SuppressWarnings("unused")
public interface ValueSetter {
    void setObject(Object value) throws UnsupportedTypeException, ValueException;
}

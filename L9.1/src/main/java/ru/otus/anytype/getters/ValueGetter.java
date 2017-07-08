package ru.otus.anytype.getters;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;

/**
 * Created by Artem Gabbasov on 22.06.2017.
 * <p>
 * Базовый интерфейс, подразумевающий только обработку типа Object
 */
@SuppressWarnings("SameReturnValue")
public interface ValueGetter {
    Object getObject() throws UnsupportedTypeException, ValueException;
}

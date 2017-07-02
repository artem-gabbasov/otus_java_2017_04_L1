package ru.otus.anytype.getters;

import ru.otus.anytype.UnsupportedTypeException;

/**
 * Created by Artem Gabbasov on 22.06.2017.
 * <p>
 * Интерфейс для обработки основных типов
 */
@SuppressWarnings("SameReturnValue")
public interface GeneralValueGetter extends ValueGetter {
    int getInt() throws UnsupportedTypeException, Exception;
    byte getByte() throws UnsupportedTypeException, Exception;
    short getShort() throws UnsupportedTypeException, Exception;
    long getLong() throws UnsupportedTypeException, Exception;
    boolean getBoolean() throws UnsupportedTypeException, Exception;
    double getDouble() throws UnsupportedTypeException, Exception;
    float getFloat() throws UnsupportedTypeException, Exception;
    char getChar() throws UnsupportedTypeException, Exception;
    String getString() throws UnsupportedTypeException, Exception;
}

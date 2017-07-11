package ru.otus.anytype.getters;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;

/**
 * Created by Artem Gabbasov on 22.06.2017.
 * <p>
 * Интерфейс для обработки основных типов
 */
@SuppressWarnings({"SameReturnValue", "RedundantThrows"})
public interface GeneralValueGetter extends ValueGetter {
    int getInt() throws UnsupportedTypeException, ValueException;
    byte getByte() throws UnsupportedTypeException, ValueException;
    short getShort() throws UnsupportedTypeException, ValueException;
    long getLong() throws UnsupportedTypeException, ValueException;
    boolean getBoolean() throws UnsupportedTypeException, ValueException;
    double getDouble() throws UnsupportedTypeException, ValueException;
    float getFloat() throws UnsupportedTypeException, ValueException;
    char getChar() throws UnsupportedTypeException, ValueException;
    String getString() throws UnsupportedTypeException, ValueException;
}

package ru.otus.anytype.setters;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 * Интерфейс для обработки основных типов
 */
@SuppressWarnings({"unused", "RedundantThrows"})
public interface GeneralValueSetter extends ValueSetter {
    void setInt(int value) throws UnsupportedTypeException, ValueException;
    void setByte(byte value) throws UnsupportedTypeException, ValueException;
    void setShort(short value) throws UnsupportedTypeException, ValueException;
    void setLong(long value) throws UnsupportedTypeException, ValueException;
    void setBoolean(boolean value) throws UnsupportedTypeException, ValueException;
    void setDouble(double value) throws UnsupportedTypeException, ValueException;
    void setFloat(float value) throws UnsupportedTypeException, ValueException;
    void setChar(char value) throws UnsupportedTypeException, ValueException;
    void setString(String value) throws UnsupportedTypeException, ValueException;
}

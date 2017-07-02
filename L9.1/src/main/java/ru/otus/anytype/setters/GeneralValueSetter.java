package ru.otus.anytype.setters;

import ru.otus.anytype.UnsupportedTypeException;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 * Интерфейс для обработки основных типов
 */
@SuppressWarnings("unused")
public interface GeneralValueSetter extends ValueSetter {
    void setInt(int value) throws UnsupportedTypeException, Exception;
    void setByte(byte value) throws UnsupportedTypeException, Exception;
    void setShort(short value) throws UnsupportedTypeException, Exception;
    void setLong(long value) throws UnsupportedTypeException, Exception;
    void setBoolean(boolean value) throws UnsupportedTypeException, Exception;
    void setDouble(double value) throws UnsupportedTypeException, Exception;
    void setFloat(float value) throws UnsupportedTypeException, Exception;
    void setChar(char value) throws UnsupportedTypeException, Exception;
    void setString(String value) throws UnsupportedTypeException, Exception;
}

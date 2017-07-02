package ru.otus.anytype.setters;

import ru.otus.anytype.UnsupportedTypeException;

/**
 * Created by Artem Gabbasov on 22.06.2017.
 * <p>
 * Вспомогательный класс для перегрузки в наследниках только поддерживаемых методов
 */
public class AbstractGeneralValueSetter implements GeneralValueSetter {
    
    @Override
    public void setObject(Object value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setInt(int value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setByte(byte value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setShort(short value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setLong(long value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setBoolean(boolean value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setDouble(double value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setFloat(float value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setChar(char value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }

    @Override
    public void setString(String value) throws UnsupportedTypeException, Exception {
        throw new UnsupportedTypeException();
    }
}

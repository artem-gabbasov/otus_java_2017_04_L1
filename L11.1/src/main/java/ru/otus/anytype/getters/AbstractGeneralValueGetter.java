package ru.otus.anytype.getters;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;

/**
 * Created by Artem Gabbasov on 22.06.2017.
 * <p>
 * Вспомогательный класс для перегрузки в наследниках только поддерживаемых методов
 */
public class AbstractGeneralValueGetter implements GeneralValueGetter {
    @Override
    public Object getObject() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public int getInt() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public byte getByte() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public short getShort() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public long getLong() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public boolean getBoolean() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public double getDouble() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public float getFloat() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public char getChar() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }

    @Override
    public String getString() throws UnsupportedTypeException, ValueException {
        throw new UnsupportedTypeException();
    }
}
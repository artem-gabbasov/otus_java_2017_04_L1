package ru.otus.anytype.auxiliaryclasses;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.getters.GeneralValueGetter;

/**
 * Created by Artem Gabbasov on 25.06.2017.
 * <p>
 */
public class TestValueGetterImpl implements GeneralValueGetter {
    public String lastValue = "";

    @Override
    public Object getObject() throws UnsupportedTypeException, Exception {
        lastValue = "Object";
        return null;
    }

    @Override
    public int getInt() throws UnsupportedTypeException, Exception {
        lastValue = "int";
        return 0;
    }

    @Override
    public byte getByte() throws UnsupportedTypeException, Exception {
        lastValue = "byte";
        return 0;
    }

    @Override
    public short getShort() throws UnsupportedTypeException, Exception {
        lastValue = "short";
        return 0;
    }

    @Override
    public long getLong() throws UnsupportedTypeException, Exception {
        lastValue = "long";
        return 0;
    }

    @Override
    public boolean getBoolean() throws UnsupportedTypeException, Exception {
        lastValue = "boolean";
        return false;
    }

    @Override
    public double getDouble() throws UnsupportedTypeException, Exception {
        lastValue = "double";
        return 0;
    }

    @Override
    public float getFloat() throws UnsupportedTypeException, Exception {
        lastValue = "float";
        return 0;
    }

    @Override
    public char getChar() throws UnsupportedTypeException, Exception {
        lastValue = "char";
        return 0;
    }

    @Override
    public String getString() throws UnsupportedTypeException, Exception {
        lastValue = "String";
        return null;
    }
}

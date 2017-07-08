package ru.otus.anytype.auxiliaryclasses;

import ru.otus.anytype.getters.GeneralValueGetter;

/**
 * Created by Artem Gabbasov on 25.06.2017.
 * <p>
 */
public class TestValueGetterImpl implements GeneralValueGetter {
    public String lastValue = "";

    @Override
    public Object getObject() {
        lastValue = "Object";
        return null;
    }

    @Override
    public int getInt() {
        lastValue = "int";
        return 0;
    }

    @Override
    public byte getByte() {
        lastValue = "byte";
        return 0;
    }

    @Override
    public short getShort() {
        lastValue = "short";
        return 0;
    }

    @Override
    public long getLong() {
        lastValue = "long";
        return 0;
    }

    @Override
    public boolean getBoolean() {
        lastValue = "boolean";
        return false;
    }

    @Override
    public double getDouble() {
        lastValue = "double";
        return 0;
    }

    @Override
    public float getFloat() {
        lastValue = "float";
        return 0;
    }

    @Override
    public char getChar() {
        lastValue = "char";
        return 0;
    }

    @Override
    public String getString() {
        lastValue = "String";
        return null;
    }
}

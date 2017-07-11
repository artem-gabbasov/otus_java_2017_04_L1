package ru.otus.anytype.auxiliaryclasses;

import ru.otus.anytype.setters.GeneralValueSetter;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 */
public class TestValueSetterImpl implements GeneralValueSetter {
    public String lastValue = "";

    @Override
    public void setObject(Object value) {
        lastValue = "Object";
    }

    @Override
    public void setInt(int value) {
        lastValue = "int";
    }

    @Override
    public void setByte(byte value) {
        lastValue = "byte";
    }

    @Override
    public void setShort(short value) {
        lastValue = "short";
    }

    @Override
    public void setLong(long value) {
        lastValue = "long";
    }

    @Override
    public void setBoolean(boolean value) {
        lastValue = "boolean";
    }

    @Override
    public void setDouble(double value) {
        lastValue = "double";
    }

    @Override
    public void setFloat(float value) {
        lastValue = "float";
    }

    @Override
    public void setChar(char value) {
        lastValue = "char";
    }

    @Override
    public void setString(String value) {
        lastValue = "String";
    }
}

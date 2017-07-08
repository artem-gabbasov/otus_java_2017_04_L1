package ru.otus.anytype.auxiliaryclasses;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;
import ru.otus.anytype.ValueSetHelper;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 */
public class TestValueSetHelper extends ValueSetHelper {
    private final TestValueSetterImpl valueSetter;

    public TestValueSetHelper(TestValueSetterImpl valueSetter) {
        this.valueSetter = valueSetter;
    }

    public String showValue(Object value) throws UnsupportedTypeException, ValueException {
        accept(valueSetter, value);
        //System.out.println(valueSetter.lastValue);
        return valueSetter.lastValue;
    }
}

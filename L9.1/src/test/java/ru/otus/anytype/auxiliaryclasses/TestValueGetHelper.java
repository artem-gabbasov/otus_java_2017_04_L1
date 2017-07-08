package ru.otus.anytype.auxiliaryclasses;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;
import ru.otus.anytype.ValueGetHelper;

/**
 * Created by Artem Gabbasov on 25.06.2017.
 * <p>
 */
public class TestValueGetHelper extends ValueGetHelper {
    private final TestValueGetterImpl valueGetter;

    public TestValueGetHelper(TestValueGetterImpl valueGetter) {
        this.valueGetter = valueGetter;
    }

    public String showValue(Class<?> clazz) throws UnsupportedTypeException, ValueException {
        accept(valueGetter, clazz);
        //System.out.println(valueGetter.lastValue);
        return valueGetter.lastValue;
    }

}

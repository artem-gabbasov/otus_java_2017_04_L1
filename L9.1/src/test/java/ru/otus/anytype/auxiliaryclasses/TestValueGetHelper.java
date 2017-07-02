package ru.otus.anytype.auxiliaryclasses;

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

    public String showValue(Class<?> clazz) throws Exception {
        accept(valueGetter, clazz);
        //System.out.println(valueGetter.lastValue);
        return valueGetter.lastValue;
    }

}

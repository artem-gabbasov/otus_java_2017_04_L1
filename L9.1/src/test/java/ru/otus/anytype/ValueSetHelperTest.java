package ru.otus.anytype;

import org.junit.Test;
import ru.otus.anytype.auxiliaryclasses.TestValueGetHelper;
import ru.otus.anytype.auxiliaryclasses.TestValueGetterImpl;
import ru.otus.anytype.auxiliaryclasses.TestValueSetHelper;
import ru.otus.anytype.auxiliaryclasses.TestValueSetterImpl;

import java.util.Collections;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 */
public class ValueSetHelperTest {
    @SuppressWarnings({"UnnecessaryBoxing", "BooleanConstructorCall"})
    @Test
    public void setter() throws ValueException, UnsupportedTypeException {
        TestValueSetterImpl setter = new TestValueSetterImpl();
        TestValueSetHelper helper = new TestValueSetHelper(setter);

        assert helper.showValue(4).equals("int");
        assert helper.showValue((byte)4).equals("byte");
        assert helper.showValue(new Short((short)4)).equals("short");
        assert helper.showValue(4L).equals("long");
        assert helper.showValue(new Boolean(false)).equals("boolean");
        assert helper.showValue(4.5).equals("double");
        assert helper.showValue(4.5F).equals("float");
        assert helper.showValue(new Character('4')).equals("char");
        assert helper.showValue("").equals("String");
        assert helper.showValue(Collections.EMPTY_LIST).equals("Object");
    }

    @SuppressWarnings("InstantiatingObjectToGetClassObject")
    @Test
    public void getter() throws UnsupportedTypeException, ValueException {
        TestValueGetterImpl getter = new TestValueGetterImpl();
        TestValueGetHelper helper = new TestValueGetHelper(getter);

        assert helper.showValue(int.class).equals("int");
        assert helper.showValue((new Byte((byte)4)).getClass()).equals("byte");
        assert helper.showValue(Short.class).equals("short");
        assert helper.showValue(new Character('4').getClass()).equals("char");
        assert helper.showValue("".getClass()).equals("String");
        assert helper.showValue(Collections.EMPTY_LIST.getClass()).equals("Object");
    }
}

package ru.otus.l51.tests;

import ru.otus.l51.tests.ReflectionHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Artem Gabbasov on 05.05.2017.
 */
public class ClassListTesterTest {
    public void launch() {
        // result: void
        // parameters: <none>

        // запускаем тесты по классам, которые должны пройти
        List<Class<?>> list = Arrays.asList(ClassTesterTest.class, ReflectionHelperTest.class);

        ClassListTester classListTester = ReflectionHelper.instantiate(
                ClassListTester.class,
                new Object[]{list},
                new Class<?>[]{List.class}
            );

        ReflectionHelper.callMethod(
                classListTester,
                "launch"
        );
    }
}

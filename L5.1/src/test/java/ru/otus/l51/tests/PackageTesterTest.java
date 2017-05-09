package ru.otus.l51.tests;

import ru.otus.l51.MyAssert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Artem Gabbasov on 09.05.2017.
 *
 * Поскольку {@code launch} использует отдельно тестируемый {@code ClassListTester},
 * достаточно протестировать, что {@code PackageTester} верно собирает список классов в переданной package
 */
public class PackageTesterTest {
    public void instantiate() {
        PackageTester packageTester = ReflectionHelper.instantiate(
                PackageTester.class,
                "ru.otus.l51.tests"
        );

        List<Class<?>> foundClasses = (List<Class<?>>) ReflectionHelper.getFieldValue(packageTester, "classes");

        MyAssert.notNull(foundClasses);

        List<String> foundClassNames = foundClasses.stream()
                .map(Class::getName)
                .collect(Collectors.toList());

        List<String> expectedClassNames = Arrays.asList(
            "ru.otus.l51.tests.ClassListTester",
            "ru.otus.l51.tests.ClassTester",
            "ru.otus.l51.tests.PackageTester",
            "ru.otus.l51.tests.ReflectionHelper",
            "ru.otus.l51.tests.ClassListTesterTest",
            "ru.otus.l51.tests.ClassTesterTest",
            "ru.otus.l51.tests.PackageTesterTest",
            "ru.otus.l51.tests.ReflectionHelperTest",
            "ru.otus.l51.tests.TestClass"
        );

        // отсортируем, чтобы получить одинаковый порядок
        Collections.sort(foundClassNames);
        Collections.sort(expectedClassNames);

        MyAssert.assertTrue(foundClassNames.equals(expectedClassNames));
    }
}

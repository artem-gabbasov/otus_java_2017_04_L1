package ru.otus.l51;

import ru.otus.l51.annotations.testing.After;
import ru.otus.l51.annotations.testing.Before;
import ru.otus.l51.annotations.testing.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Artem Gabbasov on 08.05.2017.
 *
 * Класс для тестирования класса {@code ClassTester}
 * Метод {@code launch} тестирует одноимённый метод класса {@code ClassTester},
 * а также запускает все остальные тесты
 */
public class ClassTesterTest {
    private static boolean isBeforeLaunched = false;
    private static boolean isAfterLaunched = false;
    private static boolean isTestBetweenBeforeAndAfterLaunched = false;

    private static final List<String> invokedTestsNames = new ArrayList<>();

    private final static List<String> allTestAnnotatedMethods = Arrays.asList(
            "collectMethodsByAnnotation",
            "collectMethods",
            "runTest",
            "runAdditionalMethods"
        );
    static {
        Collections.sort(allTestAnnotatedMethods);
    }

    private void clearState() {
        isBeforeLaunched = false;
        isAfterLaunched = false;
        isTestBetweenBeforeAndAfterLaunched = false;
    }

    private void clearInvokedTestsNames() {
        invokedTestsNames.clear();
    }

    @Test
    public void collectMethodsByAnnotation() {
        // result: List<String>
        // parameters: (Class<? extends Annotation> annotation)

        // собираем список всех методов, аннотированных "Test", в данном классе
        List<String> producedList = (List<String>) ReflectionHelper.callMethod(
                ReflectionHelper.instantiate(ClassTester.class, this.getClass()),
                "collectMethodsByAnnotation",
                Test.class
            );

        // выставляем листам единый порядок (список, с которым мы сравниваем, уже отсортирован)
        Collections.sort(producedList);

        MyAssert.assertTrue(producedList.equals(allTestAnnotatedMethods));

        invokedTestsNames.add("collectMethodsByAnnotation");
    }

    @Test
    public void collectMethods() {
        // result: void
        // parameters: <none>

        ClassTester classTester = ReflectionHelper.instantiate(ClassTester.class, this.getClass());

        ReflectionHelper.callMethod(
                classTester,
                "collectMethods"
        );

        List<String> producedTests = (List<String>) ReflectionHelper.getFieldValue(classTester, "tests");
        List<String> producedBefores = (List<String>) ReflectionHelper.getFieldValue(classTester, "befores");
        List<String> producedAfters = (List<String>) ReflectionHelper.getFieldValue(classTester, "afters");

        List<String> expectedBefores = Collections.singletonList(
                "fakeBefore"
        );
        List<String> expectedAfters = Collections.singletonList(
                "fakeAfter"
        );

        MyAssert.notNull(producedTests);
        MyAssert.notNull(producedBefores);
        MyAssert.notNull(producedAfters);

        // выставляем листам единый порядок (список, с которым мы сравниваем, уже отсортирован)
        Collections.sort(producedTests);
        Collections.sort(producedBefores);
        Collections.sort(producedAfters);
        Collections.sort(expectedBefores);
        Collections.sort(expectedAfters);

        MyAssert.assertTrue(producedTests.equals(allTestAnnotatedMethods));
        MyAssert.assertTrue(producedBefores.equals(expectedBefores));
        MyAssert.assertTrue(producedAfters.equals(expectedAfters));

        invokedTestsNames.add("collectMethods");
    }

    @Test
    public void runTest() {
        // result: void
        // parameters: (String methodName)

        clearState();

        ClassTester classTester = ReflectionHelper.instantiate(ClassTester.class, this.getClass());

        // нужно предварительно просканировать аннотированные методы, чтобы проверить выполнение @Before и @After
        ReflectionHelper.callMethod(
                classTester,
                "collectMethods"
        );

        ReflectionHelper.callMethod(
                classTester,
                "runTest",
                "testBetweenBeforeAndAfter"
        );

        // удостоверимся, что мы попали в тот метод, который запрашивали для теста
        MyAssert.assertTrue(isTestBetweenBeforeAndAfterLaunched);

        // и удостоверимся, что выполнились методы, размеченные как @Before и @After
        MyAssert.assertTrue(isBeforeLaunched);
        MyAssert.assertTrue(isAfterLaunched);

        invokedTestsNames.add("runTest");
    }

    @Test
    public void runAdditionalMethods() {
        // result: void
        // parameters: (List<String> list)

        clearState();

        ClassTester classTester = ReflectionHelper.instantiate(ClassTester.class, this.getClass());
        List<String> list = Collections.singletonList("fakeBefore");

        // передаём явно класс List, т.к. иначе ищется метод, принимающий ArrayList
        ReflectionHelper.callMethod(
                classTester,
                "runAdditionalMethods",
                new Object[]{list},
                new Class<?>[]{List.class}
        );

        MyAssert.assertTrue(isBeforeLaunched);

        invokedTestsNames.add("runAdditionalMethods");
    }

    public void launch() {
        // result: void
        // parameters: <none>

        clearInvokedTestsNames();

        ClassTester classTester = ReflectionHelper.instantiate(ClassTester.class, this.getClass());

        ReflectionHelper.callMethod(
                classTester,
                "launch"
        );

        // выставляем листам единый порядок (allTestAnnotatedMethods уже отсортирован)
        Collections.sort(invokedTestsNames);

        MyAssert.assertTrue(invokedTestsNames.equals(allTestAnnotatedMethods));
    }

    // нужен для проверки порядка вызова методов (т.е. что он вызывается строго между методом, отмеченным @Before, и методом, отмеченным @After)
    public void testBetweenBeforeAndAfter() {
        isTestBetweenBeforeAndAfterLaunched = true;
        MyAssert.assertTrue(isBeforeLaunched && !isAfterLaunched);
    }

    @Before
    public void fakeBefore() {
        isBeforeLaunched = true;
    }

    @After
    public void fakeAfter() {
        isAfterLaunched = true;
    }
}

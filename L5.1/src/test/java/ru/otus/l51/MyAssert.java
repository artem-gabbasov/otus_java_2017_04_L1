package ru.otus.l51;

/**
 * Created by Artem Gabbasov on 06.05.2017.
 */
public class MyAssert {
    public static void fail(Runnable r) {
        try {
            r.run();
            throw new AssertionError("MyAssert.fail on " + r);
        } catch (Exception e) {
            // ok. do nothing
        }
    }

    public static void assertTrue(boolean b) {
        if (!b) {
            throw new AssertionError("MyAssert.assertTrue");
        }
    }

    public static void notNull(Object o) {
        if (o == null) {
            throw new AssertionError("MyAssert.notNull");
        }
    }
}

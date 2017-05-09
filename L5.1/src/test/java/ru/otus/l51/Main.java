package ru.otus.l51;

import ru.otus.l51.tests.ClassListTesterTest;
import ru.otus.l51.tests.PackageTesterTest;

/**
 * Created by Artem Gabbasov on 08.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        new ClassListTesterTest().launch();
        new PackageTesterTest().instantiate();
    }
}

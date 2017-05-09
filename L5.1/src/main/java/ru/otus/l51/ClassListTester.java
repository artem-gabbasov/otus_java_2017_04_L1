package ru.otus.l51;

import java.util.List;

/**
 * Created by Artem Gabbasov on 09.05.2017.
 */
public class ClassListTester {
    private final List<Class<?>> classList;

    public ClassListTester(List<Class<?>> classList) {
        this.classList = classList;
    }

    public void launch() {
        classList.stream()
                .map(ClassTester::new)
                .forEach(ClassTester::launch);
    }
}

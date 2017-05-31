package ru.otus;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Artem Gabbasov on 26.05.2017.
 *
 * Класс, объединяющий несколько банкоматов
 */
public class ATMDepartment implements Maintainable {
    private final Set<Maintainable> elements;

    public ATMDepartment() {
        elements = new HashSet<>();
    }

    public void add(Maintainable element) {
        elements.add(element);
        element.onAddition();
    }

    public long getRemainder() {
        return elements.stream()
                .mapToLong(Maintainable::getRemainder)
                .sum();
    }

    @Override
    public void restore() {
        elements.forEach(Maintainable::restore);
    }

    @Override
    public void onAddition() {
        // никаких действий не требуется
    }
}

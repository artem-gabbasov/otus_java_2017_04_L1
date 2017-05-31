package ru.otus;

/**
 * Created by Artem Gabbasov on 26.05.2017.
 *
 * Интерфейс для банкоматов и их объединений (классы ru.otus.ATM и ru.otus.ATMDepartment).
 * Создан для реализации паттерна Composite
 */
interface Maintainable {
    long getRemainder();
    void restore();

    /**
     * Действие, выполняемое при добавлении в контейнер
     */
    void onAddition();
}

package ru.otus.observable;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 * Интерфейс listener'а, который отслеживает изменения значения переменной
 */
@FunctionalInterface
public interface Listener<T> {
    /**
     * Событие изменения значения переменной
     * @param variableName  имя изменяемой переменной
     * @param value         новое значение переменной
     */
    void fireEvent(String variableName, T value);
}

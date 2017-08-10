package ru.otus.observable;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
@FunctionalInterface
public interface Listener<T> {
    void fireEvent(String variableName, T value);
}

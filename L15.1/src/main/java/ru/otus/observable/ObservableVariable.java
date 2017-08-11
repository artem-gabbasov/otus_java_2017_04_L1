package ru.otus.observable;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 * Интерфейс переменной, изменения которой отслеживаются
 */
public interface ObservableVariable<T> {
    String getName();
    void setValue(T value);
    T getValue();
}

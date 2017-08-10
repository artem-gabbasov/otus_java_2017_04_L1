package ru.otus.observable;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public interface ObservableVariable<T> {
    String getName();
    void setValue(T value);
    T getValue();
}

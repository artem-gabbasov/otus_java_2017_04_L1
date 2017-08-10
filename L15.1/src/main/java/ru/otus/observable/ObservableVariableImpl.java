package ru.otus.observable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public class ObservableVariableImpl<T> implements ObservableVariable<T> {
    private final Consumer<ObservableVariable<T>> onChangeEvent;
    private final String name;
    private T value;

    public ObservableVariableImpl(Consumer<ObservableVariable<T>> onChangeEvent, String name, T initialValue) {
        Objects.requireNonNull(onChangeEvent);

        this.onChangeEvent = onChangeEvent;
        this.name = name;
        this.value = initialValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setValue(T value) {
        // проверяем не через equals, т.к. нас интересует, изменяется ли значение переменной value
        if (this.value != value) {
            this.value = value;
            onChangeEvent.accept(this);
        }
    }

    @Override
    public T getValue() {
        return value;
    }
}

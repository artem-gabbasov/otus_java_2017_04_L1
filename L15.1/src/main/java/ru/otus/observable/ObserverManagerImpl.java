package ru.otus.observable;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public class ObserverManagerImpl<T> implements ObserverManager<T> {
    private final Set<Listener<T>> listeners = new CopyOnWriteArraySet<>();

    @Override
    public ObservableVariable<T> createNewObservableVariable(String name, T initialValue) {
        return new ObservableVariableImpl<>(this::onChange, name, initialValue);
    }

    @Override
    public boolean addListener(Listener<T> listener) {
        return listeners.add(listener);
    }

    @Override
    public boolean removeListener(Listener<T> listener) {
        return listeners.remove(listener);
    }

    @Override
    public void onChange(ObservableVariable<T> observableVariable) {
        listeners.forEach(listener -> listener.fireEvent(observableVariable.getName(), observableVariable.getValue()));
    }
}

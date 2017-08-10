package ru.otus.observable;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public interface ObserverManager<T> {
    ObservableVariable<T> createNewObservableVariable(String name, T initialValue);
    boolean addListener(Listener<T> listener);
    boolean removeListener(Listener<T> listener);
    void onChange(ObservableVariable<T> observableVariable);
}

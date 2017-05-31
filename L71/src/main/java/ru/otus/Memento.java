package ru.otus;

/**
 * Created by Artem Gabbasov on 25.05.2017.
 */
class Memento<T> {
    private final Restorable<T> originator;
    private final T state;

    public Memento(Restorable<T> originator) {
        this.originator = originator;
        state = originator.getState();
    }

    public void restore() {
        originator.setState(state);
    }
}

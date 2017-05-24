/**
 * Created by Artem Gabbasov on 25.05.2017.
 */
class Memento<T extends Restorable> {
    private final T originator;
    private final Object state;

    public Memento(T originator) {
        this.originator = originator;
        state = originator.getState();
    }

    public void restore() {
        originator.setState(state);
    }
}

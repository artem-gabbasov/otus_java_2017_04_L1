/**
 * Created by Artem Gabbasov on 25.05.2017.
 */
class Memento {
    private final Restorable originator;
    private final Object state;

    public Memento(Restorable originator) {
        this.originator = originator;
        state = originator.getState();
    }

    public void restore() {
        originator.setState(state);
    }
}

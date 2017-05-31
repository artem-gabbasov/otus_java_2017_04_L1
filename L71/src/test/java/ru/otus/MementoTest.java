package ru.otus;

import org.junit.Test;
import ru.otus.Cell;
import ru.otus.Memento;

/**
 * Created by Artem Gabbasov on 25.05.2017.
 */
public class MementoTest {
    @Test
    public void restore() {
        Cell cell = new Cell(500, 4);
        Memento m = new Memento(cell);
        cell.setCount(0);
        m.restore();
        assert cell.getCount() == 4;
    }
}

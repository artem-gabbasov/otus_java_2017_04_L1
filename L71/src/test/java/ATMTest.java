import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Artem Gabbasov on 25.05.2017.
 */
public class ATMTest {
    @Test
    public void getState() {
        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 40);
        cellsArray[1] = new Cell(500, 30);

        ATM atm = new ATM(cellsArray);

        Cell[] expectedArray = Arrays.copyOf(cellsArray, cellsArray.length);

        assert Arrays.equals((Cell[])atm.getState(), expectedArray);
    }

    @Test
    // проверим, что мы получаем отсортированный массив
    public void setStateSort() {
        ATM atm = new ATM(null);

        Cell[] cellsArray = new Cell[5];
        cellsArray[0] = new Cell(500, 4);
        cellsArray[1] = new Cell(50, 40);
        cellsArray[2] = new Cell(100, 30);
        cellsArray[3] = new Cell(5000, 4);
        cellsArray[4] = new Cell(1000, 10);
        atm.setState(cellsArray);

        Cell[] sortedArray = new Cell[5];
        sortedArray[0] = new Cell(50, 40);
        sortedArray[1] = new Cell(100, 30);
        sortedArray[2] = new Cell(500, 4);
        sortedArray[3] = new Cell(1000, 10);
        sortedArray[4] = new Cell(5000, 4);

        assert Arrays.equals((Cell[])atm.getState(), sortedArray);
    }

    @Test
    // проверим, что при наличии дублирующихся элементов выдаётся исключение
    public void setStateDuplicates() {
        Cell[] cellsArray = new Cell[3];
        cellsArray[0] = new Cell(500, 4);
        cellsArray[1] = new Cell(50, 40);
        cellsArray[2] = new Cell(500, 30);

        try {
            new ATM(cellsArray);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    // меняем существующую ячейку
    public void updateCellModify() {
        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 40);
        cellsArray[1] = new Cell(500, 30);

        ATM atm = new ATM(cellsArray);

        Cell[] expectedArray = Arrays.copyOf(cellsArray, cellsArray.length);
        expectedArray[1] = new Cell(500, 15);

        atm.updateCell(500, 15L);

        assert Arrays.equals((Cell[])atm.getState(), expectedArray);
    }

    @Test
    // добавляем новую ячейку
    public void updateCellAdd() {
        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 40);
        cellsArray[1] = new Cell(500, 30);

        ATM atm = new ATM(cellsArray);

        Cell[] expectedArray = Arrays.copyOf(cellsArray, 3);
        expectedArray[2] = new Cell(1000, 20);

        atm.updateCell(1000, 20L);

        assert Arrays.equals((Cell[])atm.getState(), expectedArray);
    }

    @Test
    public void withdrawAmountSuccess() {
        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 4);
        cellsArray[1] = new Cell(500, 3);

        ATM atm = new ATM(cellsArray);

        Cell[] expectedArray = new Cell[2];
        expectedArray[0] = new Cell(50, 1);
        expectedArray[1] = new Cell(500, 0);

        atm.withdrawAmount(3 * 500 + 3 * 50);

        assert Arrays.equals((Cell[])atm.getState(), expectedArray);
    }

    @Test
    public void withdrawAmountFail() {
        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 4);
        cellsArray[1] = new Cell(500, 3);

        ATM atm = new ATM(cellsArray);

        try {
            atm.withdrawAmount(3 * 500 + 5 * 50);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }

        // и убедимся, что никакие купюры мы при этом не выдали
        assert Arrays.equals((Cell[])atm.getState(), cellsArray);
    }

    @Test
    public void withdrawAmountEmpty() {
        ATM atm = new ATM(null);

        try {
            atm.withdrawAmount(50);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    // проверка в обычных условиях
    public void getRemainderRegular() {
        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 4);
        cellsArray[1] = new Cell(500, 3);

        ATM atm = new ATM(cellsArray);

        assert atm.getRemainder() == 500 * 3 + 50 * 4;
    }

    @Test
    // проверка на пустом банкомате
    public void getRemainderEmpty() {
        ATM atm = new ATM(null);

        assert atm.getRemainder() == 0;
    }

    @Test
    public void saveAndRestore() {
        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 4);
        cellsArray[1] = new Cell(500, 3);

        ATM atm = new ATM(cellsArray);

        Cell[] expectedArray = Arrays.copyOf(cellsArray, cellsArray.length);

        atm.withdrawAmount(3 * 500 + 3 * 50);
        atm.restore();

        assert Arrays.equals((Cell[])atm.getState(), expectedArray);

        atm.withdrawAmount(500);
        expectedArray[1] = new Cell(500, 2);
        atm.saveState();

        atm.withdrawAmount(2 * 500);

        assert !Arrays.equals((Cell[])atm.getState(), expectedArray);

        atm.restore();

        assert Arrays.equals((Cell[])atm.getState(), expectedArray);
    }
}

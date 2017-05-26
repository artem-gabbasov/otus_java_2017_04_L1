import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Artem Gabbasov on 26.05.2017.
 */
public class ATMDepartmentTest {
    @Test
    // тест для непустого множества банкоматов
    public void getRemainderRegular() {
        ATMDepartment department = new ATMDepartment();

        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 4);
        cellsArray[1] = new Cell(500, 3);

        department.add(new ATM(cellsArray));
        department.add(new ATM(cellsArray));

        cellsArray[1] = new Cell(500, 10);

        department.add(new ATM(cellsArray));

        assert department.getRemainder() == 2 * (500 * 3 + 50 * 4) + 500 * 10 + 50 * 4;
    }

    @Test
    // тест для пустого множества банкоматов
    public void getRemainderEmpty() {
        ATMDepartment department = new ATMDepartment();

        assert department.getRemainder() == 0;
    }

    @Test
    // тест для объединения банкоматов и их department'ов
    public void getRemainderComposite() {
        ATMDepartment department = new ATMDepartment();

        Cell[] cellsArray = new Cell[2];
        cellsArray[0] = new Cell(50, 4);
        cellsArray[1] = new Cell(500, 3);

        department.add(new ATM(cellsArray));
        department.add(new ATM(cellsArray));

        cellsArray[1] = new Cell(500, 10);

        department.add(new ATM(cellsArray));

        ATMDepartment total = new ATMDepartment();

        total.add(department);

        cellsArray[0] = new Cell(100, 4);
        cellsArray[1] = new Cell(1000, 3);

        total.add(new ATM(cellsArray));

        assert total.getRemainder() == 2 * (500 * 3 + 50 * 4) + 500 * 10 + 50 * 4 + 1000 * 3 + 100 * 4;
    }

    @Test
    public void restore() {
        ATMDepartment department = new ATMDepartment();

        // Инициализируем банкоматы

        Cell[] cellsArray1 = new Cell[2];
        cellsArray1[0] = new Cell(50, 4);
        cellsArray1[1] = new Cell(500, 3);

        ATM atm1 = new ATM(cellsArray1);
        department.add(atm1);

        Cell[] cellsArray2 = Arrays.copyOf(cellsArray1, cellsArray1.length);
        cellsArray2[1] = new Cell(500, 5);

        ATM atm2 = new ATM(cellsArray2);
        department.add(atm2);

        // Меняем состояние банкоматов

        atm1.withdrawAmount(100);
        atm2.withdrawAmount(500);

        // Восстанавливаем изначальное состояние

        department.restore();

        // Сверяем с запомненным изначальным состоянием

        assert Arrays.equals((Cell[])atm1.getState(), cellsArray1);
        assert Arrays.equals((Cell[])atm2.getState(), cellsArray2);
    }

    @Test
    public void restoreComposite() {
        ATMDepartment department = new ATMDepartment();
        ATMDepartment total = new ATMDepartment();

        // Инициализируем банкоматы

        Cell[] cellsArray1 = new Cell[2];
        cellsArray1[0] = new Cell(50, 4);
        cellsArray1[1] = new Cell(500, 3);

        ATM atm1 = new ATM(cellsArray1);
        department.add(atm1);
        total.add(department);

        Cell[] cellsArray2 = Arrays.copyOf(cellsArray1, cellsArray1.length);
        cellsArray2[1] = new Cell(500, 5);

        ATM atm2 = new ATM(cellsArray2);

        total.add(atm2);

        // Меняем состояние банкоматов

        atm1.withdrawAmount(100);
        atm2.withdrawAmount(500);

        // Восстанавливаем изначальное состояние

        total.restore();

        // Сверяем с запомненным изначальным состоянием

        assert Arrays.equals((Cell[])atm1.getState(), cellsArray1);
        assert Arrays.equals((Cell[])atm2.getState(), cellsArray2);
    }
}

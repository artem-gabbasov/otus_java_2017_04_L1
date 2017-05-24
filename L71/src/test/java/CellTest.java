import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Artem Gabbasov on 24.05.2017.
 */
public class CellTest {
    @Test
    public void withdraw() {
        Cell cell = new Cell(500, 40, null);
        cell.withdraw(10);
        assert cell.getCount() == 30;
    }

    @Test
    public void withdrawFail() {
        Cell cell = new Cell(500, 40, null);

        try {
            cell.withdraw(41);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void getRemainder() {
        Cell cell = new Cell(500, 40, null);
        assert cell.getRemainder() == 500 * 40;
    }

    @Test
    public void withdrawAmountNo() {
        Cell cell = new Cell(500, 40, null);
        assert cell.withdrawAmount(100) == 100; // ничего не выдали
        assert cell.getCount() == 40;
    }

    @Test
    // не хватило купюр
    public void withdrawAmountPart1() {
        Cell cell = new Cell(500, 4, null);
        assert cell.withdrawAmount(10 * 500) == 6 * 500;
        assert cell.getCount() == 0;
    }

    @Test
    // требуемая сумма содержит более мелкую часть, чем номинал купюр
    public void withdrawAmountPart2() {
        Cell cell = new Cell(500, 4, null);
        assert cell.withdrawAmount(1200) == 200;
        assert cell.getCount() == 2;
    }

    @Test
    public void withdrawAmountWhole() {
        Cell cell = new Cell(500, 4, null);
        assert cell.withdrawAmount(1500) == 0;
        assert cell.getCount() == 1;
    }

    @Test
    public void handleWithdrawRequestOneCellTrue() {
        Cell cell = new Cell(500, 4, null);
        cell.handleWithdrawRequest(500 * 3);
        assert cell.getCount() == 1;
    }

    @Test
    public void handleWithdrawRequestOneCellFalse() {
        Cell cell = new Cell(500, 4, null);
        cell.handleWithdrawRequest(500 * 5);
        assert cell.getCount() == 4; // сумма некорректна, ничего не выдали
    }

    @Test
    public void handleWithdrawRequestChainTrue() {
        Cell cell100 = new Cell(100, 3, null);
        Cell cell500 = new Cell(500, 4, cell100);
        cell500.handleWithdrawRequest(500 * 3 + 100 * 1);
        assert (cell500.getCount() == 1 && cell100.getCount() == 2);
    }

    @Test
    public void handleWithdrawRequestChainFalse() {
        Cell cell100 = new Cell(100, 3, null);
        Cell cell500 = new Cell(500, 4, cell100);
        cell500.handleWithdrawRequest(500 * 3 + 100 * 4);
        assert (cell500.getCount() == 4 && cell100.getCount() == 3); // сумма некорректна, ничего не выдали
    }

    @Test
    public void getState() {
        Cell cell = new Cell(500, 4, null);
        assert cell.getState() == Long.valueOf(4);
    }

    @Test
    public void setState() {
        Cell cell = new Cell(500, 4, null);
        cell.setState(5L);
        assert cell.getCount() == 5;
    }
}

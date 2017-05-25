import java.util.Objects;

/**
 * Created by Artem Gabbasov on 24.05.2017.
 *
 * Класс ячейки банкомата, отвечающей за конкретный номинал банкнот.
 * Все суммы целочисленные, т.к. в данной задаче нет цели моделировать работу с копейками
 */
public class Cell implements Restorable, Comparable<Cell> {
    private final long faceValue;
    private long count;
    private Cell next = null;

    /**
     *
     * @param faceValue     Номинал купюр данной ячейки
     * @param initialCount  Изначальное количество купюр
     */
    public Cell(long faceValue, long initialCount) {
        this.faceValue = faceValue;
        count = initialCount;
    }

    public Cell(long faceValue, Object state) {
        this.faceValue = faceValue;
        setState(state);
    }

    public long getFaceValue() {
        return faceValue;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * Задаёт следующую ячейку для Chain of responsibility
     *
     * @param next          Следующая ячейка для Chain of responsibility
     */
    public void setNext(Cell next) {
        this.next = next;
    }

    /**
     * Выдаёт запрашиваемое количество купюр
     *
     * @param countToWithdraw   Количество купюр, которые нужно выдать
     * @throws IllegalArgumentException если запрашивается больше купюр, чем есть в ячейке
     */
    public void withdraw(long countToWithdraw) throws IllegalArgumentException {
        if (countToWithdraw <= count) count -= countToWithdraw;
        else
            throw new IllegalArgumentException("Not enough money to withdraw: " + count + " (needed " + countToWithdraw + ")");
    }

    /**
     * Возвращает остаток суммы в данной ячейке
     *
     * @return Остаток суммы в данной ячейке
     */
    public long getRemainder() {
        return faceValue * count;
    }

    /**
     * Выдаёт запрашиваемую сумму, насколько это возможно
     *
     * @param amount    Запрашиваемая сумма
     * @return          Невыданный остаток суммы (если получается выдать всю сумму купюрами данной ячейки, возвращает 0)
     */
    public long withdrawAmount(long amount) {
        long expectedCount = amount / faceValue; // смотрим, сколько купюр данного номинала нам может потребоваться
        long actualCount = Math.min(expectedCount, count); // выдадим сколько есть, но не больше требуемого количества
        withdraw(actualCount);
        return amount - actualCount * faceValue; // вычисляем и возвращаем остаток
    }

    /**
     * Обрабатывает запрос на выдачу суммы для паттерна Chain of responsibility.
     * Если сумма не может быть выдана, состояние ни одной ячейки не изменяется
     *
     * @param amount    Запрашиваемая сумма
     * @return  true, если сумма может быть выдана. false в противном случае
     */
    public boolean handleWithdrawRequest(long amount) {
        // сохраняем текущее состояние на случай, если понадобится его откатить (если запрошенную сумму не удастся набрать)
        Memento<Cell> m = new Memento<>(this);

        long remainder = withdrawAmount(amount);
        if (remainder == 0) return true;

        /* далее идёт небольшой финт ушами: вместо обычного return false кинем исключение,
            а при его обработке сделаем rollback и таки вернём false.
            Можно было вместо исключения сделать обычный флажок, а в конце его проверить,
             но я решил, что так больше возможностей запутаться, да и с исключением логика получается ближе к натуральной
         */
        try {
            if (next == null) throw new IncorrectChainAmountException();
            if (!next.handleWithdrawRequest(remainder)) throw new IncorrectChainAmountException();
            return true;
        } catch (IncorrectChainAmountException e) {
            m.restore();
            return false;
        }
    }

    @Override
    public Object getState() {
        return count;
    }

    @Override
    public void setState(Object state) {
        setCount((Long)state);
    }

    @Override
    public int compareTo(Cell o) {
        if (o == null) throw new NullPointerException();

        if (faceValue < o.faceValue) return -1;
        if (faceValue > o.faceValue) return 1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Cell cell = (Cell)o;
        return (faceValue == cell.faceValue && this.count == cell.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faceValue, count);
    }
}
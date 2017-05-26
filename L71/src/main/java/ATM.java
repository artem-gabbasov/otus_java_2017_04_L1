import java.util.*;

/**
 * Created by Artem Gabbasov on 25.05.2017.
 *
 * Класс, моделирующий работу банкомата
 *
 */
public class ATM implements Restorable, Maintainable {
    /* отсортированное по возрастанию номиналов начальное состояние ячеек.
        В качестве ключа используется номинал.
        Сортировка нужна, чтобы в нужном порядке набирать купюры
        (сначала пытаемся набрать сумму самыми крупными, потом остаток добираем купюрами помельче и т.д.).
        Сортировка удобнее именно по возрастанию номиналов,
        т.к. для корректного добавления ссылки на следующую ячейку пробегаем их с конца

        В Values будем хранить состояния ячеек
     */
    private SortedMap<Long, Object> cells;
    private Memento canonicalState;

    /**
     * @param cells                         Список изначальных состояний ячеек данного банкомата
     * @throws IllegalArgumentException     если какой-либо номинал встречается дважды
     */
    public ATM(Cell[] cells) throws IllegalArgumentException {
        setState(cells);
        saveState();
    }

    @Override
    public Object getState() {
        List<Cell> list = createCellsList();
        Cell[] result = new Cell[list.size()];
        return list.toArray(result);
    }

    private List<Cell> createCellsList() {
        List<Cell> list = new ArrayList<>(cells.size());
        cells.forEach((faceValue, state) -> list.add(new Cell(faceValue, state)));
        return list;
    }

    @Override
    /**
     * Задаёт состояние банкомата
     *
     * @param state                         состояние данного банкомата (массив задаваемых состояний ячеек)
     * @throws IllegalArgumentException     если какой-либо номинал встречается дважды
     */
    public void setState(Object state) throws IllegalArgumentException {
        if (state == null) {
            cells = new TreeMap<>();
            return;
        }

        Cell[] cellsArray = (Cell[])state;
        cells = new TreeMap<>();
        for (Cell cell : cellsArray) cells.put(cell.getFaceValue(), cell.getState());
        if (cells.size() < cellsArray.length) throw new IllegalArgumentException("There are duplicates");
    }

    /**
     * Задаёт состояние для ячейки, задаваемой номиналом.
     * Если ячейки с заданным номиналом нет, то она добавляется
     *
     * @param faceValue номинал ячейки, которую надо обновить
     * @param state     задаваемое состояние
     */
    public void updateCell(long faceValue, Object state) {
        cells.put(faceValue, state);
    }

    /**
     * Выдаёт запрошенную пользователем сумму, если это возможно
     *
     * @param amount                        запрошенная пользователем сумма
     * @throws IllegalArgumentException     если выдать запрошенную сумму невозможно
     */
    public void withdrawAmount(long amount) throws IllegalArgumentException {
        List<Cell> list = createCellsList();
        Cell chain = getChainOfResponsibility(list);

        if (chain == null) throw new IllegalArgumentException("ATM has no notes");

        if (!chain.handleWithdrawRequest(amount)) throw new IllegalArgumentException("Incorrect amount");
        else {
            Cell[] state = new Cell[list.size()];
            setState(list.toArray(state));
        }
    }

    /**
     * Строит Chain of Responsibility по заданным для данного ATM ячейкам
     *
     * @param list  список ячеек, из которых будет состоять цепочка (предполагается, что он отсортирован по возрастанию)
     * @return      первая ячейка цепи (с наибольшим номиналом)
     */
    private Cell getChainOfResponsibility(List<Cell> list) {
        Cell previous = null; // в качестве next мы задаём предыдущую ячейку, т.к. мы разворачиваем список (он упорядочен по возрастанию номинала, а цепочка начинается с самых крупных купюр)
        for (Cell cell : list) {
            cell.setNext(previous);
            previous = cell;
        }
        return previous; // последняя пройденная нами ячейка должна стать первой в искомой цепочке
    }

    /**
     * Возвращает остаток суммы в банкомате
     *
     * @return Остаток суммы в банкомате
     */
    public long getRemainder() {
        List<Cell> list = createCellsList();

        if (list == null || list.size() == 0) return 0;

        return list.stream()
                .mapToLong(Cell::getRemainder)
                .sum();
    }

    public void saveState() {
        canonicalState = new Memento(this);
    }

    public void restore() {
        canonicalState.restore();
    }

    @Override
    public void onAddition() {
        saveState();
    }
}

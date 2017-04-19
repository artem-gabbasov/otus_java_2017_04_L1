package ru.otus.l32;

import java.util.*;

/**
 * Created by Artem Gabbasov on 17.04.2017.
 */
class MyArrayList<T> implements List<T> {
    // Изначальная длина внутреннего массива (если ничего не задано)
    static final int DEFAULT_CAPACITY = 10;

    // Значение, на которое увеличивается длина внутреннего массива, когда в нём не хватает места.
    // Можно было бы использовать общую константу для создания массива и для расширения, но так выходит чуть гибче
    static final int CAPACITY_MAGIC_NUMBER = DEFAULT_CAPACITY;

    private T[] theArray;
    private int size;

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(int capacity) {
        theArray = (T[]) new Object[capacity];
        size = 0;
    }

    private class MyIterator implements Iterator<T> {
        protected int position; // задаёт индекс последнего пройденного элемента, точнее, того, который слева от положения итератора (изначально -1)
        protected boolean positionChanged; // нужно для проверки условия при выполнении операций (в частности, удаление). Признак того, что с момента последней операции был осуществлён переход

        public MyIterator() {
            position = -1;
            positionChanged = false;
        }

        @Override
        public boolean hasNext() {
            return position < size() - 1;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            positionChanged = true;
            return get(++position);
        }

        @Override
        public void remove() {
            doOperation(() -> MyArrayList.this.remove(position--), true);
        }

        // производит операцию над последним возвращённым элементом
        // если affectsIndex == true, запрещает производить дальнейшие операции до следующего перемещения
        protected void doOperation(Runnable operation, boolean affectsPosition) {
            if (!positionChanged) throw new IllegalStateException();
            operation.run();
            if (affectsPosition) positionChanged = false;
        }
    }

    private class MyListIterator extends MyIterator implements ListIterator<T> {
        // направление последнего перемещения (нужно для операций - удаление, добавление, изменение)
        // true - последнее перемещение - previous
        // false - последнее перемещение - next (по умолчанию)
        private boolean isReversed;

        public MyListIterator() {
            super();
            isReversed = false;
        }

        @Override
        public T next() {
            T res = super.next();
            isReversed = false;
            return res;
        }

        @Override
        public boolean hasPrevious() {
            return position >= 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            positionChanged = true;
            isReversed = true;
            return get(--position);
        }

        @Override
        public int nextIndex() {
            return position + 1;
        }

        @Override
        public int previousIndex() {
            return position;
        }

        // возвращает позицию для операций удаления и изменения с учётом перехода вперед/назад
        private int actualIndex() {
            if (isReversed) {
                return nextIndex(); // пришли справа
            } else {
                return previousIndex(); // пришли слева
            }
        }

        @Override
        public void remove() {
            doOperation(() -> MyArrayList.this.remove(actualIndex()), true);
            if (!isReversed) position--;
        }

        @Override
        public void set(T t) {
            doOperation(() -> MyArrayList.this.set(actualIndex(), t), false);
        }

        @Override
        public void add(T t) {
            doOperation(() -> MyArrayList.this.add(++position, t), true);
        }
    }

    // Если надо, расширяет массив, чтобы вместить дополнительные элементы
    private void checkAndExtend() {
        if (size() >= theArray.length) {
            theArray = Arrays.copyOf(theArray, theArray.length + CAPACITY_MAGIC_NUMBER);
        }
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > theArray.length) {
            theArray = Arrays.copyOf(theArray, minCapacity);
        }
    }

    public void trimToSize() {
        theArray = Arrays.copyOf(theArray, size());
    }

    //+
    @Override
    public int size() {
        return size;
    }

    //+
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public boolean contains(Object o) {
        return false;
    }

    //+
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    //+
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(theArray, size());
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    //+
    @Override
    public boolean add(T t) {
        checkAndExtend();
        theArray[size++] = t;
        return true;
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public boolean remove(Object o) {
        return false;
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size(), c);
    }

    //+
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(); // в случае index == size исключение не кидается, т.к. просто добавляем в конец списка
        if (c == null) throw new NullPointerException();
        int initialSize = size();

        ensureCapacity(size() + c.size());

        performAndShiftElements(index, () -> {
            System.arraycopy(c.toArray(), 0, theArray, index, c.size());
            size = index + c.size();
        });

        return size() > initialSize;
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    //+
    @Override
    public void clear() {
        size = 0; // от нас требуется перейти к состоянию isEmpty() == true
    }

    //+
    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        return theArray[index];
    }

    //+
    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        T res = theArray[index];
        theArray[index] = element;
        return res;
    }

    // выполняет операцию, сдвигая элементы от заданного индекса до конца массива
    private void performAndShiftElements(int index, Runnable operation) {
        T[] tmpArray = Arrays.copyOfRange(theArray, index, size());
        operation.run();
        ensureCapacity(size() + tmpArray.length);
        System.arraycopy(tmpArray, 0, theArray, size(), tmpArray.length);
        size += tmpArray.length;
    }

    //+
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(); // в случае index == size исключение не кидается, т.к. просто добавляем в конец списка
        performAndShiftElements(index, () -> {
            // забываем все элементы после index, т.к. мы их добавим после выполнения этой операции
            size = index + 1;
            set(index, element);
        });
    }

    //+
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        T res = get(index);
        performAndShiftElements(index + 1, () -> {
            // просто затираем данный элемент
            size = index;
        });
        return res;
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public int indexOf(Object o) {
        return 0;
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator();
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    // НЕ РЕАЛИЗОВАН!!!
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}

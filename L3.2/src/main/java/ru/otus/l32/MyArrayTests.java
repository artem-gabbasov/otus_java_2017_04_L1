package ru.otus.l32;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Artem Gabbasov on 17.04.2017.
 */
public class MyArrayTests {
    public static void Tests() {
        MyArrayList<Integer> intArray = new MyArrayList<>();
        intArray.add(78);
        assert intArray.size() == 1;
        assert intArray.get(0) == 78;

        MyArrayList<String> stringArray = new MyArrayList<>(3);
        stringArray.add("1");
        stringArray.add("2");
        stringArray.add("3");
        stringArray.add("4");
        assert stringArray.size() == 4;
        assert stringArray.get(2).equals("3");

        MyArrayList<Number> nArray = new MyArrayList<>();
        assert nArray.addAll(intArray); // здесь непустой список, должны вернуть true
        assert nArray.size() == intArray.size();
        assert nArray.get(0).intValue() == 78;

        try {
            nArray.addAll(null);
            assert false; // должны были провалиться в catch
        } catch (NullPointerException e) {
            // ok
        }

        LinkedList<Number> emptyList = new LinkedList<>();
        assert !nArray.addAll(emptyList); // пустой список, должны вернуть false

        //--------------------------------------------------------------------------------------------------------------
        //------------------------------- МЕТОДЫ ИЗ ЗАДАНИЯ ------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        assert Collections.addAll(intArray, 2, 3, 4, 5); // должны вернуть true
        // intArray = {78, 2, 3, 4, 5}
        assert intArray.size() == 5;
        assert intArray.get(3) == 4;

        // nArray = {78}
        // intArray = {78, 2, 3, 4, 5}
        try {
            Collections.copy(nArray, intArray);
            assert false; // Должны провалиться в catch, т.к. dest меньше, чем src
        } catch (IndexOutOfBoundsException e) {
            //ok
        }

        assert Collections.addAll(nArray, 2.2, 3.3, 4.4, 5.5, 6.6); // должны вернуть true
        // nArray = {78, 2.2, 3.3, 4.4, 5.5, 6.6}
        assert nArray.size() == 6;
        // intArray = {78, 2, 3, 4, 5}
        Collections.copy(nArray, intArray);
        assert nArray.size() == 6;
        assert nArray.get(1).intValue() == 2;
        // nArray = {78, 2, 3, 4, 5, 6.6}

        Collections.sort(intArray, null);
        // intArray = {2, 3, 4, 5, 78}
        assert intArray.get(2) == 4;
        assert intArray.get(4) == 78;

        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        intArray.add(0, 1);
        // intArray = {1, 2, 3, 4, 5, 78}
        assert intArray.size() == 6;
        assert intArray.get(0) == 1;
        assert intArray.get(1) == 2;

        intArray.add(2, 20);
        // intArray = {1, 2, 20, 3, 4, 5, 78}
        assert intArray.size() == 7;
        assert intArray.get(1) == 2;
        assert intArray.get(2) == 20;
        assert intArray.get(3) == 3;

        intArray.add(7, 80);
        // intArray = {1, 2, 20, 3, 4, 5, 78, 80}
        assert intArray.size() == 8;
        assert intArray.get(6) == 78;
        assert intArray.get(7) == 80;

        intArray.remove(2);
        // intArray = {1, 2, 3, 4, 5, 78, 80}
        assert intArray.size() == 7;
        assert intArray.get(1) == 2;
        assert intArray.get(2) == 3;

        // nArray = {78, 2, 3, 4, 5, 6.6}
        assert nArray.addAll(2, intArray);
        // nArray = {78, 2, 1, 2, 3, 4, 5, 78, 80, 3, 4, 5, 6.6}
        assert nArray.size() == 13;
        assert nArray.get(1).intValue() == 2;
        assert nArray.get(2).intValue() == 1;
        assert nArray.get(8).intValue() == 80;
        assert nArray.get(9).intValue() == 3;

        // теперь добавим один массив в конец другого
        assert nArray.addAll(13, intArray);
        // nArray = {78, 2, 1, 2, 3, 4, 5, 78, 80, 3, 4, 5, 6.6, 1, 2, 3, 4, 5, 78, 80}
        assert nArray.size() == 20;
        assert nArray.get(13).intValue() == 1;
        assert nArray.get(19).intValue() == 80;
    }
}
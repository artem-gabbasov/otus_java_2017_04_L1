package ru.otus.l41;

import java.util.*;

/**
 * Created by Artem Gabbasov on 27.04.2017.
 *
 * Класс, используемый для создания мусора (т.е. имитации выполнения какой-то программы)
 *
 * Заполняет лист элементами, потом удаляет половину из них
 */
public class GarbageCreator {
    private List<Integer> list;
    // удобнее хранить половину размера, т.к. именно половину мы оставляем и половину удаляем
    private int halfSize = 1_000_000;

    public GarbageCreator() {
        list = new ArrayList<>();
    }

    public void run() throws InterruptedException {
        while (true) {
            System.out.println("Adding elements...");

            // половину заполняем числом 42, половину - числом 43
            list.addAll(Collections.nCopies(halfSize, 42));
            list.addAll(Collections.nCopies(halfSize, 43));
            // теперь удаляем все элементы, равные 43
            list.removeAll(Collections.singleton(43));
            // ждём, чтобы упасть примерно через 5 минут

            System.out.println("List size is " + list.size() + "\n" +
                "Free memory: " + Runtime.getRuntime().freeMemory()
            );

            Thread.sleep(5_000);

            System.out.println("Awaken");
        }
    }
}

package ru.otus.l21;

import java.lang.management.ManagementFactory;

/**
 * Created by tully. (стартовый код для задания)
 */
//VM options -Xmx1024m -Xms1024m
// взял гигабайт, чтобы вместить побольше объектов и снизить погрешности
public class Main {
    static long prev = -1;
    static Runtime runtime;

    static int size = 10 * 1024 * 1024;

    public static void main(String... args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        runtime = Runtime.getRuntime();

        reportMemory();

        Object[] array = new Object[size];
        System.out.println("Array of size: " + array.length + " created");
        Thread.sleep(10 * 1000);

        int n = 0;
        System.out.println("Starting the loop");

        // Здесь можно увидеть, сколько занимает ссылка на каждый объект
        // У меня получилось 4 байта без отключения сжатия ссылок (у меня 64-битная система)
        // При отключении сжатия ссылок здесь чётко видно 8 байт на объект
        reportMemory();

        while (n < Integer.MAX_VALUE) {
            int i = n % size;

            // Тесты для определения количества памяти, занимаемой тем или иным объектом
            // Размеры ссылок на сами объекты, хранящиеся в массиве, здесь никак не сказываются
            // В комментариях указал полученные у себя результаты (система у меня 64-битная) и выводы

//            array[i] = new Object(); // 16 байт
            // Т.к. у меня 64-битная система, а сжатие ссылок я не отключал, то:
            // mark word занимает 8 байт
            // klass pointer занимает 4 байта (сжатый)
            // Итого 12 байт, после выравнивания 16
            // Запускал этот тест без сжатия ссылок, убедился, что получаю всё те же 16 байт, только 4 байта теперь не съедаются выравниванием

            // ----------Тесты для массивов----------
//            array[i] = new Object[0]; // 16 байт
            // 12 байт заголовок (см. выше - предыдущий пример)
            // 4 байта длина
            // Итого 16 байт, выравнивание ни на что не влияет
            // Запускал этот тест без сжатия ссылок, убедился, что получаю 24 байта

//            array[i] = new Object[1]; // 24 байта
            // 16 байт на сам объект массива (см. выше - предыдущий пример)
            // 4 байта одна ссылка
            // Итого 20 байт, после выравнивания 24

//            array[i] = new Object[5]; // 40 байт
            // 16 байт на сам объект массива
            // 20 байт пять ссылок
            // Итого 36 байт, после выравнивания 40

//            array[i] = new Object[10]; // 56 байт
            // 16 байт на сам объект массива
            // 40 байт десять ссылок
            // Итого 56 байт, выравнивание ни на что не влияет

//            array[i] = new Object[11]; // 64 байта
            // 16 байт на сам объект массива
            // 44 байта одиннадцать ссылок
            // Итого 60 байт, после выравнивания 64

            // ----------Тесты для строк-------------
//            array[i] = ""; // 0 байт
            // Видимо, для пустой строки никаких объектов не создаётся

//            array[i] = new String(""); // 24 байта
            // 12 байт заголовок
            // 4 байта поле hash (нестатическое)
            // 4 байта ссылка на массив типа char
            // Итого 20 байт, после выравнивания 24
            // Запускал этот тест без сжатия ссылок, убедился, что получаю 32 байта (по 4 байта за klass pointer и ссылку на массив)

//            array[i] = new String("A"); // 24 байта
//            array[i] = new String("ABCDEFGHIJKL"); // 24 байта
            // Прироста используемой памяти не наблюдается, видимо, константы закешированы (пул)

            // Вносим немного разнообразия
//            array[i] = new String(Integer.toString(i % 10)); // 48 байт
            // 24 байта на объект строки
            // 16 байт на объект массива типа char
            // 2 байта на один символ
            // Итого 42 байта, после выравнивания 48
            // Запускал этот тест без сжатия ссылок, убедился, что получаю 64 байта (по 8 байтов прибавляют объекты строки и массива)

            n++;
            if (n % 1024 == 0) {
                Thread.sleep(1);
            }
            if (n % size == 0) {
                System.out.println("Created " + n + " objects");

                reportMemory();

                System.out.println("Creating new array");
                array = new Object[size];

                System.out.println("Calling gc");
                System.gc();

                reportMemory();
            }
        }
    }

    private static void reportMemory(){
        // Определяем текущее количество свободной памяти
        long current = runtime.freeMemory();

        // Определяем количество памяти, занятой с последнего измерения
        long diff = prev < 0 ? 0 : prev - current;

        // Определяем (примерное) количество памяти, занятой одним объектом
        double perObj = diff / (double)size;

        // Округляем результат до целого
        long rounded = Math.round(perObj);

        System.out.println("Free memory: " + current + "; Rounded: " + rounded + "; (diff with previous: " + diff + ", per obj: " + perObj + ")");

        prev = current;
    }
}

package ru.otus.anytype;

/**
 * Created by Artem Gabbasov on 08.07.2017.
 * <p>
 * Исключение, возникающее в геттерах и сеттерах (пользовательское исключение)
 */
public class ValueException extends Exception {
    public ValueException(Throwable t) {
        super(t);
    }
}

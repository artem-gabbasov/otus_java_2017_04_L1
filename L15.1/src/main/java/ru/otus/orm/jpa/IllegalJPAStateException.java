package ru.otus.orm.jpa;

/**
 * Created by Artem Gabbasov on 01.07.2017.
 * <p>
 */
@SuppressWarnings("WeakerAccess")
public class IllegalJPAStateException extends JPAException {
    public IllegalJPAStateException(String message) {
        super(message);
    }
}

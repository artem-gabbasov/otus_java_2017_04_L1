package ru.otus;

/**
 * Created by Artem Gabbasov on 25.05.2017.
 *
 * Интерфейс для классов, способных сохранять и загружать свои состояния
 *
 */
interface Restorable<T> {
    T getState();
    void setState(T state);
}

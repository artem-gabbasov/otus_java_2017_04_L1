package ru.otus.cache;

/**
 * Created by Artem Gabbasov on 18.07.2017.
 * <p>
 * Интерфейс для обработки обращения к idle элементам
 */
@SuppressWarnings("WeakerAccess")
public interface IdleManager {
    /**
     * Оповещение о том, что к элементу произведён доступ
     */
    void refresh();
}

package ru.otus.cache.impl;

import ru.otus.cache.IdleManager;

/**
 * Created by Artem Gabbasov on 18.07.2017.
 * <p>
 * Реализация интерфейса {@link IdleManager}, которая не предусматривает никаких действий над idle элементами
 */
public class EmptyIdleManager implements IdleManager {
    @Override
    public void refresh() {
    }
}

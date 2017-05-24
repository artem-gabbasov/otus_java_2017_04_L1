/**
 * Created by Artem Gabbasov on 25.05.2017.
 *
 * Интерфейс для классов, способных сохранять и загружать свои состояния
 *
 */
interface Restorable {
    Object getState();
    void setState(Object state);
}

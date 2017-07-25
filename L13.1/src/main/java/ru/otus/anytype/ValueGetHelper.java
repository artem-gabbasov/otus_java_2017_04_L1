package ru.otus.anytype;

import ru.otus.anytype.getters.GeneralValueGetter;
import ru.otus.anytype.getters.ValueGetter;

/**
 * Created by Artem Gabbasov on 22.06.2017.
 * <p>
 * Вспомогательный класс, возвращающий значения произвольного типа
 */
public class ValueGetHelper {
    private Object result;

    /**
     * Возвращает значение произвольного типа
     * @param valueGetter                   объект, возвращающий значение
     * @param clazz                         класс возвращаемого значения
     * @return                              возвращаемое из объекта значение
     * @throws UnsupportedTypeException     если заданный класс не поддерживается
     * @throws ValueException               если в переданном объекте возникает исключение
     */
    public Object accept(ValueGetter valueGetter, Class<?> clazz) throws UnsupportedTypeException, ValueException {
        if ((valueGetter instanceof GeneralValueGetter) && acceptGeneral((GeneralValueGetter) valueGetter, clazz)) return result;
        if (acceptCustom(valueGetter, clazz)) return result;
        return valueGetter.getObject();
    }

    /**
     * Возвращает значение произвольного типа среди основных типов
     * @param valueGetter                   объект, возвращающий значение
     * @param clazz                         класс возвращаемого значения
     * @return                              возвращаемое из объекта значение
     * @throws UnsupportedTypeException     если заданный класс не поддерживается
     * @throws ValueException               если в переданном объекте возникает исключение
     */
    private boolean acceptGeneral(GeneralValueGetter valueGetter, Class<?> clazz) throws UnsupportedTypeException, ValueException {
        if (int.class == clazz || Integer.class == clazz) result = valueGetter.getInt(); else
        if (byte.class == clazz || Byte.class == clazz) result = valueGetter.getByte(); else
        if (short.class == clazz || Short.class == clazz) result = valueGetter.getShort(); else
        if (long.class == clazz || Long.class == clazz) result = valueGetter.getLong(); else
        if (boolean.class == clazz || Boolean.class == clazz) result = valueGetter.getBoolean(); else
        if (double.class == clazz || Double.class == clazz) result = valueGetter.getDouble(); else
        if (float.class == clazz || Float.class == clazz) result = valueGetter.getFloat(); else
        if (char.class == clazz || Character.class == clazz) result = valueGetter.getChar(); else
        if (String.class == clazz) result = valueGetter.getString(); else
            return false;

        return true;
    }

    /**
     * Обрабатывает прочие типы (не входящие в основные). Добавлен для перегрузки в наследниках
     * @param valueGetter                   объект, возвращающий значение
     * @param clazz                         класс возвращаемого значения
     * @return                              возвращаемое из объекта значение
     * @throws UnsupportedTypeException     если заданный класс не поддерживается
     * @throws ValueException               если в переданном объекте возникает исключение
     */
    @SuppressWarnings({"WeakerAccess", "SameReturnValue", "unused"})
    protected boolean acceptCustom(ValueGetter valueGetter, Class<?> clazz) throws UnsupportedTypeException, ValueException {
        return false;
    }
}

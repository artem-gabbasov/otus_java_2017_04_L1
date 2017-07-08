package ru.otus.anytype;

import ru.otus.anytype.setters.GeneralValueSetter;
import ru.otus.anytype.setters.ValueSetter;

import java.util.Objects;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 * Вспомогательный класс, задающий значения произвольного типа
 */
public class ValueSetHelper {
    /**
     * Задаёт значение произвольного типа
     * @param valueSetter                   объект, задающий значение
     * @param value                         задаваемое значение
     * @throws UnsupportedTypeException     если класс заданного значения не поддерживается
     * @throws ValueException               если в переданном объекте возникает исключение
     */
    public void accept(ValueSetter valueSetter, Object value) throws UnsupportedTypeException, ValueException {
        Objects.requireNonNull(value);

        if ((valueSetter instanceof GeneralValueSetter) && acceptGeneral((GeneralValueSetter) valueSetter, value)) return;
        if (acceptCustom(valueSetter, value)) return;
        valueSetter.setObject(value);
    }

    /**
     * Задаёт значение произвольного типа среди основных типов
     * @param valueSetter                   объект, задающий значение
     * @param value                         задаваемое значение
     * @return                              признак, указывающий найден ли подходящий метод для задания значения
     * @throws UnsupportedTypeException     если класс заданного значения не поддерживается
     * @throws ValueException               если в переданном объекте возникает исключение
     */
    private boolean acceptGeneral(GeneralValueSetter valueSetter, Object value) throws UnsupportedTypeException, ValueException {
        Class<?> clazz = value.getClass();

        if (int.class == clazz || Integer.class == clazz) valueSetter.setInt((int)value); else
        if (byte.class == clazz || Byte.class == clazz) valueSetter.setByte((byte)value); else
        if (short.class == clazz || Short.class == clazz) valueSetter.setShort((short)value); else
        if (long.class == clazz || Long.class == clazz) valueSetter.setLong((long)value); else
        if (boolean.class == clazz || Boolean.class == clazz) valueSetter.setBoolean((boolean)value); else
        if (double.class == clazz || Double.class == clazz) valueSetter.setDouble((double)value); else
        if (float.class == clazz || Float.class == clazz) valueSetter.setFloat((float)value); else
        if (char.class == clazz || Character.class == clazz) valueSetter.setChar((char)value); else
        if (String.class == clazz) valueSetter.setString((String)value); else
            return false;

        return true;
    }

    /**
     * Обрабатывает прочие типы (не входящие в основные). Добавлен для перегрузки в наследниках
     * @param valueSetter                   объект, задающий значение
     * @param value                         задаваемое значение
     * @return                              признак, указывающий найден ли подходящий метод для задания значения
     * @throws UnsupportedTypeException     если класс заданного значения не поддерживается
     * @throws ValueException               если в переданном объекте возникает исключение
     */
    @SuppressWarnings({"WeakerAccess", "SameReturnValue", "unused"})
    protected boolean acceptCustom(ValueSetter valueSetter, Object value) throws UnsupportedTypeException, ValueException {
        return false;
    }
}

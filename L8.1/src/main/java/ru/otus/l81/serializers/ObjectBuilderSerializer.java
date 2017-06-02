package ru.otus.l81.serializers;

import ru.otus.l81.modelbuilders.ArrayModelBuilder;
import ru.otus.l81.modelbuilders.ModelBuilder;
import ru.otus.l81.modelbuilders.ObjectModelBuilder;

import javax.json.*;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 01.06.2017.
 * <p>
 * Сериализатор объектов, пробегающий по полям объекта
 */
public class ObjectBuilderSerializer implements Serializer {
    private final JsonObjectBuilder contents;

    public ObjectBuilderSerializer(Object o) throws IllegalAccessException {
        contents = serialize(o);
    }

    @Override
    public void addToObjectBuilder(JsonObjectBuilder model, String name) {
        model.add(name, contents);
    }

    @Override
    public void addToArrayBuilder(JsonArrayBuilder model) {
        model.add(contents);
    }

    @Override
    public String getAsString() {
        return writeToString(contents.build());
    }

    private static String writeToString(JsonObject jsonst) {
        StringWriter stWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
            jsonWriter.writeObject(jsonst);
        }

        return stWriter.toString();
    }

    /**
     * Основная часть кода, которая пробегается по объектам и сериализует их по очереди
     * @param o                         объект для сериализации
     * @return                          билдер, созданный по объекту
     * @throws IllegalAccessException   если нет доступа к какому-либо из полей
     */
    private JsonObjectBuilder serialize(Object o) throws IllegalAccessException {
        JsonObjectBuilder model = Json.createObjectBuilder();

        for (Field field : o.getClass().getDeclaredFields()) {
            if ((field.getModifiers() & Modifier.TRANSIENT) > 0) continue;

            boolean isAccessible = true;
            try {
                isAccessible = field.isAccessible();
                field.setAccessible(true);
                processOneField(model, field, field.get(o));
            } finally {
                if (!isAccessible) {
                    field.setAccessible(false);
                }
            }
        }

        return model;
    }

    /**
     * Определяет фактический тип поля (либо элемента массива) и добавляет его в модель json'а
     * @param modelBuilder              интерфейс, призванный обеспечить единый интерфейс для полей объектов и элементов массивов
     * @param clazz                     тип добавляемого значения
     * @param value                     добавляемое значение
     * @throws IllegalAccessException   если добавляется объект, к какому-то из полей которого нет доступа
     */
    private void addValueToModel(ModelBuilder modelBuilder, Class<?> clazz, Object value) throws IllegalAccessException {
        if (int.class == clazz || byte.class == clazz || short.class == clazz || Integer.class == clazz || Byte.class == clazz || Short.class == clazz) modelBuilder.addToModel(((Number) value).intValue()); else
        if (long.class == clazz || Long.class == clazz) modelBuilder.addToModel((long) value); else
        if (boolean.class == clazz || Boolean.class == clazz) modelBuilder.addToModel((boolean) value); else
        if (double.class == clazz || float.class == clazz || Double.class == clazz || Float.class == clazz) modelBuilder.addToModel(((Number) value).doubleValue()); else
        if (char.class == clazz || Character.class == clazz) modelBuilder.addToModel(value.toString()); else
        if (String.class == clazz) modelBuilder.addToModel((String) value); else
        if (BigInteger.class.isAssignableFrom(clazz)) modelBuilder.addToModel((BigInteger) value); else
        if (BigDecimal.class.isAssignableFrom(clazz)) modelBuilder.addToModel((BigDecimal) value); else
        if (checkForArray(clazz)) modelBuilder.addToModel(processArray(getArray(clazz, value))); else
        if (Map.class.isAssignableFrom(clazz)) modelBuilder.addToModel(processMap((Map)value)); else
        // иначе обрабатываем просто как Object
        modelBuilder.addToModel(new SerializerFactoryImpl().createSerializer(value));
    }

    private void processOneField(JsonObjectBuilder model, Field field, Object fieldValue) throws IllegalAccessException {
        if (fieldValue == null) return;
        addValueToModel(new ObjectModelBuilder(model, field.getName()), field.getType(), fieldValue);
    }

    private void processArrayElement(JsonArrayBuilder model, Object element) throws IllegalAccessException {
        if (element == null) return;
        addValueToModel(new ArrayModelBuilder(model), element.getClass(), element);
    }

    private boolean checkForArray(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    private Object getArray(Class<?> clazz, Object o) throws IllegalArgumentException {
        if (clazz.isArray()) return o;
        if (Collection.class.isAssignableFrom(clazz)) return ((Collection) o).toArray();
        throw new IllegalArgumentException("Unknown array type: " + clazz.getName());
    }

    /**
     * Пробегает элементы массива и добавляет их в модель json'а
     * @param array                     массив, элементы которого необходимо пробежать
     * @return                          билдер, созданный по массиву
     * @throws IllegalAccessException   если какой-либо элемент содержит объект, к какому-то из полей которого нет доступа
     */
    private JsonArrayBuilder processArray(Object array) throws IllegalAccessException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        int length = Array.getLength(array);
        for (int i = 0; i < length; i++)
        {
            processArrayElement(arrayBuilder, Array.get(array, i));
        }

        return arrayBuilder;
    }

    /**
     * Пробегает Map и сериализует её
     * @param map                       map, элементы которой необходимо пробежать
     * @return                          билдер, созданный по map
     * @throws IllegalAccessException   если какой-либо элемент содержит объект, к какому-то из полей которого нет доступа
     */
    private JsonObjectBuilder processMap(Map<?,?> map) throws IllegalAccessException {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        for (Map.Entry entry : map.entrySet()) {
            Object value = entry.getValue();
            addValueToModel(new ObjectModelBuilder(builder, entry.getKey().toString()), value.getClass(), value);
        }

        return builder;
    }
}
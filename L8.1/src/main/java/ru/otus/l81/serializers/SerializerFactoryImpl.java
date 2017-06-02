package ru.otus.l81.serializers;

/**
 * Created by Artem Gabbasov on 02.06.2017.
 * <p>
 */
public class SerializerFactoryImpl implements SerializerFactory {
    @Override
    public Serializer createSerializer(Object o) throws IllegalAccessException {
        if (isToStringPreferred(o.getClass())) {
            return new StringSerializer(o);
        } else {
            return new ObjectBuilderSerializer(o);
        }
    }

    private boolean isToStringPreferred(Class<?> clazz) {
        return clazz.isEnum();
    }
}

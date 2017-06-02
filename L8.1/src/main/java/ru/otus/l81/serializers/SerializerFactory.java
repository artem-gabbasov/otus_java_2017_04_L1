package ru.otus.l81.serializers;

/**
 * Created by Artem Gabbasov on 01.06.2017.
 * <p>
 */
@SuppressWarnings("WeakerAccess")
public interface SerializerFactory {
    @SuppressWarnings("unused")
    Serializer createSerializer(Object o) throws IllegalAccessException;
}

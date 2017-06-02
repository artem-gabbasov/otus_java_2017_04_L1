package ru.otus.l81.modelbuilders;

import ru.otus.l81.serializers.Serializer;

import javax.json.JsonArrayBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Artem Gabbasov on 02.06.2017.
 * <p>
 * Посредник между моим кодом и javax.json.
 * Нужен для реализации единого интерфейса добавления значений в JsonArrayBuilder (где не требуется имени поля) и
 * JsonObjectBuilder (где требуется имя поля)
 */
public interface ModelBuilder {
    void addToModel(int value);
    void addToModel(long value);
    void addToModel(boolean value);
    void addToModel(double value);
    void addToModel(String value);
    void addToModel(BigInteger value);
    void addToModel(BigDecimal value);
    void addToModel(JsonArrayBuilder value);
    void addToModel(Serializer value);
}

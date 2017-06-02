package ru.otus.l81.serializers;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;


/**
 * Created by Artem Gabbasov on 31.05.2017.
 * <p>
 * Сериализатор объектов
 */
public interface Serializer {
    void addToObjectBuilder(JsonObjectBuilder model, String name);
    void addToArrayBuilder(JsonArrayBuilder model);
    String getAsString();
}

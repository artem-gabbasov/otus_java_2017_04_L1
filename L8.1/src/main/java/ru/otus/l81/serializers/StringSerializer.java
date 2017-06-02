package ru.otus.l81.serializers;

import javax.json.*;

/**
 * Created by Artem Gabbasov on 01.06.2017.
 * <p>
 * Сериализатор объектов, просто вызывающий их метод toString()
 */
public class StringSerializer implements Serializer {
    private final String contents;

    public StringSerializer(Object o) {
        contents = o.toString();
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
        return contents;
    }
}

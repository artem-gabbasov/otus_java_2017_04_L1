package ru.otus.l81.modelbuilders;

import ru.otus.l81.serializers.Serializer;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Artem Gabbasov on 02.06.2017.
 * <p>
 */
public class ObjectModelBuilder implements ModelBuilder {
    private final JsonObjectBuilder model;
    private final String name;

    public ObjectModelBuilder(JsonObjectBuilder model, String name) {
        this.model = model;
        this.name = name;
    }

    @Override
    public void addToModel(int value) {
        model.add(name, value);
    }

    @Override
    public void addToModel(long value) {
        model.add(name, value);
    }

    @Override
    public void addToModel(boolean value) {
        model.add(name, value);
    }

    @Override
    public void addToModel(double value) {
        model.add(name, value);
    }

    @Override
    public void addToModel(String value) {
        model.add(name, value);
    }

    @Override
    public void addToModel(BigInteger value) {
        model.add(name, value);
    }

    @Override
    public void addToModel(BigDecimal value) {
        model.add(name, value);
    }

    @Override
    public void addToModel(JsonArrayBuilder value) {
        model.add(name, value);
    }

    @Override
    public void addToModel(Serializer value) {
        value.addToObjectBuilder(model, name);
    }
}

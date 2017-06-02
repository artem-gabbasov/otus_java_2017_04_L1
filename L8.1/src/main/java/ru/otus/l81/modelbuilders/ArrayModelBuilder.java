package ru.otus.l81.modelbuilders;

import ru.otus.l81.serializers.Serializer;

import javax.json.JsonArrayBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Artem Gabbasov on 02.06.2017.
 * <p>
 */
public class ArrayModelBuilder implements ModelBuilder {
    private final JsonArrayBuilder model;

    public ArrayModelBuilder(JsonArrayBuilder model) {
        this.model = model;
    }

    @Override
    public void addToModel(int value) {
        model.add(value);
    }

    @Override
    public void addToModel(long value) {
        model.add(value);
    }

    @Override
    public void addToModel(boolean value) {
        model.add(value);
    }

    @Override
    public void addToModel(double value) {
        model.add(value);
    }

    @Override
    public void addToModel(String value) {
        model.add(value);
    }

    @Override
    public void addToModel(BigInteger value) {
        model.add(value);
    }

    @Override
    public void addToModel(BigDecimal value) {
        model.add(value);
    }

    @Override
    public void addToModel(JsonArrayBuilder value) {
        model.add(value);
    }

    @Override
    public void addToModel(Serializer value) {
        value.addToArrayBuilder(model);
    }
}

package ru.otus.l81.classes;

/**
 * Created by Artem Gabbasov on 01.06.2017.
 * <p>
 */
@SuppressWarnings({"CanBeFinal", "unused"})
public class SimpleObject {
    @SuppressWarnings("FieldCanBeLocal")
    private int val = 4;
    private ComplexObject nullObj = null;

    @Override
    public String toString() {
        return "val = " + val;
    }
}

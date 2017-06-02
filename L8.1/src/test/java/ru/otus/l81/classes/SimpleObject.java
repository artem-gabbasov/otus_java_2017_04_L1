package ru.otus.l81.classes;

/**
 * Created by Artem Gabbasov on 01.06.2017.
 * <p>
 */
@SuppressWarnings({"CanBeFinal", "unused"})
public class SimpleObject {
    @SuppressWarnings("FieldCanBeLocal")
    protected int val = 4;
    protected ComplexObject nullObj = null;

    private String priv = "ate";

    @Override
    public String toString() {
        return "val = " + val;
    }
}

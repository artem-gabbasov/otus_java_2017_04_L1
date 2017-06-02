package ru.otus.l81;

import com.google.gson.Gson;
import org.junit.Test;
import ru.otus.l81.classes.ComplexObject;
import ru.otus.l81.classes.SimpleObject;
import ru.otus.l81.serializers.Serializer;
import ru.otus.l81.serializers.SerializerFactoryImpl;

/**
 * Created by Artem Gabbasov on 31.05.2017.
 * <p>
 */
public class SerializerTest {
    private void performTest(Object o) throws IllegalAccessException {
        Serializer serializer = new SerializerFactoryImpl().createSerializer(o);
        String myResult = serializer.getAsString();
        String gsonResult = new Gson().toJson(o);
        System.out.println("myResult: " + myResult);
        System.out.println("gsonResult: " + gsonResult);
        assert myResult.equals(gsonResult);
    }

    @Test
    public void serializeSimpleObject() throws IllegalAccessException {
        performTest(new SimpleObject());
    }

    @Test
    public void serializeComplexObject() throws IllegalAccessException {
        performTest(new ComplexObject());
    }
}

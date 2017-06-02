package ru.otus.l81.classes;

import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Artem Gabbasov on 28.05.2017.
 * <p>
 *
 */
@SuppressWarnings({"CanBeFinal", "unused", "RedundantStringConstructorCall", "MismatchedQueryAndUpdateOfCollection"})
public class ComplexObject {
    private int v1 = 42;
    private double v2 = 0.5;
    private long v3 = 15L;
    private boolean v4 = false;
    private byte v5 = (byte) 250;
    private char v6 = 'c';
    private float v7 = 0F;
    private short v8 = -1;
    private String v9 = null;
    private transient int v10 = 5;
    @SuppressWarnings("unused")
    private enum e {A, B, C}
    private e enumVal = e.B;
    private BigInteger bigInteger = new BigInteger("1234567890987654321234567890987654321");
    private BigDecimal bigDecimal = new BigDecimal("1234567890987654321234567890987654321.123456789");
    private int[] intArray = {4, 5, 6, 7};
    private Object[] objectArray = {"stringConst", new String("stringObj"), new SimpleObject()};
    private double[] emptyArray = new double[0];
    private long[] notInitializedArray;
    private SimpleObject[] simpleObjectArray = {new SimpleObject()};
    private List<String> stringList = new ArrayList<>();
    {
        stringList.add("te\"ss\"t1");
        stringList.add("test2");
    }
    private List<Object> objectList = new LinkedList<>();
    {
        objectList.add(new SimpleObject());
        objectList.add("asd");
        objectList.add(15L);
        objectList.add(new Object());
    }
    private EnumSet<JsonValue.ValueType> enumSet = EnumSet.of(JsonValue.ValueType.OBJECT, JsonValue.ValueType.FALSE, JsonValue.ValueType.NUMBER);
    private Map<String, String> map = new TreeMap<>();
    {
        map.put("a", "abstract");
        map.put("c", "class");
        map.put("b", "byte");
    }
}

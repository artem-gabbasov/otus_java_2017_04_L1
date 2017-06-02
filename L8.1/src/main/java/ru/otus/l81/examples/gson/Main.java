package ru.otus.l81.examples.gson;

import com.google.gson.Gson;

/**
 * Created by tully.
 */
@SuppressWarnings("WeakerAccess")
public class Main {
    public static void main(String[] args) {
        BagOfPrimitives obj = new BagOfPrimitives();
        System.out.println(obj);
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        System.out.println(json);

        BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
        System.out.println(obj.equals(obj2));
        System.out.println(obj2);
    }
}

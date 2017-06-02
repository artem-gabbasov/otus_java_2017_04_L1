package ru.otus.l81.examples.io;

/**
 * Created by tully.
 */
@SuppressWarnings("WeakerAccess")
public class Student extends Person{


    private final String course;

    @SuppressWarnings("SameParameterValue")
    public Student(int age, String name, String course) {
        super(age, name);
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public String toString() {
        return super.toString() + " course: " + getCourse();
    }
}

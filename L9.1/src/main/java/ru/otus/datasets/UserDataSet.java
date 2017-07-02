package ru.otus.datasets;

import javax.persistence.*;

/**
 * Created by Artem Gabbasov on 04.06.2017.
 * <p>
 * Аннотированный для сохранения в БД класс пользователя
 */
@SuppressWarnings({"SameParameterValue", "unused"})
@Entity
@Table(name = "users")
public class UserDataSet implements DataSet {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    public UserDataSet() {}

    public UserDataSet(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

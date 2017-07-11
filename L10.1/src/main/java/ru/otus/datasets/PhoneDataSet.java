package ru.otus.datasets;

import javax.persistence.*;

/**
 * Created by Artem Gabbasov on 11.07.2017.
 * <p>
 * Аннотированный для сохранения в БД класс телефона
 */
@SuppressWarnings({"SameParameterValue", "unused"})
@Entity
@Table(name = "phones")
public class PhoneDataSet implements DataSet {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "code")
    private int code;

    @Column(name = "number", nullable = false)
    private String number;

    public PhoneDataSet() {}

    public PhoneDataSet(int id, int code, String number) {
        this.id = id;
        this.code = code;
        this.number = number;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

package ru.otus.orm.datasets.instances;

import ru.otus.orm.datasets.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Artem Gabbasov on 11.07.2017.
 * <p>
 * Аннотированный для сохранения в БД класс адреса
 */
@SuppressWarnings("unused")
@Entity
@Table(name = "addresses")
public class AddressDataSet implements DataSet {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "street")
    private String street;

    @Column(name = "zip")
    private int zip;

    public AddressDataSet() {}

    public AddressDataSet(long id, String street, int zip) {
        this.id = id;
        this.street = street;
        this.zip = zip;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}

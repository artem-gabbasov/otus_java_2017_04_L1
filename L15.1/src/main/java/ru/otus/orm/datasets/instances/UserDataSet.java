package ru.otus.orm.datasets.instances;

import ru.otus.orm.datasets.DataSet;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL)
    /*@JoinTable(name = "phones",
            joinColumns = {@JoinColumn(name = "user_id")},
             inverseJoinColumns = {@JoinColumn(name = "id")}
    )*/
    private List<PhoneDataSet> phones;

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

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }
}

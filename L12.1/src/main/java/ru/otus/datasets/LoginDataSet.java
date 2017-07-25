package ru.otus.datasets;

import ru.otus.jpa.NameColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Artem Gabbasov on 25.07.2017.
 * <p>
 */
@SuppressWarnings("unused")
@Entity
@Table(name = "login")
public class LoginDataSet implements NamedDataSet {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @NameColumn
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "passwordMD5")
    private String passwordMD5;

    public LoginDataSet() {
    }

    public LoginDataSet(long id, String username, String passwordMD5) {
        this.id = id;
        this.username = username;
        this.passwordMD5 = passwordMD5;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getPasswordMD5() {
        return passwordMD5;
    }

    public void setPasswordMD5(String passwordMD5) {
        this.passwordMD5 = passwordMD5;
    }
}

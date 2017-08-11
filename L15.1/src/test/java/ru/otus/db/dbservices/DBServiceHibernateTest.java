package ru.otus.db.dbservices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.orm.datasets.instances.AddressDataSet;
import ru.otus.orm.datasets.instances.PhoneDataSet;
import ru.otus.orm.datasets.instances.UserDataSet;
import ru.otus.db.dbservices.impl.DBServiceHibernateImpl;
import ru.otus.orm.jpa.JPAException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Artem Gabbasov on 10.07.2017.
 * <p>
 */
@SuppressWarnings("EmptyMethod")
//@Ignore
public class DBServiceHibernateTest extends DBServiceTestCommon {
    @Override
    public DBService createDBService() {
        return new DBServiceHibernateImpl();
    }

    @Before
    public void clear() throws SQLException {
        super.clear();
    }

    @Test
    public void saveAndLoad() throws SQLException, IllegalAccessException, JPAException {
        super.saveAndLoad();
    }

    @Test
    public void loadNotFound() throws SQLException, JPAException {
        super.loadNotFound();
    }

    @Test
    public void insertAndUpdate() throws SQLException, IllegalAccessException, JPAException {
        super.insertAndUpdate();
    }

    @Test
    public void linkedDataSets() throws IllegalAccessException, SQLException, JPAException {
        DBService dbService = createDBService();

        UserDataSet user1 = new UserDataSet(1, "user1", 10);
        UserDataSet user2 = new UserDataSet(2, "user2", 20);

        AddressDataSet address1 = new AddressDataSet(1, "street1", 111111);
        AddressDataSet address2 = new AddressDataSet(2, "street2", 222222);

        PhoneDataSet phone1 = new PhoneDataSet(1, 495, "1234567");
        PhoneDataSet phone2 = new PhoneDataSet(2, 347, "1234567");
        List<PhoneDataSet> phones = Arrays.asList(phone1, phone2);

        user1.setAddress(address1);
        user1.setPhones(phones);

        user2.setAddress(address2);

        dbService.save(user1);
        dbService.save(user2);

        UserDataSet loadedUser1 = dbService.load(1, UserDataSet.class);
        UserDataSet loadedUser2 = dbService.load(2, UserDataSet.class);

        assert loadedUser1.getAddress().getStreet().equals("street1") && loadedUser1.getAddress().getZip() == 111111;
        assert loadedUser2.getAddress().getStreet().equals("street2") && loadedUser2.getAddress().getZip() == 222222;

        assert loadedUser1.getPhones().size() == 2;
        PhoneDataSet loadedPhone1 = loadedUser1.getPhones().get(0);
        PhoneDataSet loadedPhone2 = loadedUser1.getPhones().get(1);
        assert loadedPhone1.getCode() == 495 && loadedPhone1.getNumber().equals("1234567");
        assert loadedPhone2.getCode() == 347 && loadedPhone2.getNumber().equals("1234567");

        assert loadedUser2.getPhones().size() == 0;
    }

    @After
    public void rollback() throws SQLException {
        super.rollback();
    }
}

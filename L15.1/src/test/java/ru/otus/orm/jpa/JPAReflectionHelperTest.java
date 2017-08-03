package ru.otus.orm.jpa;

import org.junit.Test;
import ru.otus.orm.datasets.*;
import ru.otus.orm.datasets.instances.LoginDataSet;
import ru.otus.orm.datasets.instances.UserDataSet;
import ru.otus.orm.jpa.IllegalJPAStateException;
import ru.otus.orm.jpa.JPAException;
import ru.otus.orm.jpa.JPAReflectionHelper;
import ru.otus.orm.jpa.NoJPAAnnotationException;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 */
public class JPAReflectionHelperTest {
    @Test
    public void getTableName() throws JPAException {
        //System.out.println(JPAReflectionHelper.getTableName(UserDataSet.class));
        assert JPAReflectionHelper.getTableName(UserDataSet.class).equals("users");
    }

    @Test
    public void getTableNameSchema() throws JPAException {
        //System.out.println(JPAReflectionHelper.getTableName(TestDataSet.class));
        assert JPAReflectionHelper.getTableName(TestDataSet.class).equals("test_schema.test_name");
    }

    @Test(expected = IllegalJPAStateException.class)
    public void getTableNameEmpty() throws JPAException {
        JPAReflectionHelper.getTableName(TestDataSet2.class);
    }

    @Test(expected = NoJPAAnnotationException.class)
    public void getTableNameNoAnnotation() throws JPAException {
        JPAReflectionHelper.getTableName(TestDataSet3.class);
    }

    @Test
    public void getColumnValuesMap() throws IllegalAccessException, NoJPAAnnotationException {
        UserDataSet dataSet = new UserDataSet(42, "TestUser", 99);

        Map<String, Object> map = JPAReflectionHelper.getColumnValuesMap(dataSet, UserDataSet.class);

        assert map.size() == 3;
        assert map.get("id").equals(42L);
        assert map.get("name").equals("TestUser");
        assert map.get("age").equals(99);
    }

    @Test
    public void getFieldColumnsMap() throws NoJPAAnnotationException {
        Map<Field, String> map = JPAReflectionHelper.getFieldColumnsMap(UserDataSet.class);

        assert map.size() == 3;
        assert map.containsValue("id");
        assert map.containsValue("name");
        assert map.containsValue("age");
    }

    @Test(expected = NoJPAAnnotationException.class)
    public void getFieldColumnsMapNoColumns() throws NoJPAAnnotationException {
        JPAReflectionHelper.getFieldColumnsMap(TestDataSet.class);
    }

    @Test
    public void getFieldColumnsMapEmptyColumnName() throws NoJPAAnnotationException {
        Map<Field, String> map = JPAReflectionHelper.getFieldColumnsMap(TestDataSet4.class);

        assert map.size() == 2;
        assert map.containsValue("dummy");
        assert map.containsValue("dummy3");
    }

    @Test
    public void getIdColumnName() throws JPAException {
        assert JPAReflectionHelper.getIdColumnName(UserDataSet.class).equals("id");
    }

    @Test(expected = NoJPAAnnotationException.class)
    public void getIdColumnNameNoAnnotation() throws JPAException {
        JPAReflectionHelper.getIdColumnName(TestDataSet.class);
    }

    @Test(expected = IllegalJPAStateException.class)
    public void getIdColumnNameMultiple() throws JPAException {
        JPAReflectionHelper.getIdColumnName(TestDataSet5.class);
    }

    @Test
    public void getNameColumnName() throws JPAException {
        assert JPAReflectionHelper.getNameColumnName(LoginDataSet.class).equals("username");
    }
}

package ru.otus.db.sql;

import org.junit.Test;

/**
 * Created by Artem Gabbasov on 01.07.2017.
 * <p>
 * Класс, содержащий SQL-запросы
 */
public class DAOTest {
    @Test
    public void getColumnsSequence() {
        assert DAO.getColumnsSequence(new String[]{"id", "col1", "col2"}).equals("(`id`,`col1`,`col2`)");
    }

    @Test
    public void getColumnsSequenceForUpdate() {
        assert DAO.getColumnsSequenceForUpdate(new String[]{"id", "col1", "col2"}, "id").equals("`col1`=VALUES(`col1`),`col2`=VALUES(`col2`)");
    }

    @Test
    public void getColumnsSequenceForUpdateNoIdColumnMatch() {
        assert DAO.getColumnsSequenceForUpdate(new String[]{"id", "col1", "col2"}, "idd").equals("`id`=VALUES(`id`),`col1`=VALUES(`col1`),`col2`=VALUES(`col2`)");
    }

    @Test
    public void getValuesSequence() {
        assert DAO.getValuesSequence(3).equals("(?,?,?)");
    }

    @Test
    public void getUpdateString() {
        assert DAO.getUpdateString("users", new String[]{"id", "name", "age"}, "id")
                .equals("INSERT INTO users (`id`,`name`,`age`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `name`=VALUES(`name`),`age`=VALUES(`age`);");
    }
}

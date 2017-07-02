package ru.otus.db.sql;

import org.junit.Test;
import ru.otus.db.sql.SQLUpdateHelperImpl;

/**
 * Created by Artem Gabbasov on 01.07.2017.
 * <p>
 */
public class SQLUpdateHelperImplTest {
    @Test
    public void getColumnsSequence() {
        assert new SQLUpdateHelperImpl().getColumnsSequence(new String[]{"id", "col1", "col2"}).equals("(`id`,`col1`,`col2`)");
    }

    @Test
    public void getColumnsSequenceForUpdate() {
        assert new SQLUpdateHelperImpl().getColumnsSequenceForUpdate(new String[]{"id", "col1", "col2"}, "id").equals("`col1`=VALUES(`col1`),`col2`=VALUES(`col2`)");
    }

    @Test
    public void getColumnsSequenceForUpdateNoIdColumnMatch() {
        assert new SQLUpdateHelperImpl().getColumnsSequenceForUpdate(new String[]{"id", "col1", "col2"}, "idd").equals("`id`=VALUES(`id`),`col1`=VALUES(`col1`),`col2`=VALUES(`col2`)");
    }

    @Test
    public void getValuesSequence() {
        assert new SQLUpdateHelperImpl().getValuesSequence(3).equals("(?,?,?)");
    }

    @Test
    public void getUpdateString() {
        assert new SQLUpdateHelperImpl().getUpdateString("users", new String[]{"id", "name", "age"}, "id")
                .equals("INSERT INTO users (`id`,`name`,`age`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `name`=VALUES(`name`),`age`=VALUES(`age`);");
    }
}

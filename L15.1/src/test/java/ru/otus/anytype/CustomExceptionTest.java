package ru.otus.anytype;

import org.junit.Test;
import ru.otus.anytype.auxiliaryclasses.SQLExceptionThrower;
import ru.otus.anytype.auxiliaryclasses.SQLValueExceptionThrower;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 08.07.2017.
 * <p>
 */
@SuppressWarnings("EmptyCatchBlock")
public class CustomExceptionTest {
    @Test(expected = SQLException.class)
    public void catchGeneral() throws SQLException {
        try {
            new ValueGetHelper().accept(new SQLExceptionThrower(), Object.class);
        } catch (UnsupportedTypeException e) {
        } catch (ValueException e) {
            if (e.getCause() instanceof SQLException) {
                //e.getCause().printStackTrace();
                throw (SQLException)e.getCause();
            }
        }
    }

    @Test(expected = SQLException.class)
    public void catchSpecial() throws SQLException {
        try {
            new ValueGetHelper().accept(new SQLValueExceptionThrower(), Object.class);
        } catch (UnsupportedTypeException e) {
        } catch (ValueException e) {
            //e.getCause().printStackTrace();
            throw (SQLException)e.getCause();
        }
    }
}

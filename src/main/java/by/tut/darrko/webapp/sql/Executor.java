package by.tut.darrko.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface Executor<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}

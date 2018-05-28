package by.tut.darrko.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ExecutorTransactional<T> {
    T executeTransactional(Connection connection) throws SQLException;
}
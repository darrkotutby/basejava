package by.tut.darrko.webapp.sql;

import by.tut.darrko.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeTransactional(ExecutorTransactional<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                T t = executor.executeTransactional(conn);
                conn.commit();
                return t;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    public <T> T execute(String sql, Connection conn, Executor<T> executor) throws SQLException {
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        }
    }

    public <T> T execute(String sql, Executor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

}

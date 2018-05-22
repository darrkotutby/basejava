package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.sql.ConnectionFactory;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        execute("delete from resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        execute("update resume set full_name = ? where uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        execute("insert into resume (uuid, full_name) values (?,?)", preparedStatement -> {
            try {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, r.getFullName());
                preparedStatement.execute();
            } catch (PSQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(r.getUuid());
                }
                throw e;
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return execute("select uuid, full_name from resume where uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        execute("delete from resume where uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return execute("select uuid, full_name from resume order by full_name", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Resume(resultSet.getString("uuid"),
                        resultSet.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public long size() {
        return execute("select count(1) size from resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        });
    }

    private <T> T execute(String sql, Executor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(sql)) {
            return executor.get(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @FunctionalInterface
    private interface Executor<T> {
        T get(PreparedStatement preparedStatement) throws SQLException;
    }
}

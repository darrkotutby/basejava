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
        try {
            write("delete from resume", (Writer<Resume>) PreparedStatement::execute);
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public void update(Resume r) {
        try {
            write("update resume set full_name = ? where uuid = ?", (Writer<Resume>) preparedStatement -> {
                preparedStatement.setString(1, r.getFullName());
                preparedStatement.setString(2, r.getUuid());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            });
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public void save(Resume r) {
        try {
            write("insert into resume (uuid, full_name) values (?,?)", (Writer<Resume>) preparedStatement -> {
                try {
                    preparedStatement.setString(1, r.getUuid());
                    preparedStatement.setString(2, r.getFullName());
                    preparedStatement.execute();
                } catch (PSQLException e) {
                    if (e.getSQLState().equals("23505")) {
                        throw new ExistStorageException(r.getUuid());
                    }
                    throw new StorageException("Error", e);
                }
            });
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try {
            return read("select uuid, full_name from resume where uuid = ?", preparedStatement -> {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                return new Resume(uuid, resultSet.getString("full_name"));
            });
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public void delete(String uuid) {
        try {
            write("delete from resume where uuid = ?", (Writer<String>) preparedStatement -> {
                preparedStatement.setString(1, uuid);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new NotExistStorageException(uuid);
                }
            });
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try {
            return read("select uuid, full_name from resume order by full_name", preparedStatement -> {
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Resume> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(new Resume(resultSet.getString("uuid"),
                            resultSet.getString("full_name")));
                }
                return list;
            });
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public long size() {
        try {
            return read("select count(1) size from resume", preparedStatement -> {
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getLong(1);
            });
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    private <T> T read(String sql, Reader<T> reader) throws SQLException {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(sql)) {
            return reader.get(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    private <T> void write(String sql, Writer<T> writer) throws SQLException {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(sql)) {
            writer.write(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @FunctionalInterface
    private interface Reader<T> {
        T get(PreparedStatement preparedStatement) throws SQLException;
    }

    @FunctionalInterface
    private interface Writer<T> {
        void write(PreparedStatement preparedStatement) throws SQLException;
    }
}

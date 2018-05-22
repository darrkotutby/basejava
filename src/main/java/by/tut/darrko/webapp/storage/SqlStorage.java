package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.ContactType;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.sql.ConnectionFactory;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        executeTransactional(connection -> execute("delete from resume", connection,
                (preparedStatement) -> {
                    preparedStatement.execute();
                    return null;
                }));
    }

    @Override
    public void update(Resume r) {
        executeTransactional((ExecutorTransactional<Void>) connection -> {
            execute("update resume set full_name = ? where uuid = ?", connection,
                    (Executor<Void>) (preparedStatement) -> {
                        preparedStatement.setString(1, r.getFullName());
                        preparedStatement.setString(2, r.getUuid());
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                        return null;
                    });
            insertUpdateContacts(r, connection);
            return null;
        });
    }


    @Override
    public void save(Resume r) {
        executeTransactional((ExecutorTransactional<Void>) connection -> {
            execute("insert into resume (uuid, full_name) values (?,?)", connection,
                    (Executor<Void>) (preparedStatement) -> {
                        try {
                            preparedStatement.setString(1, r.getUuid());
                            preparedStatement.setString(2, r.getFullName());
                            preparedStatement.execute();
                            return null;
                        } catch (PSQLException e) {
                            if (e.getSQLState().equals("23505")) {
                                throw new ExistStorageException(r.getUuid());
                            }
                            throw e;
                        }
                    });
            insertUpdateContacts(r, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return executeTransactional(connection -> {
            Resume resume = execute("select uuid, full_name from resume where uuid = ?", connection,
                    (preparedStatement) -> {
                        preparedStatement.setString(1, uuid);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        return new Resume(uuid, resultSet.getString("full_name"));
                    });
            resume.setContacts(loadContacts(uuid, connection));
            return resume;
        });
    }


    @Override
    public void delete(String uuid) {
        executeTransactional((ExecutorTransactional<Void>) connection -> {
            execute("delete from resume where uuid = ?", connection,
                    (Executor<Void>) (preparedStatement) -> {
                        preparedStatement.setString(1, uuid);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected == 0) {
                            throw new NotExistStorageException(uuid);
                        }
                        return null;
                    });
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return executeTransactional(connection ->
                execute("select uuid, full_name from resume order by full_name, uuid", connection,
                        (preparedStatement) -> {
                            ResultSet resultSet = preparedStatement.executeQuery();
                            List<Resume> list = new ArrayList<>();
                            while (resultSet.next()) {
                                Resume resume = new Resume(resultSet.getString("uuid"),
                                        resultSet.getString("full_name"));
                                resume.setContacts(loadContacts(resume.getUuid(), connection));
                                list.add(resume);
                            }
                            return list;
                        }));
    }

    @Override
    public long size() {
        return executeTransactional(connection -> execute("select count(1) size from resume", connection,
                (preparedStatement) -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    return resultSet.getLong(1);
                }));
    }

    private <T> T executeTransactional(ExecutorTransactional<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                T t = executor.execute(conn);
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

    private <T> T execute(String sql, Connection conn, Executor<T> executor) throws SQLException {
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        }
    }

    private void insertUpdateContacts(Resume r, Connection connection) throws SQLException {
        execute("insert into contact (uuid, contact_type, value) values (?,?,?)\n" +
                        "ON CONFLICT ON CONSTRAINT contact_uuid_contact_type_uk DO UPDATE\n" +
                        "   SET value = ?", connection,
                (Executor<Void>) (preparedStatement) -> {
                    for (Map.Entry<ContactType, String> entry :
                            r.getContacts().entrySet()) {
                        preparedStatement.setString(1, r.getUuid());
                        preparedStatement.setString(2, entry.getKey().toString());
                        preparedStatement.setString(3, entry.getValue());
                        preparedStatement.setString(4, entry.getValue());
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    return null;
                });
    }

    private Map<ContactType, String> loadContacts(String uuid, Connection connection) throws SQLException {
        return execute("select contact_type, value from contact where uuid = ?", connection,
                (preparedStatement) -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet1 = preparedStatement.executeQuery();
                    Map<ContactType, String> map = new EnumMap<>(ContactType.class);
                    while (resultSet1.next()) {
                        map.putIfAbsent(ContactType.valueOf(resultSet1.getString("contact_type")),
                                resultSet1.getString("value"));
                    }
                    return map;
                });
    }

    @FunctionalInterface
    private interface Executor<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }

    @FunctionalInterface
    private interface ExecutorTransactional<T> {
        T execute(Connection connection) throws SQLException;
    }
}

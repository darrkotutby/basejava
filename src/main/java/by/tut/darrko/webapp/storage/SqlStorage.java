package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.*;
import by.tut.darrko.webapp.sql.ConnectionFactory;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        execute("delete from resume", preparedStatement -> {
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        executeTransactional(connection -> {
            execute("update resume set full_name = ? where uuid = ?", connection,
                    preparedStatement -> {
                        preparedStatement.setString(1, r.getFullName());
                        preparedStatement.setString(2, r.getUuid());
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                        return null;
                    });
            deleteContacts(r, connection);
            insertContacts(r, connection);
            deleteSections(r, connection);
            insertSections(r, connection);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        executeTransactional(connection -> {
            execute("insert into resume (uuid, full_name) values (?,?)", connection,
                    preparedStatement -> {
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
            insertContacts(r, connection);
            insertSections(r, connection);
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

            Map<ContactType, String> contactMap = loadContacts(uuid, connection);
            if (contactMap != null) {
                resume.setContacts(contactMap);
            }

            Map<SectionType, Section> sectionMap = loadSections(uuid, connection);
            if (sectionMap != null) {
                resume.setSections(sectionMap);
            }
            return resume;
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
        return executeTransactional(connection -> {
            Map<String, Map<ContactType, String>> contacts =
                    execute("select uuid, contact_type, value from contact order by uuid", connection,
                            preparedStatement -> {
                                ResultSet resultSet = preparedStatement.executeQuery();
                                return getContactsMap(resultSet);
                            });
            Map<String, Map<SectionType, Section>> sections =
                    execute("select uuid, section_type, value from section order by uuid", connection,
                            preparedStatement -> {
                                ResultSet resultSet = preparedStatement.executeQuery();
                                return getSectionsMap(resultSet);
                            });
            return execute("select uuid, full_name from resume order by full_name, uuid", connection,
                    preparedStatement -> {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        List<Resume> list = new ArrayList<>();
                        while (resultSet.next()) {
                            Resume resume = new Resume(resultSet.getString("uuid"),
                                    resultSet.getString("full_name"));
                            Map<ContactType, String> contactsMap = contacts.get(resume.getUuid());
                            if (contactsMap != null) {
                                resume.setContacts(contactsMap);
                            }
                            Map<SectionType, Section> sectionsMap = sections.get(resume.getUuid());
                            if (sectionsMap != null) {
                                resume.setSections(sectionsMap);
                            }
                            list.add(resume);
                        }
                        return list;
                    });
        });
    }

    private Map<String, Map<SectionType, Section>> getSectionsMap(ResultSet resultSet) throws SQLException {
        Map<String, Map<SectionType, Section>> map = new HashMap<>();
        while (resultSet.next()) {
            String uuid = resultSet.getString("uuid");
            Map<SectionType, Section> sectionsMap =
                    map.getOrDefault(uuid,
                            new EnumMap<>(SectionType.class));
            SectionType sectionType = SectionType.valueOf(resultSet.getString("section_type"));
            sectionsMap.putIfAbsent(sectionType, createSection(sectionType,
                    resultSet.getString("value")));
            map.put(uuid, sectionsMap);
        }
        return map;
    }

    private Map<String, Map<ContactType, String>> getContactsMap(ResultSet resultSet) throws SQLException {
        Map<String, Map<ContactType, String>> map = new HashMap<>();
        while (resultSet.next()) {
            String uuid = resultSet.getString("uuid");
            Map<ContactType, String> contactsMap =
                    map.getOrDefault(uuid,
                            new EnumMap<>(ContactType.class));
            contactsMap.put(ContactType.valueOf(resultSet.getString("contact_type")),
                    resultSet.getString("value"));
            map.put(uuid, contactsMap);
        }
        return map;
    }

    @Override
    public long size() {
        return execute("select count(1) size from resume", (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        });
    }

    private <T> T executeTransactional(ExecutorTransactional<T> executor) {
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

    private <T> T execute(String sql, Connection conn, Executor<T> executor) throws SQLException {
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        }
    }

    private <T> T execute(String sql, Executor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    private void insertContacts(Resume r, Connection connection) throws SQLException {
        execute("insert into contact (uuid, contact_type, value) values (?,?,?)", connection,
                preparedStatement -> {
                    for (Map.Entry<ContactType, String> entry :
                            r.getContacts().entrySet()) {
                        preparedStatement.setString(1, r.getUuid());
                        preparedStatement.setString(2, entry.getKey().toString());
                        preparedStatement.setString(3, entry.getValue());
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    return null;
                });
    }

    private void deleteContacts(Resume r, Connection connection) throws SQLException {
        execute("delete from contact where uuid = ?", connection,
                preparedStatement -> {
                    preparedStatement.setString(1, r.getUuid());
                    preparedStatement.execute();
                    return null;
                });
    }

    private void insertSections(Resume r, Connection connection) throws SQLException {
        execute("insert into section (uuid, section_type, value) values (?,?,?)", connection,
                preparedStatement -> {
                    for (Map.Entry<SectionType, Section> entry :
                            r.getSections().entrySet()) {
                        preparedStatement.setString(1, r.getUuid());
                        preparedStatement.setString(2, entry.getKey().toString());
                        switch (entry.getKey()) {
                            case PERSONAL:
                            case OBJECTIVE: {
                                preparedStatement.setString(3, ((TextSection) entry.getValue()).getContent());
                                break;
                            }
                            case ACHIEVEMENT:
                            case QUALIFICATIONS: {
                                preparedStatement.setString(3, String.join("\n",
                                        ((ListSection) entry.getValue()).getItems()));
                                break;
                            }
                            case EXPERIENCE:
                            case EDUCATION: {
                                break;
                            }
                            default:
                                throw new IllegalArgumentException("Unknown section type:" + entry.getKey());
                        }
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    return null;
                });
    }

    private Section createSection(SectionType sectionType, String value) {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE: {
                return new TextSection(value);
            }
            case ACHIEVEMENT:
            case QUALIFICATIONS: {
                return new ListSection(Arrays.asList(value.split("\\s*\n\\s*")));
            }
            case EXPERIENCE:
            case EDUCATION: {
                return null;
            }
            default:
                throw new IllegalArgumentException("Unknown section type:" + sectionType);
        }
    }


    private void deleteSections(Resume r, Connection connection) throws SQLException {
        execute("delete from section where uuid = ?", connection,
                preparedStatement -> {
                    preparedStatement.setString(1, r.getUuid());
                    preparedStatement.execute();
                    return null;
                });
    }

    private Map<SectionType, Section> loadSections(String uuid, Connection connection) throws SQLException {
        return execute("select uuid, section_type, value from section where uuid = ?", connection,
                (preparedStatement) -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return getSectionsMap(resultSet).get(uuid);
                });
    }


    private Map<ContactType, String> loadContacts(String uuid, Connection connection) throws SQLException {
        return execute("select uuid, contact_type, value from contact where uuid = ?", connection,
                (preparedStatement) -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return getContactsMap(resultSet).get(uuid);
                });
    }

    @FunctionalInterface
    private interface Executor<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }

    @FunctionalInterface
    private interface ExecutorTransactional<T> {
        T executeTransactional(Connection connection) throws SQLException;
    }
}

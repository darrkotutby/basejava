package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.ContactType;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.model.Section;
import by.tut.darrko.webapp.model.SectionType;
import by.tut.darrko.webapp.sql.SqlHelper;
import by.tut.darrko.webapp.util.JsonParser;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException();
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("delete from resume", preparedStatement -> {
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.executeTransactional(connection -> {
            sqlHelper.execute("update resume set full_name = ? where uuid = ?", connection,
                    preparedStatement -> {
                        preparedStatement.setString(1, r.getFullName());
                        preparedStatement.setString(2, r.getUuid());
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                        return null;
                    });
            deleteAttributes(r, "delete from contact where uuid = ?", connection);
            deleteAttributes(r, "delete from section where uuid = ?", connection);
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.executeTransactional(connection -> {
            sqlHelper.execute("insert into resume (uuid, full_name) values (?,?)", connection,
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
        return sqlHelper.executeTransactional(connection -> {
            Resume resume = sqlHelper.execute("select uuid, full_name from resume where uuid = ?", connection,
                    (preparedStatement) -> {
                        preparedStatement.setString(1, uuid);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        return new Resume(uuid, resultSet.getString("full_name"));
                    });
            Map<ContactType, String> contactMap =
                    sqlHelper.execute("select uuid, contact_type, value from contact where uuid = ?", connection,
                            (preparedStatement) -> {
                                preparedStatement.setString(1, uuid);
                                ResultSet resultSet = preparedStatement.executeQuery();
                                return getContactsMap(resultSet).get(uuid);
                            });
            if (contactMap != null) {
                resume.setContacts(contactMap);
            }

            Map<SectionType, Section> sectionMap =
                    sqlHelper.execute("select uuid, section_type, value from section where uuid = ?", connection,
                            (preparedStatement) -> {
                                preparedStatement.setString(1, uuid);
                                ResultSet resultSet = preparedStatement.executeQuery();
                                return getSectionsMap(resultSet).get(uuid);
                            });
            if (sectionMap != null) {
                resume.setSections(sectionMap);
            }
            return resume;
        });
    }


    @Override
    public void delete(String uuid) {
        sqlHelper.execute("delete from resume where uuid = ?", preparedStatement -> {
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
        return sqlHelper.executeTransactional(connection -> {
            Map<String, Map<ContactType, String>> contacts =
                    sqlHelper.execute("select uuid, contact_type, value from contact order by uuid", connection,
                            preparedStatement -> {
                                ResultSet resultSet = preparedStatement.executeQuery();
                                return getContactsMap(resultSet);
                            });
            Map<String, Map<SectionType, Section>> sections =
                    sqlHelper.execute("select uuid, section_type, value from section order by uuid", connection,
                            preparedStatement -> {
                                ResultSet resultSet = preparedStatement.executeQuery();
                                return getSectionsMap(resultSet);
                            });
            return sqlHelper.execute("select uuid, full_name from resume order by full_name, uuid", connection,
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

    @Override
    public long size() {
        return sqlHelper.execute("select count(1) size from resume", (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        });
    }

    private void insertContacts(Resume r, Connection connection) throws SQLException {
        insertAttributes(r.getUuid(), r.getContacts(),
                "insert into contact (uuid, contact_type, value) values (?,?,?)", connection, String.class);
    }

    private void insertSections(Resume r, Connection connection) throws SQLException {
        insertAttributes(r.getUuid(), r.getSections(),
                "insert into section (uuid, section_type, value) values (?,?,?)", connection, Section.class);
    }

    private <T, V> void insertAttributes(String uuid, Map<T, V> map,
                                         String sql, Connection connection, Class<V> clazz) throws SQLException {
        sqlHelper.execute(sql, connection,
                preparedStatement -> {
                    for (Map.Entry<T, V> entry :
                            map.entrySet()) {
                        preparedStatement.setString(1, uuid);
                        preparedStatement.setString(2, entry.getKey().toString());
                        preparedStatement.setString(3, JsonParser.write(entry.getValue(), clazz));
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    return null;
                });
    }

    private void deleteAttributes(Resume r, String sql, Connection connection) throws SQLException {
        sqlHelper.execute(sql, connection,
                preparedStatement -> {
                    preparedStatement.setString(1, r.getUuid());
                    preparedStatement.execute();
                    return null;
                });
    }

    private Map<String, Map<ContactType, String>> getContactsMap(ResultSet resultSet) throws SQLException {
        Map<String, Map<ContactType, String>> map = new LinkedHashMap<>();
        while (resultSet.next()) {
            String uuid = resultSet.getString("uuid");
            Map<ContactType, String> contactsMap =
                    map.getOrDefault(uuid,
                            new EnumMap<>(ContactType.class));
            contactsMap.put(ContactType.valueOf(resultSet.getString("contact_type")),
                    JsonParser.read(resultSet.getString("value"), String.class));
            map.put(uuid, contactsMap);
        }
        return map;
    }

    private Map<String, Map<SectionType, Section>> getSectionsMap(ResultSet resultSet) throws SQLException {
        Map<String, Map<SectionType, Section>> map = new LinkedHashMap<>();
        while (resultSet.next()) {
            String uuid = resultSet.getString("uuid");
            Map<SectionType, Section> sectionsMap =
                    map.getOrDefault(uuid,
                            new EnumMap<>(SectionType.class));
            sectionsMap.putIfAbsent(SectionType.valueOf(resultSet.getString("section_type")),
                    JsonParser.read(resultSet.getString("value"), Section.class));
            map.put(uuid, sectionsMap);
        }
        return map;
    }

    private <T, V, B> Map<T, Map<V, B>> getSectionsMap1(ResultSet resultSet) throws SQLException {
        Map<String, Map<SectionType, Section>> map = new LinkedHashMap<>();

        return null;
    }


}

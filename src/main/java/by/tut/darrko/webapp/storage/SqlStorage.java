package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.sql.ConnectionFactory;

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
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("delete from resume")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public void update(Resume r) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement("update resume set full_name = ? where uuid = ?")) {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public void save(Resume r) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement("insert into resume (uuid, full_name) values (?,?)")) {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ExistStorageException(r.getUuid());
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(r.getUuid());
            }
            throw new StorageException("Error", e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement("select uuid, full_name from resume where uuid = ?")) {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement("delete from resume where uuid = ?")) {
            preparedStatement.setString(1, uuid);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement("select uuid, full_name from resume order by full_name")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return list;
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }

    @Override
    public long size() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement("select count(1) size from resume")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new StorageException("Error. ResultSet is empty");
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new StorageException("Error", e);
        }
    }
}

package ru.itmo.wp.model.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;

public abstract class AbstractRepositoryImpl {
    
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected ResultSet findBy(List<String> fields, List<String> values, String table) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            StringBuilder query = new StringBuilder("SELECT * FROM " + table + " WHERE ");
            for (int i = 0; i < fields.size(); i++) {
                query.append(fields.get(i)).append("=?");
                if (i != fields.size() - 1) {
                    query.append(" AND ");
                }
            }
            // throw new RuntimeException(query.toString());
            try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
                for (int i = 0; i < values.size(); i++) {
                    statement.setString(i + 1, values.get(i));
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    protected ResultSet findAll(String table) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    protected int findCount(String table) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM " + table)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next())
                        return resultSet.getInt(1);
                    throw new RepositoryException("Can't find count");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find count", e);
        }
    }

    protected ResultSet save(List<String> fields, List<String> values, String table) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            StringBuilder query = new StringBuilder("INSERT INTO `" + table + "` (");
            for (int i = 0; i < fields.size(); i++) {
                query.append('`').append(fields.get(i)).append('`');
                if (i != fields.size() - 1) {
                    query.append(", ");
                }
            }
            query.append(") VALUES (");
            for (int i = 0; i < fields.size(); i++) {
                query.append("?");
                if (i != fields.size() - 1) {
                    query.append(", ");
                }
            }
            query.append(")");
            try (PreparedStatement statement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < values.size(); i++) {
                    statement.setString(i + 1, values.get(i));
                }
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save User.");
                } else {
                    return statement.getGeneratedKeys();
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

}

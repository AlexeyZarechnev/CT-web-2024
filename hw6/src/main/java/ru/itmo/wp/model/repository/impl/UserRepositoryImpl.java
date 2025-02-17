package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
public class UserRepositoryImpl extends AbstractRepositoryImpl implements UserRepository {

    private final static String TABLE_NAME = "User"; 

    @Override
    public User find(long id) {
        ResultSet resultSet = findBy(List.of("id"), List.of(Long.toString(id)), TABLE_NAME);
        try { 
            return toUser(resultSet.getMetaData(), resultSet);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public User findByLogin(String login) {
        ResultSet resultSet = findBy(List.of("login"), List.of(login), TABLE_NAME);
        try { 
            return toUser(resultSet.getMetaData(), resultSet);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha, boolean byEmail) {
        String parameter = byEmail ? "email" : "login";
        ResultSet resultSet = findBy(List.of(parameter, "passwordSha"), List.of(loginOrEmail, passwordSha), TABLE_NAME);
        try { 
            return toUser(resultSet.getMetaData(), resultSet);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = findAll(TABLE_NAME);
        try { 
            User user;
            while ((user = toUser(resultSet.getMetaData(), resultSet)) != null) {
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return users;
    }

    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    public void save(User user, String passwordSha) {
        ResultSet generatedKeys = save(List.of("login", "email", "passwordSha"), 
        List.of(user.getLogin(), user.getEmail(), passwordSha), TABLE_NAME);
        try {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
                user.setCreationTime(find(user.getId()).getCreationTime());
            } else {
                throw new RepositoryException("Can't save User [no autogenerated fields].");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        ResultSet resultSet = findBy(List.of("email"), List.of(email), TABLE_NAME);
        try { 
            return toUser(resultSet.getMetaData(), resultSet);
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public int getUserCount() {
        return findCount(TABLE_NAME);
    }
}

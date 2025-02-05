package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface UserRepository {
    User find(long id);

    User findByLogin(String login);

    User findByEmail(String email);

    User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha, boolean byEmail);

    List<User> findAll();

    int getUserCount();

    void setAdmin(long id, boolean admin);

    void save(User user, String passwordSha);
}

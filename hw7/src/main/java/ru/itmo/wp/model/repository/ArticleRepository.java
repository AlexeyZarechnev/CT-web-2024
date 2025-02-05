package ru.itmo.wp.model.repository;

import java.util.List;

import ru.itmo.wp.model.domain.Article;

public interface ArticleRepository {
    void save(Article article);

    void setHidden(long articleId, boolean hidden);

    List<Article> findAll();

    List<Article> findAllByUserId(long userId);

    Article find(long id);

    Article findByTitle(String title);

    int findCount();

    int findCountByUserId(long userId);
}

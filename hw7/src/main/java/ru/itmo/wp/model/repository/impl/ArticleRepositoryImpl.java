package ru.itmo.wp.model.repository.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;

public class ArticleRepositoryImpl extends AbstractRepositoryImpl implements ArticleRepository {

    private final static String TABLE_NAME = "Article";

    @Override
    public void save(Article article) {
        ResultSet generatedKeys = save(List.of("userId", "title", "text"), List.of(Long.toString(article.getUserId()), 
        article.getTitle(), article.getText()), TABLE_NAME);
        try {
            if (generatedKeys.next()) {
                article.setId(generatedKeys.getLong(1));
                article.setCreationTime(find(article.getId()).getCreationTime());
            } else {
                throw new RepositoryException("Can't save User [no autogenerated fields].");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    @Override
    public void setHidden(long articleId, boolean hidden) {
        set(List.of("hidden"), List.of(hidden ? "1" : "0"), List.of("id"), List.of(Long.toString(articleId)), TABLE_NAME);
    }

    @Override
    public List<Article> findAll() {
        ResultSet resultSet = findBy(List.of("hidden"), List.of("false"), TABLE_NAME);
        try {
            Article article;
            List<Article> articles = new ArrayList<>();
            while ((article = toArticle(resultSet.getMetaData(), resultSet)) != null) {
                articles.add(article);
            }
            resultSet.close();
            return articles;
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Articles.", e);
        }
    }

    @Override
    public List<Article> findAllByUserId(long userId) {
        ResultSet resultSet = findBy(List.of("userId"), List.of(Long.toString(userId)), TABLE_NAME);
        try {
            Article article;
            List<Article> articles = new ArrayList<>();
            while ((article = toArticle(resultSet.getMetaData(), resultSet)) != null) {
                articles.add(article);
            }
            resultSet.close();
            return articles;
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Articles.", e);
        }
    }

    @Override
    public Article find(long id) {
        ResultSet resultSet = findBy(List.of("id"), List.of(Long.toString(id)), TABLE_NAME);
        try { 
            Article article = toArticle(resultSet.getMetaData(), resultSet);
            resultSet.close();
            return article;
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public int findCount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCount'");
    }

    @Override
    public int findCountByUserId(long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCountByUserId'");
    }

    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations.
            }
        }

        return article;
    }

    @Override
    public Article findByTitle(String title) {
        ResultSet resultSet = findBy(List.of("title"), List.of(title), TABLE_NAME);
        try { 
            Article article = toArticle(resultSet.getMetaData(), resultSet);
            resultSet.close();
            return article;
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }
    
}

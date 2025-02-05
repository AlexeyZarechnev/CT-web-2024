package ru.itmo.wp.model.service;

import java.util.List;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

public class ArticleService {

    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public void validateArticle(Article article) throws ValidationException {
        if (article.getTitle() == null || article.getTitle().isEmpty() || article.getTitle().isBlank()) {
            throw new ValidationException("Title is required");
        }
        if (article.getTitle().length() > 255) {
            throw new ValidationException("Title must be shorter than 255 characters");
        }
        if (article.getTitle().matches("[\\n\\r]")) {
            throw new ValidationException("Title must not contain new lines");
        }
        if (articleRepository.findByTitle(article.getTitle()) != null) {
            throw new ValidationException("Title is already used");
        }
        if (article.getText() == null || article.getText().isEmpty()|| article.getText().isBlank()) {
            throw new ValidationException("Text is required");
        }
        if (article.getText().length() > 65535) {
            throw new ValidationException("Text must be shorter than 65535 characters");
        }
    }

    public Article find(long articleId) {
        return articleRepository.find(articleId);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllByUserId(long userId) {
        return articleRepository.findAllByUserId(userId);
    }

    public void setHidden(long articleId, boolean hidden) {
        articleRepository.setHidden(articleId, hidden);
    }
}

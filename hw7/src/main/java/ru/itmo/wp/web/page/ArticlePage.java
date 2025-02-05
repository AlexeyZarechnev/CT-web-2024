package ru.itmo.wp.web.page;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.UnauthorizedException;

@SuppressWarnings({"unused"})
public class ArticlePage extends AbstractPage {

    private ArticleService articleService = new ArticleService();

    private void create(HttpServletRequest request, Map<String, Object> view) throws UnauthorizedException, ValidationException {
        Article article = new Article();

        checkAuthorization();

        article.setUserId(getUser().getId());
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));

        articleService.validateArticle(article);

        articleService.createArticle(article);
        setMessage("Article created successfully");
    }
}

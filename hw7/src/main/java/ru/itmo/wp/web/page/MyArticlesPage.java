package ru.itmo.wp.web.page;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.UnauthorizedException;

@SuppressWarnings({"unused"})
public class MyArticlesPage extends AbstractPage {

    private final ArticleService articleService = new ArticleService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) throws UnauthorizedException {
        super.before(request, view);
        checkAuthorization();
    }

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAllByUserId(getUser().getId()));
    }

    private void hideArticle(HttpServletRequest request, Map<String, Object> view) {
        long articleId = Long.parseLong(request.getParameter("articleId"));

        Article article = articleService.find(articleId);
        if (article == null || article.getUserId() != getUser().getId()) {
            setMessage("You haven't access this article");
            return;
        }

        articleService.setHidden(articleId, true);
        setMessage("Article hidden");
    }

    private void showArticle(HttpServletRequest request, Map<String, Object> view) {
        long articleId = Long.parseLong(request.getParameter("articleId"));
        articleService.setHidden(articleId, false);
        setMessage("Article shown");
    }
}

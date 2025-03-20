package app.api.service;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.exception.NoContentInRepositoryException;
import app.api.repository.ArticlesRepository;
import app.api.repository.DummyArticlesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArticlesService {
  private final ArticlesRepository articleRepository;
  private final CategoriesService categoriesService;

  public ArticlesService() {
    this.articleRepository = new DummyArticlesRepository();
    this.categoriesService = new CategoriesService();
  }

  public List<Article> getArticles() {
    try {
      List<Article> articles = articleRepository.getArticles();
      log.info("getArticles");
      if (articles.isEmpty()) {
        log.error("No articles exists");
        throw new NoContentInRepositoryException("getArticles was failed");
      }
      return articles;
    } catch (Exception e) {
      log.error("getArticles was failed", e);
      throw e;
    }
  }

  public Map<Article, Category> getArticlesWithCategory(UserId userId) {
    List<Article> articles = getArticles();
    log.info("Get articles with category");
    Map<Article, Category> articleCategoryMap = new HashMap<>();
    for(Article article : articles) {
      articleCategoryMap.put(article, categoriesService.findById(article.categoryId()));
    }
    log.info("Get articles with category finished");
    return articleCategoryMap;
  }
}

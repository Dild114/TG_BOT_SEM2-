package app.api.service;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.exception.NoContentInRepositoryException;
import app.api.repository.ArticlesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ArticlesService {
  public ArticlesRepository articleRepository;


  public ArticlesService(ArticlesRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public Map<Article, Category> getArticles(UserId userId) {
    try {
      Map<Article, Category> articles = articleRepository.getArticles(userId);
      log.info("getArticles userId = {}", userId);
      if (articles.isEmpty()) {
        log.error("No articles for user with userId = {}", userId);
        throw new NoContentInRepositoryException("getArticles userId = " + userId + " was failed");
      }
      return articles;
    } catch (Exception e) {
      log.error("getArticles userId = {} was failed", userId, e);
      throw e;
    }
  }
}

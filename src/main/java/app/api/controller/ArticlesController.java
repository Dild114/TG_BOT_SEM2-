package app.api.controller;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.service.ArticlesService;
import app.api.controller.interfaceDrivenControllers.ArticleControllerInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class ArticlesController implements ArticleControllerInterface {

  private final ArticlesService articlesService;

  public ArticlesController(ArticlesService articlesService) {
    this.articlesService = articlesService;
  }

  @Override
  public ResponseEntity<Map<Article, Category>> getArticles(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Invalid userId = " + id);
    }
    log.info("Fetching articles for userId={}", id);
    Map<Article, Category> articles = articlesService.getArticles(new UserId(id));
    return ResponseEntity.ok(articles);
  }
}

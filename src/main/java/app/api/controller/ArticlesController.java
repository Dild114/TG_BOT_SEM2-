package app.api.controller;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.service.ArticlesService;
import app.api.controller.interfaceDrivenControllers.ArticleControllerInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<Map<Article, Category>> getArticles(Long userId) {
    log.info("Fetching articles for userId={}", userId);
    try {
      Map<Article, Category> articles = articlesService.getArticles(new UserId(userId));
      return ResponseEntity.ok(articles);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}

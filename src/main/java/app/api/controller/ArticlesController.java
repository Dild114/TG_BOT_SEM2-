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

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ArticlesController implements ArticleControllerInterface {

  private final ArticlesService articlesService;

  public ArticlesController(ArticlesService articlesService) {
    this.articlesService = articlesService;
  }

  @Override
  public ResponseEntity<List<Article>> getArticles() {
    log.info("Fetching articles");
    try {
      List<Article> articles = articlesService.getArticles();
      return ResponseEntity.ok(articles);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @Override
  public ResponseEntity<Map<Article, Category>> getArticleWithCategory(Long userId) {
    try {
      Map<Article, Category> articleCategoryMap = articlesService.getArticlesWithCategory(new UserId(userId));
      return ResponseEntity.ok(articleCategoryMap);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}

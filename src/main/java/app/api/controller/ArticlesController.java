package app.api.controller;

import app.api.entity.Article;
//import app.api.entity.Category;
import app.api.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticlesController{

  private final ArticleService articleService;

  public ArticlesController(ArticleService articleService) {

    this.articleService = articleService;
  }

//  @Override
//  public ResponseEntity<Map<Article, Category>> getArticles(int id) {
//    log.info("Fetching articles for userId={}", id);
//    try {
//      Map<Article, Category> articles = articlesService.getArticles(new UserId(id));
//      return ResponseEntity.ok(articles);
//    } catch (Exception e) {
//      log.error("get articles failed", e);
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//  }

  @PostMapping("/create_article")
  public ResponseEntity<Article> createArticle(@RequestBody Article article) {
    Article savedArticle = articleService.saveArticle(article);
    return ResponseEntity.ok(savedArticle);
  }
}

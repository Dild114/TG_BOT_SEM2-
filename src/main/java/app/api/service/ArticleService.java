package app.api.service;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {

  public ArticleRepository articleRepository;

  @Transactional
  public Article addArticle(Article article) {
    return articleRepository.save(article);
  }

  @Transactional
  public Article findArticleById(ArticleId id) {
    return articleRepository.findById(id).orElse(null);
  }

  public List<Article> getArticlesByCategory(Category category) {
    return articleRepository.findByCategory(category);
  }

  @Transactional
  public void deleteArticleById(ArticleId id) {
    Article article = findArticleById(id);
    articleRepository.delete(article);
  }

  @Transactional
  public void deleteArticlesByCategory(Category category) {
    List<Article> articles = articleRepository.findByCategory(category);
    if (!articles.isEmpty()) {
      articleRepository.deleteAll(articles);
    }
  }

  public List<Article> getAllArticles() {
    return articleRepository.findAll();
  }

}

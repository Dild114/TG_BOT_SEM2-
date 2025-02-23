package app.api.service;

import app.api.entity.Article;
import app.api.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {

  public ArticleRepository articleRepository;

//  public Map<Article, Category> getArticles(UserId userId) {
//    Map<Article, Category> articles = articleRepository.getArticles(userId);
//    log.info("getArticles userId={}", userId);
//    return articles;
//  }

  @Transactional
  public Article saveArticle(Article article) {
    return articleRepository.save(article);
  }
}

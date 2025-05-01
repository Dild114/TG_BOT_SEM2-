package app.api.service;

import app.api.entity.Article;
import app.api.entity.User;
import app.api.mapper.ArticleMapper;
import app.api.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;

  @Transactional(readOnly = true)
  public Article getUserArticle(Long chatId, Long articleId) {
    return articleRepository.findById(articleId)
        .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));
  }

  @Transactional
  public List<Article> getNewUserArticles(Long chatId, long countResponseArticlesForUser) {
    List<Article> articles = articleRepository.findArticlesByUser_ChatId(chatId);
    articles.sort(Comparator.comparing(Article::getId).reversed());

    List<Article> responseNewArticles = new ArrayList<>();
    int countArticlesInResponseList = 0;

    for (Article article : articles) {
      if (!article.getFavoriteStatus()) {
        responseNewArticles.add(article);
        article.changeWatchedStatus();
        ++countArticlesInResponseList;
      }
      if (countArticlesInResponseList == countResponseArticlesForUser) {
        break;
      }
    }
    return responseNewArticles;
  }

  @Transactional(readOnly = true)
  public List<Article> getLikedUserArticles(Long chatId) {
    List<Article> articles = articleRepository.findArticlesByUser_ChatId(chatId);
    articles.sort(Comparator.comparing(Article::getId));

    List<Article> responseLikedArticles = new ArrayList<>();
    for (Article article : articles) {
      if (article.getFavoriteStatus()) {
        responseLikedArticles.add(article);
        article.changeWatchedStatus();
      }
    }

    return responseLikedArticles;
  }

  @Transactional
  public void addArticle(User user, String articleName, String articleCategoryName, String articleParsedTime, String articleParsedDate, String articleUrl, String briefContent) {
    Article article = Article.builder()
        .name(articleName)
        .url(articleUrl)
        .creationDate(OffsetDateTime.now())
        .briefContent(briefContent)
        .statusOfWatchingBriefContent(false)
        .favoriteStatus(false)
        .watchedStatus(false)
        .user(user)
        .build();

    articleRepository.save(article);
  }

  @Transactional
  public void deleteUnneededUserArticles(Long chatId) {
    articleRepository.deleteUnneededUserArticles(chatId);
  }

  public void changeUserArticleStatusBrief(long chatId, long articleId) {
    Article article = articleRepository.findArticlesByUser_ChatId(chatId).stream()
        .filter(a -> a.getId().equals(articleId))
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));
    article.changeStatusOfWatchingBriefContent();
    articleRepository.save(article);
  }

  public void changeUserArticleFavoriteStatus(long chatId, long articleId) {
    Article article = articleRepository.findArticlesByUser_ChatId(chatId).stream()
        .filter(a -> a.getId().equals(articleId))
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));
    article.changeFixedStatus();
    articleRepository.save(article);
  }
}
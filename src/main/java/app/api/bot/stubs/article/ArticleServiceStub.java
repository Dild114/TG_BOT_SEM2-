package app.api.bot.stubs.article;

import app.api.bot.stubs.user.UserServiceStub;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceStub {
  private final UserServiceStub userServiceStub;
  private final Map<Long, Map<Long, ArticleStub>> articlesForUsers;

  private static long nextId = 0;

  public ArticleServiceStub(UserServiceStub userServiceStub) {
    this.articlesForUsers = new HashMap<>();
    this.userServiceStub = userServiceStub;
  }

  public ArticleStub getUserArticle(long chatId, long articleId) {
    return articlesForUsers.get(chatId).get(articleId);
  }

  public List<ArticleStub> getNewUserArticles(long chatId) {
    //TODO: необходимо реализовать в репозитории, чтобы возвращались только статьи, у которых favoriteStatus = false (т.е. пользователь их ещё у себя не закрепил)
    Map<Long, ArticleStub> userArticles = articlesForUsers.getOrDefault(chatId, new HashMap<>());
    List<ArticleStub> sortedUserArticles = new ArrayList<>(userArticles.values());

    sortedUserArticles.sort(Comparator.comparingLong(ArticleStub::getArticleId).reversed());

    int countResponseArticlesForUser = userServiceStub.getUserCountArticlesInOneRequest(chatId);

    List<ArticleStub> responseNewArticles = new ArrayList<>();


    int countArticlesInResponseList = 0;
    for (ArticleStub articleStub : sortedUserArticles) {
      if (!articleStub.getFavoriteStatus()) {
        responseNewArticles.add(articleStub);
        articleStub.changeWatchedStatus();
        ++countArticlesInResponseList;
      }
      if (countArticlesInResponseList == countResponseArticlesForUser) {
        break;
      }
    }

    return responseNewArticles;
  }

  public List<ArticleStub> getLikedUserArticles(long chatId) {
    Map<Long, ArticleStub> userArticles = articlesForUsers.getOrDefault(chatId, new HashMap<>());
    List<ArticleStub> sortedUserArticles = new ArrayList<>(userArticles.values());

    sortedUserArticles.sort(Comparator.comparingLong(ArticleStub::getArticleId));

    List<ArticleStub> responseLikedArticles = new ArrayList<>();

    for (ArticleStub articleStub : sortedUserArticles) {
      if (articleStub.getFavoriteStatus()) {
        responseLikedArticles.add(articleStub);
        articleStub.changeWatchedStatus();
      }
    }

    return responseLikedArticles;
  }

  //TODO: УДАЛИТЬ!!! это заглушка
  public void addRandomArticlesToUser(long chatId) {
    for (int i = 0; i < 12; i++) {
      if (i % 2 == 0) {
        addArticle(chatId, "МТС", "Айти", "12:34", "23.04", "https://balashiha.mts.ru/personal", "Мтс внатуре крутые ребята ёмаё");
      } else {
        addArticle(chatId, "Яндекс", "Доставка еды", "23:15", "01.01", "https://ya.ru/?npr=1&utm_referrer=https%3A%2F%2Fwww.google.com%2F", "");
      }
    }
  }

  public void addArticle(long chatId, String articleName, String articleCategoryName, String articleParsedTime, String articleParsedDate, String articleUrl, String briefContent) {
    long articleId = getNextArticleId();
    articlesForUsers.computeIfAbsent(chatId, k -> new HashMap<>()).put(articleId,
        new ArticleStub(articleId, articleName, articleCategoryName, articleParsedTime, articleParsedDate, articleUrl, briefContent));
  }

  public void deleteUnneededUserArticles(long chatId) {
    Map<Long, ArticleStub> userArticles = articlesForUsers.getOrDefault(chatId, new HashMap<>());
    List<Long> articlesIdToDelete = new ArrayList<>();
    for (long key : userArticles.keySet()) {
      ArticleStub articleStub = userArticles.get(key);
      if (articleStub.getWatchedStatus()) {
        if (!articleStub.getFavoriteStatus()) {
          articlesIdToDelete.add(key);
        } else {
          articleStub.changeWatchedStatus();
        }
      }
    }
    for (long articleId : articlesIdToDelete) {
      userArticles.remove(articleId);
    }
  }

  public void deleteAllUserArticles(long chatId) {
    articlesForUsers.remove(chatId);
  }

  public void changeUserArticleStatusBrief(long chatId, long articleId) {
    articlesForUsers.get(chatId).get(articleId).changeStatusOfWatchingBriefContent();
  }

  public void changeUserArticleFavoriteStatus(long chatId, long articleId) {
    articlesForUsers.get(chatId).get(articleId).changeFixedStatus();
  }

  private static long getNextArticleId() {
    return ++nextId;
  }
}

package app.connect_java_with_python;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.User;
import app.api.entity.Website;
import app.api.repository.ArticleRepository;
import app.api.repository.CategoryRepository;
import app.api.repository.WebsiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ArticleTrans {
  private final ArticleRepository articleRepository;
  private final WebsiteRepository websiteRepository;
  private final CategoryRepository categoryRepository;

  public ArticleTrans (
      ArticleRepository articleRepository,
      WebsiteRepository websiteRepository,
      CategoryRepository categoryRepository
  ) {
    this.articleRepository = articleRepository;
    this.websiteRepository = websiteRepository;
    this.categoryRepository = categoryRepository;
  }


  @Transactional
  public void addArticleByUser(User user) {
    List<Website> websites = websiteRepository.findAllWebsitesByUser_ChatId(user.getChatId());
    log.info("website db");
    List<String> categories =
        categoryRepository.
            findCategoriesByUser_ChatId(user.getChatId())
            .stream()
            .filter(Category::isEnabled)
            .map(Category::getName)
            .toList();
    log.info(categories.toString() + "  " + categories.get(0));
    log.info(websites.get(0).getSourceUrl() + " asdasdasd");
    for (Website website : websites) {
      if (website.isSourceActiveStatus()) {
        // map<url, text>
        Map<String, String> articles = RestClient.getListArticleByUrl(website.getSourceUrl());
        // map<url, category>
        Map<String, String> categoryByUrl = new HashMap<>();

        if (articles == null) {
          log.error("failed get article from user {} and url {}", user.getChatId(), website.getSourceUrl());
          continue;
        }

        for (Map.Entry<String, String> item : articles.entrySet()) {
          Integer idx = RestClient.getCategoryArticle(item.getValue(), categories);

          // Проверяем, что индекс непустой и в пределах списка
          if (idx != null && idx >= 0 && idx < categories.size()) {
            categoryByUrl.put(item.getKey(), categories.get(idx));
          } else {
            log.warn("не удалось или не нашлась категория для статьи с url: {} и статьей с ссылкой: {}", website.getSourceUrl(), item.getKey());
          }
//
        }
        Map<String, String> urlAndShortDiscr = Map.of();
        if (user.isBriefContentOfArticlesStatus()) {
          // map<url, shortDiscr>
          urlAndShortDiscr = new HashMap<>();
          for (Map.Entry<String, String> item : articles.entrySet()) {
            urlAndShortDiscr.put(item.getKey(), RestClient.getRetelling(item.getKey()));
          }
        }
        // map<url, name (around 10 word)>
        Map<String, String> urlAndName = new HashMap<>();
        for (Map.Entry<String, String> item : articles.entrySet()) {
          urlAndName.put(item.getKey(), RestClient.getNameArticle(item.getValue()));
        }

        for (String key : articles.keySet()) {
          Category category = categoryRepository.findCategoriesByNameAndUser_ChatId(categoryByUrl.get(key), user.getChatId());
          Article article = Article.builder()
              .name(urlAndName.get(key))
              .url(key)
              .website(website)
              .creationDate(OffsetDateTime.now())
              .briefContent(urlAndShortDiscr.get(key))
              .statusOfWatchingBriefContent(false)
              .favoriteStatus(false)
              .watchedStatus(false)
              .user(user)
              .category(category)
              .build();
          articleRepository.save(article);
        }
      }
    }
  }
}

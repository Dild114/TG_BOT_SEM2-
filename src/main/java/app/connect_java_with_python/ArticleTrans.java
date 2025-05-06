package app.connect_java_with_python;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.User;
import app.api.entity.Website;
import app.api.repository.ArticleRepository;
import app.api.repository.CategoryRepository;
import app.api.repository.UserRepository;
import app.api.repository.WebsiteRepository;
import jakarta.ws.rs.OPTIONS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class ArticleTrans {
  private final ArticleRepository articleRepository;
  private final WebsiteRepository websiteRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public ArticleTrans (
      ArticleRepository articleRepository,
      WebsiteRepository websiteRepository,
      CategoryRepository categoryRepository,
      UserRepository userRepository
  ) {
    this.articleRepository = articleRepository;
    this.websiteRepository = websiteRepository;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
  }


  @Transactional
  public void addArticleByUser(Long chatId) {
    Optional<User> userOptional = userRepository.findById(chatId);
    User user = null;
    if (userOptional.isPresent()) {
      user = userOptional.get();
    }
    List<Website> websites = websiteRepository.findAllWebsitesByUser_ChatId(user.getChatId());
    log.info("website db");
    List<String> categories =
        categoryRepository.
            findCategoriesByUser_ChatId(user.getChatId())
            .stream()
            .filter(Category::isEnabled)
            .map(Category::getName)
            .toList();
    log.info(categories.toString() + " ss  " + categories.get(0));
    log.info(websites.get(0).getSourceUrl() + " asdasdasd");
    for (Website website : websites) {
      if (website.isSourceActiveStatus()) {
        // map<url, text>
        Map<String, String> articles = RestClient.getListArticleByUrl(website.getSourceUrl());
        if (!articles.isEmpty()) {
          log.info(articles.toString());
          log.info("успешно спарсил");
        }
        // map<url, category>
        Map<String, String> categoryByUrl = new HashMap<>();

        if (articles == null) {
          log.error("failed get article from user {} and url {}", user.getChatId(), website.getSourceUrl());
          continue;
        }
        // map<url, text>
        Map<String, String> newArticles = new HashMap<>();
        for (Map.Entry<String, String> item : articles.entrySet()) {
          Integer idx = RestClient.getCategoryArticle(item.getValue(), categories);
          log.info("после запроса на определение категории index: {} and text: {}", idx, item.getValue());
          // Проверяем, что индекс непустой и в пределах списка
          if (idx != null && idx >= 0 && idx < categories.size()) {
            categoryByUrl.put(item.getKey(), categories.get(idx));
            newArticles.put(item.getKey(), item.getValue());
          } else {
            log.warn("не удалось или не нашлась категория для статьи с url: {} и статьей с ссылкой: {} и индекс категории: {}", website.getSourceUrl(), item.getKey(), idx);
          }
//
        }
        Map<String, String> urlAndShortDiscr = Map.of();
        if (user.isBriefContentOfArticlesStatus()) {
          // map<url, shortDiscr>
          log.info("включено кратное описание");
          urlAndShortDiscr = new HashMap<>();
          for (Map.Entry<String, String> item : newArticles.entrySet()) {
            urlAndShortDiscr.put(item.getKey(), RestClient.getRetelling(item.getKey()));
          }
        }
        // map<url, name (around 10 word)>
        Map<String, String> urlAndName = new HashMap<>();
        for (Map.Entry<String, String> item : newArticles.entrySet()) {
          urlAndName.put(item.getKey(), RestClient.getNameArticle(item.getValue()));
          log.info("формируем название для сайта {}", item.getKey());
        }

        for (String key : newArticles.keySet()) {
          log.info("добавляем в бд");
          if (categoryByUrl.get(key) != null) {
            Category category = categoryRepository.findCategoriesByNameAndUser_ChatId(categoryByUrl.get(key), user.getChatId());
            Article article = Article.builder()
                .name(urlAndName.get(key))
                .url(key)
                .website(website)
                .briefContent(urlAndShortDiscr.get(key))
                .creationDate(OffsetDateTime.now())
                .statusOfWatchingBriefContent(false)
                .favoriteStatus(false)
                .watchedStatus(false)
                .user(user)
                .category(category)
                .build();
            Article savedArticle = articleRepository.save(article);
            user.getArticles().add(savedArticle);
            userRepository.save(user);
          }
        }
      }
    }
  }
}

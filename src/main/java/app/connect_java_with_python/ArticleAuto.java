package app.connect_java_with_python;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.User;
import app.api.entity.Website;
import app.api.repository.ArticleRepository;
import app.api.repository.CategoryRepository;
import app.api.repository.UserRepository;
import app.api.repository.WebsiteRepository;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ArticleAuto {
  private final UserRepository userRepository;
  private final ArticleTrans articleTrans;

  public ArticleAuto(
      UserRepository userRepository,
      ArticleTrans articleTrans
  ) {
    this.userRepository = userRepository;
    this.articleTrans = articleTrans;
  }

  // 5 минут
  @Scheduled(fixedDelay = 300_000)
  public void addArticleByAllUsers() {
    List<User> allUsers = userRepository.findAll();
    for (User user : allUsers) {
      articleTrans.addArticleByUser(user);
    }
  }
}

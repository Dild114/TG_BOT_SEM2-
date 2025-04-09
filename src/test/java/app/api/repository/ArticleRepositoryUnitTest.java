package app.api.repository;

//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.transaction.support.TransactionTemplate;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import app.api.entity.Article;
//import app.api.entity.ArticleId;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest(properties = {
//    "spring.jpa.hibernate.ddl-auto=create-drop"
//})
//@ActiveProfiles({"test"})
//@Testcontainers
//class ArticleRepositoryUnitTest {
//  @Container
//  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
//      "postgres:16-alpine"
//  );
//  private static String url;
//  private static String name;
//  private static String password;
//
//  @DynamicPropertySource
//  static void configureProperties(DynamicPropertyRegistry registry) {
//    registry.add("spring.datasource.url", postgres::getJdbcUrl);
//    registry.add("spring.datasource.username", postgres::getUsername);
//    registry.add("spring.datasource.password", postgres::getPassword);
//    url = postgres.getJdbcUrl();
//    name = postgres.getUsername();
//    password = postgres.getPassword();
//  }
//
//  @Autowired
//  private ArticleRepository articleRepository;
//
//  @Autowired
//  private TransactionTemplate transactionTemplate;
//
//  @Test
//  @Sql(statements = "CREATE SEQUENCE IF NOT EXISTS article_id_seq START WITH 1 INCREMENT BY 1;")
//  void shouldTest() {
//
//    transactionTemplate.execute(status -> {// смотрим что в БД ничего нет
//      final var count = articleRepository.count();
//      assertThat(count).isEqualTo(0);
//
//      // создаем новый Article который хотим сохранить
//      // В new ArticleId() мы НЕ задаем значение - оно будет автоматом генериться
//      final var initArticleId = new ArticleId();
//     // final var initArticle = new Article(initArticleId, "name", "url", 1L, 1L);
//    //  final var savedArticle = articleRepository.save(initArticle);
//      // У savedArticle - уже id проставился автоматом в 1
//      // у initArticle он не проставился. Потому что initArticle и savedArticle - это два разных java-объекта
//      return null;
//    });
//
//    transactionTemplate.execute(status -> {
//      final var afterTestCount = articleRepository.findAll();
//      assertThat(afterTestCount.size()).isEqualTo(1);
//      return null;
//    });
//  }
//}




import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.repository.ArticleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleRepositoryTest {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
          .withDatabaseName("testdb")
          .withUsername("test")
          .withPassword("test");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  private ArticleRepository articleRepository;

  @Test
  @Order(1)
  void shouldSaveAndFindArticle() {
    final var articleId = new ArticleId(1L);
    final var article = Article.builder().name("a").id(articleId).url("123").build();

    articleRepository.save(article);
    final var found = articleRepository.findById(articleId).orElse(null);

    assertThat(found).isNotNull();
    assertThat(found).isEqualTo(article);
  }

  @Test
  @Order(2)
  void shouldDeleteArticle() {
    final var articleId = new ArticleId(2L);

    articleRepository.save(Article.builder().name("a").id(articleId).url("123").build());

    articleRepository.deleteById(articleId);

    assertThat(articleRepository.findById(articleId)).isEmpty();
  }
}
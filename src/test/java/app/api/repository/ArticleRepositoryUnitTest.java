package app.api.repository;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import app.api.entity.Article;
import app.api.entity.ArticleId;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@ActiveProfiles({"test"})
@Testcontainers
class ArticleRepositoryUnitTest {
  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      "postgres:16-alpine"
  );

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  private ArticleRepository articleRepository;

  @Test
  @Sql(statements = "CREATE SEQUENCE IF NOT EXISTS article_id_seq START WITH 1 INCREMENT BY 1;")
  void shouldTest() {
    // смотрим что в БД ничего нет
    final var count = articleRepository.count();
    assertThat(count).isEqualTo(0);

    // создаем новый Article который хотим сохранить
    // В new ArticleId() мы НЕ задаем значение - оно будет автоматом генериться
    final var initArticleId = new ArticleId();
    final var initArticle = new Article(initArticleId, "name", "url", 1L, 1L);
    final var savedArticle = articleRepository.save(initArticle);
    // У savedArticle - уже id проставился автоматом в 1
    // у initArticle он не проставился. Потому что initArticle и savedArticle - это два разных java-объекта

    final var afterTestCount = articleRepository.findAll();
    assertThat(afterTestCount).isEqualTo(1);

  }
}
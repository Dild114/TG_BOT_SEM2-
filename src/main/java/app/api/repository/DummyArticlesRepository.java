package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.CategoryId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DummyArticlesRepository implements ArticlesRepository {
  private final List<Article> repository = new ArrayList<>();
  private final AtomicLong countId = new AtomicLong(0);

  public DummyArticlesRepository() {
    repository.add(new Article("TEST", new ArticleId(1), "URL", new CategoryId(1)));
  }

  @Override
  public Long generateId() {
    return countId.incrementAndGet();
  }

  @Override
  public List<Article> getArticles() {
    return repository;
  }

  @Override
  public void add(Article article) {
    repository.add(article);
  }
}

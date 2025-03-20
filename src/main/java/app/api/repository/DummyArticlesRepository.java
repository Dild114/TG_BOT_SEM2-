package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DummyArticlesRepository implements ArticlesRepository {
  private final List<Article> repository;
  private final AtomicLong countId = new AtomicLong(0);

    public DummyArticlesRepository() {
        this.repository = new ArrayList<>();
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

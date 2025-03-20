package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.UserId;

import java.util.List;
import java.util.Map;


public interface ArticlesRepository {
  Long generateId();

  List<Article> getArticles();

  void add(Article article);
}

package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository

public interface ArticleRepository extends JpaRepository<Article, ArticleId> {
  List<Article> findByTitleContaining(String keyword);
  List<Article> findByCategoryId(ArticleId categoryId);
  List<Article> findByUserId(ArticleId userId);
  List<Article> findByCategory(Category category);
}

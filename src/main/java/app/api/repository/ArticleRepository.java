package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, ArticleId> {
  Set<Article> findArticlesByUserId(Long userId);
}
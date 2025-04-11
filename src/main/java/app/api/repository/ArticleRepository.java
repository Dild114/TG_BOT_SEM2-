package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, ArticleId> {
  Optional<Article> findByUrl(String url);

  @Query("select a from Article a join fetch a.categories c where c.name = :category")
  List<Article> findAllByCategoryName(@Param("category") String category);
}
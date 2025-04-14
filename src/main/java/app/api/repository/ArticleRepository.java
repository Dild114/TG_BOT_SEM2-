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

  @Query("""
  SELECT a FROM Article a
  WHERE a.user.id = :userId
    AND a.creationDate >= CURRENT_TIMESTAMP - a.user.messageStorageTimeDay * 1
  """)
  Set<Article> findActiveArticlesByUserId(@Param("userId") Long userId);


  @Query(value = """
  DELETE FROM article a
  WHERE a.user_id = :userId
    AND a.creation_date < CURRENT_TIMESTAMP - INTERVAL a.message_storage_time_day DAY
""", nativeQuery = true)
  void deleteExpiredArticlesByUserId(@Param("userId") Long userId);


}
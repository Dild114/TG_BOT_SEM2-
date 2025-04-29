package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, ArticleId> {
  Set<Article> findArticlesByUserId(Long userId);

  @Query("""
    SELECT a FROM Article a
    WHERE a.user.id = :userId
      AND a.creationDate >= FUNCTION('DATE_SUB', CURRENT_TIMESTAMP, a.user.messageStorageTimeDay, 'DAY')
    """)
  Set<Article> findActiveArticlesByUserId(@Param("userId") Long userId);

  @Modifying
  @Query(value = """
    DELETE FROM article a
    WHERE a.user_id = :userId
      AND a.creation_date < CURRENT_TIMESTAMP - INTERVAL '1 day' * a.message_storage_time_day
    """, nativeQuery = true)
  void deleteExpiredArticlesByUserId(@Param("userId") Long userId);
}
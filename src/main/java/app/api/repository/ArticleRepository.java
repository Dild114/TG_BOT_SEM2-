package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  List<Article> findArticlesByUser_ChatId(Long userId);

  @Modifying
  @Query(value = """
    DELETE FROM articles
    WHERE user_id = :chatId
        AND watched_status = true
        AND favorite_status = false;
    """, nativeQuery = true)
  void deleteUnneededUserArticles(@Param("chatId") Long chatId);
}
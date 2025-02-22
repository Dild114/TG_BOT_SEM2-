package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ArticleRepository extends JpaRepository<Article, ArticleId> {

//  Map<Article, Category> findAllByUserId(UserId userId);
}

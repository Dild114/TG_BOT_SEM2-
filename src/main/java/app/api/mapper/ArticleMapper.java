package app.api.mapper;

import app.api.dto.ArticleDto;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.entity.Article;
import app.api.entity.ArticleId;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component

public class ArticleMapper {
  public ArticleDto toDto(Article article) {
    return ArticleDto.builder()
            .id(article.getId().getId())
            .name(article.getName())
            .url(article.getUrl())
            .creationDate(article.getCreationDate())
            .categoryIds(categoryToLong(article.getCategories()))
            .userIds(userToLong(article.getUsers()))
            .build();
  }

  public Article toEntity(ArticleDto articleDto) {
    return Article.builder()
            .id(new ArticleId(articleDto.getId()))
            .name(articleDto.getName())
            .url(articleDto.getUrl())
            .categories(longToCategory(articleDto.getCategoryIds()))
            .users(longToUser(articleDto.getUserIds()))
            .build();
  }

  private Set<Category> longToCategory(Set<Long> ids) {
    return ids.stream()
            .map(id -> Category.builder()
                    .id(new CategoryId(id))
                    .build()
            )
            .collect(Collectors.toSet());
  }

  private Set<User> longToUser(Set<Long> ids) {
    return ids.stream()
            .map(id -> User.builder()
                    .userId(new UserId(id))
                    .build()
            )
            .collect(Collectors.toSet());
  }

  private Set<Long> categoryToLong(Set<Category> categories) {
    return categories.stream()
            .map(c -> c.getId().getId())
            .collect(Collectors.toSet());
  }

  private Set<Long> userToLong(Set<User> users) {
    return users.stream()
            .map(u -> u.getUserId().getId())
            .collect(Collectors.toSet());
  }
}

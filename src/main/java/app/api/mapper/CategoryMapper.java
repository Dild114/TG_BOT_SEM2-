package app.api.mapper;

import app.api.dto.CategoryDto;
import app.api.entity.*;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component

public class CategoryMapper {
  public CategoryDto toDto(Category category) {
    return CategoryDto.builder()
            .id(category.getId().getId())
            .name(category.getName())
            .userIds(userToLong(category.getUsers()))
            .articleIds(articleToLong(category.getArticles()))
            .build();
  }

  public Category toEntity(CategoryDto categoryDto) {
    return Category.builder()
            .id(new CategoryId(categoryDto.getId()))
            .name(categoryDto.getName())
            .users(longToUser(categoryDto.getUserIds()))
            .articles(longToArticle(categoryDto.getArticleIds()))
            .build();
  }

  private Set<Long> userToLong(Set<User> users) {
    return users.stream()
            .map(u -> u.getUserId().getId())
            .collect(Collectors.toSet());
  }

  private Set<Long> articleToLong(Set<Article> articles) {
    return articles.stream()
            .map(a -> a.getId().getId())
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

  private Set<Article> longToArticle(Set<Long> ids) {
    return ids.stream()
            .map(id -> Article.builder()
                    .id(new ArticleId(id))
                    .build()
            )
            .collect(Collectors.toSet());
  }
}

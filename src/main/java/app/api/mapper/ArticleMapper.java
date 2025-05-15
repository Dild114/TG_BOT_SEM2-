package app.api.mapper;

import app.api.dto.ArticleDto;
import app.api.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

  public static ArticleDto toDto(Article article) {
    return ArticleDto.builder()
        .id(article.getId())
        .name(article.getName())
        .url(article.getUrl())
        .creationDate(article.getCreationDate())
        .category(article.getCategory() != null ? article.getCategory().getName() : null)
        .website(article.getWebsite() != null ? article.getWebsite().getSourceName() : null)
        .statusOfWatchingBriefContent(article.getStatusOfWatchingBriefContent())
        .favoriteStatus(article.getFavoriteStatus())
        .build();
  }

  public Article toEntity(ArticleDto articleDto) {
    // Создание сущности на основе DTO
    Article article = new Article();
    article.setId(articleDto.getId());
    article.setName(articleDto.getName());
    article.setUrl(articleDto.getUrl());
    article.setCreationDate(articleDto.getCreationDate());
    // Здесь должен быть логика для поиска категории и сайта по id
    // Например, через репозитории
    // article.setCategory(categoryRepository.findById(articleDto.getCategoryId()));
    // article.setWebsite(websiteRepository.findById(articleDto.getWebsiteId()));
    return article;
  }
}

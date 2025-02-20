package app.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Article", description = "Сущность пользователя")
public record Article(
    @Schema(description = "Имя Статьи", example = "ML")
    String name,
    @Schema(description = "Уникальный идентификатор", example = "123")
    ArticleId id,
    @Schema(description = "Url ссылка статьи", example = "https://habr.com/ru/articles/814061/")
    String url,
    @Schema(description = "Уникальный идентификатор категории статьи", example = "234")
    CategoryId categoryId
) {}

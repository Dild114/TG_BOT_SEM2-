package app.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Schema(name = "Article", description = "Сущность статьи")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @EmbeddedId
    @Schema(description = "Уникальный идентификатор", example = "123")
    private ArticleId id;

    @Schema(description = "Имя статьи", example = "ML")
    private String name;

    @Schema(description = "Url ссылка статьи", example = "https://habr.com/ru/articles/814061/")
    private String url;

    @Schema(description = "Уникальный идентификатор категории статьи", example = "234")
    private Long categoryId;

    @Schema(description = "Уникальный идентификатор сайта статьи", example = "123")
    private Long websiteId;

}

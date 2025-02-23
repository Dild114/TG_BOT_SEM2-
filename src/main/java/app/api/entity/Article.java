package app.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}

package app.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Site", description = "Сущность сайта")
public record Site(
    @Schema(description = "Уникальный идентификатор", example = "123")
    SiteId id,
    @Schema(description = "Url сайта", example = "https://habr.com/ru/articles/814061/")
    String url
) {}

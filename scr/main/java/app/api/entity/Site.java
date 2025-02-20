package app.api.entity;

import app.api.controller.Sites;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Site", description = "Сущность сайта")
public record Site(
    @Schema(description = "Уникальный идентификатор", example = "123")
    SiteId id,
    @Schema(description = "Url сайта", example = "https://habr.com/ru/articles/814061/")
    Sites url,
    @Schema(description = "ID пользователя", example = "234")
    UserId userId
) {}

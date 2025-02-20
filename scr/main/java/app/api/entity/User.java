package app.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "User", description = "Сущность пользователя")
public class User {
  // пока что telegramId и email не нужен и мы его не будет использовать
  @Schema(description = "ID пользователя", example = "123")
  UserId userId;
  @Schema(description = "Имя пользователя", example = "Nikolay")
  String userName;
  @Schema(description = "Пароль пользователя", example = "qwerty")
  String password;
  @Schema(description = "Email пользователя", example = "qwerty@qwerty.com")
  String email;
  @Schema(description = "Список категорий пользователя", example = "[ML,frontend, backend]")
  List<Category> categories;
  @Schema(description = "Список сайтов пользователя", example = "[https://habr.com/ru/articles/814061/, https://ru.wikipedia.org/wiki/]")
  List<Site> sites;

  public User(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }
}

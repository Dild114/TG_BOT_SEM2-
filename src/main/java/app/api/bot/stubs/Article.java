package app.api.bot.stubs;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Article {
  private final int id;
  private final String name = "\uD83D\uDCCC Название статьи";
  private final String categoryName = "Категория: Название категории";
  private final String parsedTime = "📅 21.04 | 🕓 12:30";
  private final String url = "https://moskva.mts.ru/personal";

  //TODO: обрабатывать ситуацию, когда у статьи отсутствует краткое содержание, в таком случае выводим: Карткое содержание отсутствует 🤗
  private final String briefContent = "Тут может быть краткое содержание спаршенной статьи";

  @Setter
  private Boolean statusOfWatchingBriefContent = false;

  public Article(int id) {
    this.id = id;
  }
}

package app.api.bot.stubs.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArticleStub {
  private final long articleId;
  private final String articleName;
  private final String articleCategoryName;
  private final String articleParsedDate;
  private final String articleParsedTime;
  private final String articleUrl;

  //TODO: обрабатывать ситуацию, когда у статьи отсутствует краткое содержание, в таком случае выводим: Карткое содержание отсутствует 🤗
  private final String briefContent;// = "Тут может быть краткое содержание спаршенной статьи";

  private Boolean statusOfWatchingBriefContent = false;

  private Boolean favoriteStatus = false;

  private Boolean watchedStatus = false;

  public void changeStatusOfWatchingBriefContent() {
    this.statusOfWatchingBriefContent = !this.statusOfWatchingBriefContent;
  }

  public void changeFixedStatus() {
    this.favoriteStatus = !this.favoriteStatus;
  }

  public void changeWatchedStatus() {
    this.watchedStatus = !this.watchedStatus;
  }
}

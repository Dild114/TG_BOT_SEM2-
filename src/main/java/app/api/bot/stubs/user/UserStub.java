package app.api.bot.stubs.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserStub {
  private final long chatId;
  private int countStringsInOnePage = 5;
  private int countArticlesInOneRequest = 3;
  private boolean briefContentOfArticlesStatus = false;

  public void changeCountStringsInOnePage(int newCountPages) {
    this.countStringsInOnePage = newCountPages;
  }

  public void changeCountArticlesInOneRequest(int newCountArticles) {
    this.countArticlesInOneRequest = newCountArticles;
  }

  public void changeMakeBriefStatus(boolean newBriefStatus) {
    this.briefContentOfArticlesStatus = newBriefStatus;
  }
}

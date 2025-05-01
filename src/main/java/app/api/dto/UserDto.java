package app.api.dto;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.Website;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private Long chatId;

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

  // todo: понять надо ли это
  private Set<Website> websites = new HashSet<>();
  private Set<Category> categories = new HashSet<>();
}
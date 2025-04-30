package app.api.bot.stubs.user;

import app.api.service.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceStub {
  private final Map<Long, UserStub> users;
  private final WebsiteService sourceService;
  private final CategoryService categoryService;

  public UserServiceStub(WebsiteService sourceService, CategoryService categoryService) {
    this.users = new HashMap<>();

    this.sourceService = sourceService;
    this.categoryService = categoryService;
  }

  public void createUser(long chatId) {
    users.put(chatId, new UserStub(chatId));
  }

  public void deleteUser(long chatId) {
    //TODO: в бд по-сути должно быть реализовано как каскадное удаление
    users.remove(chatId);

//    sourceService.deleteUserSources(chatId);
//    categoryService.deleteAllUserCategories(chatId);
  }

  public int getUserCountStringsInOnePage(long chatId) {
    return users.get(chatId).getCountStringsInOnePage();
  }

  public int getUserCountArticlesInOneRequest(long chatId) {
    return users.get(chatId).getCountArticlesInOneRequest();
  }

  public boolean getUserBriefStatus(long chatId) {
    return users.get(chatId).isBriefContentOfArticlesStatus();
  }

  public void changeUserCountStringsInOnePage(long chatId, int newCountPages) {
    users.get(chatId).changeCountStringsInOnePage(newCountPages);
  }

  public void changeUserCountArticlesInOneRequest(long chatId, int newCountArticles) {
    users.get(chatId).changeCountArticlesInOneRequest(newCountArticles);
  }

  public void changeUserMakeBriefStatus(long chatId, boolean newBriefStatus) {
    users.get(chatId).changeMakeBriefStatus(newBriefStatus);
  }
}

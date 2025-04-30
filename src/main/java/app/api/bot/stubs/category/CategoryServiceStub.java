package app.api.bot.stubs;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
public class CategoryServiceStub {
  private final Map<Long, Map<Long, CategoryStub>> categoriesForUsers;
  private static long nextId = 0;

  public CategoryServiceStub() {
    this.categoriesForUsers = new HashMap<>();
  }

  public void changeStatus(long chatId, long categoryId) {
    categoriesForUsers.get(chatId).get(categoryId).changeCategoryStatus();
  }

  public void addCategory(long chatId, String categoryName) {
    Map<Long, CategoryStub> userCategories = categoriesForUsers.get(chatId);
    boolean flag = true;
    for (CategoryStub categoryStub : userCategories.values()) {
      if (categoryStub.getCategoryName().equals(categoryName)) {
        flag = false;
        break;
      }
    }
    if (flag) {
      long categoryId = getNextCategoryId();
      userCategories.put(getNextCategoryId(), new CategoryStub(categoryId, categoryName));
    }
  }

  public void deleteCategory(long chatId, long categoryId) {
    categoriesForUsers.get(chatId).remove(categoryId);
  }

  private static long getNextCategoryId() {
    return ++nextId;
  }
}

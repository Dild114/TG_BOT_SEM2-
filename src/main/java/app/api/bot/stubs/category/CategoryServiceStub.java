package app.api.bot.stubs.category;

import app.api.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceStub {
  private final Map<Long, Map<Long, CategoryStub>> categoriesForUsers;
  private static long nextId = 0;

  public CategoryServiceStub() {
    this.categoriesForUsers = new HashMap<>();
  }

  public List<CategoryStub> getUserCategories(long chatId) {
    Map<Long, CategoryStub> userCategories = categoriesForUsers.getOrDefault(chatId, new HashMap<>());
    List<CategoryStub> sortedUserCategories = new ArrayList<>(userCategories.values());

    sortedUserCategories.sort(Comparator.comparingLong(CategoryStub::getCategoryId));

    return sortedUserCategories;
  }

  public void changeUserCategoryStatus(long chatId, long categoryId) {
    categoriesForUsers.get(chatId).get(categoryId).changeCategoryStatus();
  }

  public void addCategoryToUser(long chatId, String categoryName) {
    Map<Long, CategoryStub> userCategories = categoriesForUsers.computeIfAbsent(chatId, k -> new HashMap<>());
    boolean flag = true;
    for (CategoryStub categoryStub : userCategories.values()) {
      if (categoryStub.getCategoryName().equals(categoryName)) {
        flag = false;
        break;
      }
    }
    if (flag) {
      long categoryId = getNextCategoryId();
      userCategories.put(categoryId, new CategoryStub(categoryId, categoryName));
    }
  }

  public void deleteCategoryFromUser(long chatId, String categoryName) {
    Map<Long, CategoryStub> userCategories = categoriesForUsers.computeIfAbsent(chatId, k -> new HashMap<>());
    for (CategoryStub categoryStub : userCategories.values()) {
      if (categoryStub.getCategoryName().equals(categoryName)) {
        userCategories.remove(categoryStub.getCategoryId());
        break;
      }
    }
  }

  public void deleteAllUserCategories(long chatId) {
    categoriesForUsers.remove(chatId);
  }

  private static long getNextCategoryId() {
    return ++nextId;
  }
}

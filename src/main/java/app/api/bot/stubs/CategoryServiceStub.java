package app.api.bot.stubs;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Getter
@Service
public class CategoryServiceStub {
  private final LinkedHashMap<String, Boolean> categories;

  public CategoryServiceStub() {
    this.categories = new LinkedHashMap<>();
  }

  public void changeStatus(String category) {
    categories.put(category, !categories.get(category));
  }

  public void addCategory(String category) {
    if (!categories.containsKey(category)) {
      categories.put(category, true);
    }
  }

  public void deleteCategory(String category) {
    categories.remove(category);
  }
}

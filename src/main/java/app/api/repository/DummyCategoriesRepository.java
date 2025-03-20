package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Простая заглушка, использующая список в памяти
public class DummyCategoriesRepository implements CategoriesRepository {

  private final List<Category> inMemoryCategories = new ArrayList<>();

  @Override
  public List<Category> findAll(UserId userId) {
    // Фильтруем категории по ID пользователя
    List<Category> userCategories = new ArrayList<>();
    for (Category category : inMemoryCategories) {
      if (category.userId().equals(userId)) {
        userCategories.add(category);
      }
    }
    return userCategories;
  }

  @Override
  public Category findById(CategoryId id) {
    return inMemoryCategories.stream()
      .filter(category -> category.id().equals(id))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
  }

  @Override
  public void delete(CategoryId id) {
    inMemoryCategories.removeIf(category -> category.id().equals(id));
  }

  @Override
  public void create(Category category) {
    inMemoryCategories.add(category);
  }

  @Override
  public CategoryId getCategoryId() {
    // Генерация простого идентификатора
    return new CategoryId(1);
  }
}
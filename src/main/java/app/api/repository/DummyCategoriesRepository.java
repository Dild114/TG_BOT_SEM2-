package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class DummyCategoriesRepository implements CategoriesRepository {

  private final List<Category> repository = new ArrayList<>();
  private final AtomicLong countId = new AtomicLong(0);

  @Override
  public List<Category> findAll(UserId userId) {
    List<Category> userCategories = new ArrayList<>();
    for (Category category : repository) {
      if (category.userId().equals(userId)) {
        userCategories.add(category);
      }
    }
    log.info("Categories found: " + userCategories.size());
    return userCategories;
  }

  @Override
  public Category findById(CategoryId id) {
    for (Category category : repository) {
      if (category.id().equals(id)) {
        log.info("Category found: " + id);
        return category;
      }
    }
    log.warn("Category not found: " + id);
    throw new IllegalArgumentException("Category not found: " + id);
  }

  @Override
  public void delete(CategoryId id) {
    for (Category category : repository) {
      if (category.id().equals(id)) {
        repository.remove(category);
        return;
      }
    }
    log.warn("Category not found: " + id);
  }

  @Override
  public void create(Category category) {
    repository.add(category);
  }

  @Override
  public CategoryId getCategoryId() {
    return new CategoryId(countId.incrementAndGet());
  }
}
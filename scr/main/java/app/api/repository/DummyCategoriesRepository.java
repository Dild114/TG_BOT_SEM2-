package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DummyCategoriesRepository implements CategoriesRepository {
  int countId= 0;
  @Override
  public CategoryId getCategoryId() {
    countId += 1;
    return new CategoryId(countId);
  }

  @Override
  public List<Category> findAll(UserId userId) {
    return List.of();
  }

  @Override
  public Category findById(CategoryId id) {
    return null;
  }

  @Override
  public CategoryId delete(CategoryId id, UserId userId) {
    return null;
  }

  @Override
  public boolean create(Category category) {
    return false;
  }
}

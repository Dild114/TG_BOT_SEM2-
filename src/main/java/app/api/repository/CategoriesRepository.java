package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;

import java.util.List;

public interface CategoriesRepository {
  CategoryId getCategoryId();

  List<Category> findAll(UserId userId);

  Category findById(CategoryId id);

  CategoryId delete(CategoryId id);

  boolean create(Category category);
}

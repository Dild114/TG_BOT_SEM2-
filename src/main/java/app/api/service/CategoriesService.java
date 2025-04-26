package app.api.service;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.repository.CategoriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoriesService {
  private final CategoriesRepository categoriesRepository;

  public CategoriesService(CategoriesRepository categoriesRepository) {
    this.categoriesRepository = categoriesRepository;
  }

  public List<Category> findAll(UserId userId) {
    log.info("Finding all categories");
    return categoriesRepository.findAll(userId);
  }
  public Category findById(CategoryId id) {
    Category category = categoriesRepository.findById(id);
    log.info("findById({})", id);
   return category;
  }
  public void delete(CategoryId id, UserId userId) {
    categoriesRepository.delete(id, userId);
    log.info("Deleting Category with id {}", id);
  }

  public CategoryId create(String name, UserId userId) {
    CategoryId categoryId = categoriesRepository.getCategoryId();
    Category category = new Category(categoryId, name, userId);
    categoriesRepository.create(category);
    log.info("Creating Category with name {}", name);
    return categoryId;
  }
}

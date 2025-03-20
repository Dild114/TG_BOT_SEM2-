package app.api.controller;

import app.api.controller.requests.CategoryRequest;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.service.CategoriesService;
import app.api.controller.interfaceDrivenControllers.CategoryControllerInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CategoriesController implements CategoryControllerInterface {
  private final CategoriesService categoriesService = new CategoriesService();

  @Override
  public ResponseEntity<List<Category>> getMyCategories(Long userId) {
    log.info("Fetching categories for userId: {}", userId);
    // TODO: исключение
    List<Category> categories = categoriesService.findAll(new UserId(userId));
    return ResponseEntity.status(HttpStatus.OK).body(categories);
  }


  @Override
  public ResponseEntity<Category> getCategoryById(Long categoryId) {
    //TODO: исключение
    log.info("Fetching category with ID: {}", categoryId);
    Category category = categoriesService.findById(new CategoryId(categoryId));
    return ResponseEntity.status(HttpStatus.OK).body(category);
  }


  @Override
  public ResponseEntity<CategoryId> createCategoryForUser(CategoryRequest categoryRequest, Long userId) {
    //TODO: исключение
    log.info("Creating category for userId: {}", userId);
    CategoryId categoryId = categoriesService.create(categoryRequest.name(), new UserId(userId));
    return ResponseEntity.status(HttpStatus.OK).body(categoryId);
  }


  @Override
  public ResponseEntity<Void> deleteUserCategories(Long userId) {
    //TODO
    log.info("Deleting all categories for userId: {}", userId);
    categoriesService.deleteUser(new UserId(userId));
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


  @Override
  public ResponseEntity<Void> deleteCategory(Long categoryId) {
    log.info("Deleting category with ID: {}", categoryId);
    // TODO: исключение
    categoriesService.deleteCategory(new CategoryId(categoryId));
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

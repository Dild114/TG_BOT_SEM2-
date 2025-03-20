package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;

import java.util.List;

public interface CategoriesRepository {
  List<Category> findAll(UserId userId); // Найти все категории пользователя
  Category findById(CategoryId id);     // Найти категорию по ID
  void delete(CategoryId id);           // Удалить категорию по ID
  void create(Category category);       // Создать новую категорию
  CategoryId getCategoryId();           // Генерация нового ID категории
}
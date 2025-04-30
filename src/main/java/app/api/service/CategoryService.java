package app.api.service;

import app.api.bot.stubs.exceptions.*;
import app.api.dto.CategoryDto;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.User;
import app.api.mapper.CategoryMapper;
import app.api.repository.CategoryRepository;
import app.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;
  private final UserRepository userRepository;

  @Transactional
  public void addCategoryToUser(Long chatId, String categoryName) {
    User user = userRepository.findById(chatId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + chatId));
    List<Category> userCategories = categoryRepository.findCategoriesByUser_ChatId(chatId);
    List<CategoryDto> categoriesDto = userCategories.stream()
        .map(categoryMapper::toDto)
        .toList();

    boolean flag = true;
    for (CategoryDto categoryDto : categoriesDto) {
      if (categoryDto.getCategoryName().equals(categoryName)) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        user.getCategories().add(savedCategory);
        userRepository.save(user);
        return;
      }
    }
    throw new InvalidValueException("Категория с названием " + categoryName + " ранее была добавлена пользователю " + chatId);
  }

  @Transactional
  public void deleteCategoryFromUser(Long chatId, String categoryName) {
    User user = userRepository.findById(chatId)
      .orElseThrow(() -> new EntityNotFoundException("User not found: " + chatId));
    List<Category> userCategories = categoryRepository.findCategoriesByUser_ChatId(chatId);
    for (Category category : userCategories) {
      if (category.getName().equals(categoryName)) {
        categoryRepository.delete(category);
        user.getCategories().remove(category);
        userRepository.save(user);
        return;
      }
    }
    throw new InvalidValueException("Категория с названием " + categoryName + " не найдена у пользователя " + chatId);
  }

  @Transactional(readOnly = true)
  public List<CategoryDto> getUserCategories(Long chatId) {
    List<Category> categories = categoryRepository.findCategoriesByUser_ChatId(chatId);
    List<CategoryDto> categoriesDto = categories.stream()
        .map(categoryMapper::toDto)
        .collect(Collectors.toList());
    categoriesDto.sort(Comparator.comparingLong(CategoryDto::getCategoryId));
    return categoriesDto;
  }

  @Transactional
  public void changeUserCategoryStatus(Long chatId, Long categoryId) {
    Category category = categoryRepository.findById(new CategoryId(categoryId))
        .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));

    category.setEnabled(!category.isEnabled());
    categoryRepository.save(category);
  }

  @Transactional
  public void deleteAllUserCategories(long chatId) {
    User user = userRepository.findById(chatId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + chatId));
    List<Category> userCategories  = user.getCategories();
    categoryRepository.deleteAll(userCategories);
  }
}
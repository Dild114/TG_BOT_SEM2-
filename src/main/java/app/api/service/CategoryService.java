package app.api.service;

import app.api.dto.CategoryDto;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.User;
import app.api.entity.UserId;
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
  public CategoryId addCategoryToUser(CategoryDto categoryDto) {
      Long userId = categoryDto.getUserId();
      User user = userRepository.findById(new UserId(userId))
          .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

      Category category = categoryMapper.toEntity(categoryDto);
      category.setUser(user);
      categoryRepository.save(category);
      return new CategoryId(category.getId());
    }

  @Transactional
  public void deleteCategoryFromUser(Long userId, Long categoryId) {
      User user = userRepository.findById(new UserId(userId))
          .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
      Category category = categoryRepository.findById(new CategoryId(categoryId))
          .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));

      user.getCategories().remove(category);
      category.setUser(null);
      categoryRepository.save(category);
  }

  @Transactional(readOnly = true)
  public List<CategoryDto> getUserCategories(Long chatId) {
    List<Category> categories = categoryRepository.findCategoriesByUserId(chatId);
    List<CategoryDto> categoriesDto = categories.stream()
        .map(categoryMapper::toDto)
        .collect(Collectors.toList());

  }

  @Transactional
  public void changeUserCategoryStatus(Long categoryId, boolean enabled) {
    Category category = categoryRepository.findById(new CategoryId(categoryId))
        .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));

    category.setEnabled(enabled);
    categoryRepository.save(category);
  }
}
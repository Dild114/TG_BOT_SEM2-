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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;
  private final UserRepository userRepository;

  @Transactional
  public CategoryId addCategoryByUserId(CategoryDto categoryDto) {
      Long userId = categoryDto.getUserId();
      User user = userRepository.findById(new UserId(userId))
          .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

      Category category = categoryMapper.toEntity(categoryDto);
      category.setUser(user);
      categoryRepository.save(category);
      return new CategoryId(category.getId());
    }

  @Transactional
  public void deleteCategory(Long userId, Long categoryId) {
      User user = userRepository.findById(new UserId(userId))
          .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
      Category category = categoryRepository.findById(new CategoryId(categoryId))
          .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));

      user.getCategories().remove(category);
      category.setUser(null);
      categoryRepository.save(category);
  }

  @Transactional(readOnly = true)
  public Set<CategoryDto> getCategoriesByUserId(Long userId) {
    return categoryRepository.
        findCategoriesByUserId(userId)
        .stream()
        .map(categoryMapper::toDto)
        .collect(Collectors.toSet());
  }

  @Transactional
  public void setCategoryEnabled(Long categoryId, boolean enabled) {
    Category category = categoryRepository.findById(new CategoryId(categoryId))
        .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));

    category.setEnabled(enabled);
    categoryRepository.save(category);
  }
}
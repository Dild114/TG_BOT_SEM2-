package app.api.service;

import app.api.dto.CategoryDto;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.mapper.CategoryMapper;
import app.api.repository.CategoryRepository;
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

  @Transactional(readOnly = true)
  public CategoryDto getCategory(Long id) {
    Category category = categoryRepository.findById(new CategoryId(id))
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    return categoryMapper.toDto(category);
  }

  @Transactional(readOnly = true)
  public List<CategoryDto> findAll() {
    return categoryRepository.findAll().stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
  }

  @Transactional
public void saveCategory(CategoryDto categoryDto) {
    categoryRepository.save(categoryMapper.toEntity(categoryDto));
  }

  @Transactional
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(new CategoryId(id));
  }

  @Transactional(readOnly = true)
  public Set<CategoryDto> getCategoriesByUserId(Long userId) {
    return categoryRepository.
        findCategoriesByUserId(userId)
        .stream()
        .map(categoryMapper::toDto)
        .collect(Collectors.toSet());
  }
}
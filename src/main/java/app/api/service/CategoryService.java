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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service

public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Transactional(readOnly = true) //возвращает категорию по id пользователья
  public CategoryDto getCategory(Long id) {
    Category category = categoryRepository.findById(new CategoryId(id))
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    log.info("Category id: {}", category.getId().getId());
    return categoryMapper.toDto(category);
  }

  @Transactional(readOnly = true)
  public List<CategoryDto> findAll() { //вывод всех категорий
    return categoryRepository.findAll().stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
  }

  @Transactional
public void saveCategory(CategoryDto categoryDto) { //сохранение категории
    categoryRepository.save(categoryMapper.toEntity(categoryDto));
    log.info("Category id: {} was saved", categoryDto.getId());
  }

  @Transactional
  public void deleteCategory(Long id) { //удаление категории
    categoryRepository.deleteById(new CategoryId(id));
    log.info("Category (id = {}) was deleted", id);
  }

  @Transactional(readOnly = true)
  public List<CategoryDto> getCategoriesByUserId(Long userId) { //находит и выводит все категории, на которые подписан
    //пользователь
    return categoryRepository.findCategoriesByUsersId(new UserId(userId)).stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
  }
}

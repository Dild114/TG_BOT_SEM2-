package app.api.mapper;

import app.api.dto.CategoryDto;
import app.api.entity.Category;
import app.api.entity.User;
import app.api.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  private final UserRepository userRepository;

  public CategoryMapper(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public CategoryDto toDto(Category category) {
    return CategoryDto.builder()
        .categoryId(category.getId())
        .categoryName(category.getName())
        .userId(category.getUser().getChatId())
        .categoryActiveStatus(category.isEnabled())
        .build();
  }

  public Category toEntity(CategoryDto categoryDto) {
    // Получаем пользователя по userId из dto
    User user = userRepository.findById(categoryDto.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + categoryDto.getUserId()));

    return Category.builder()
        .id(categoryDto.getCategoryId())
        .name(categoryDto.getCategoryName())
        .user(user)
        .isEnabled(categoryDto.isCategoryActiveStatus())
        .build();
  }
}

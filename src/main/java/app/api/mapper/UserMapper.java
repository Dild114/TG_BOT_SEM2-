package app.api.mapper;

import app.api.dto.UserDto;
import app.api.entity.User;
import app.api.entity.Website;
import app.api.entity.UserId;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

  public UserDto toDto(User user) {
    // Конвертация сущности в DTO
    return UserDto.builder()
        .userId(user.getId())
        .name(user.getName())
        .telegramId(user.getTelegramId())
        .isSubscribeEnabled(user.isSubscribeEnabled())
        .isShortDescriptionEnabled(user.isShortDescriptionEnabled())
        .websites(user.getWebsites())
        .build();
  }

  public User toEntity(UserDto dto) {
    // Создание сущности User из DTO
    Set<Website> websites = dto.getWebsites()
        .stream()
        .map(id -> Website.builder()
            .id(id.getId())
            .build())
        .collect(Collectors.toSet());

    return User.builder()
        .id(dto.getUserId())
        .name(dto.getName())
        .telegramId(dto.getTelegramId())
        .isSubscribeEnabled(dto.isSubscribeEnabled())
        .isShortDescriptionEnabled(dto.isShortDescriptionEnabled())
        .websites(websites)
        .build();
  }
}

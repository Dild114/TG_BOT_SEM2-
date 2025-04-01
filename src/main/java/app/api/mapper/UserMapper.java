package app.api.mapper;

import app.api.dto.UserDto;
import app.api.entity.*;
import lombok.Builder;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;
import app.api.repository.UserRepository;
import app.api.repository.WebsiteRepository;
import lombok.RequiredArgsConstructor;

@Builder
@Component
@RequiredArgsConstructor

public class UserMapper {
  private final UserRepository userRepository;
  private final WebsiteRepository websiteRepository;

  public UserDto toDto(User user) {
    return UserDto.builder()
            .userId(user.getUserId().getId())
            .telegramId(user.getTelegramId())
            .isSubscribeEnabled(user.isSubscribeEnabled())
            .isShortDescriptionEnabled(user.isShortDescriptionEnabled())
            .websiteIds(user.getWebsites()
                    .stream()
                    .map(w -> w.getWebsiteId().getId())
                    .collect(Collectors.toSet()))
            .build();
  }

  public User toEntity(UserDto dto) {
    Set<Website> websites = dto.getWebsiteIds()
            .stream()
            .map(id -> Website.builder()
                    .id(new WebsiteId(id))
                    .build())
            .collect(Collectors.toSet());

    return User.builder()
            .userId(new UserId(dto.getUserId()))
            .telegramId(dto.getTelegramId())
            .isSubscribeEnabled(dto.isSubscribeEnabled())
            .isShortDescriptionEnabled(dto.isShortDescriptionEnabled())
            .websites(websites)
            .build();
  }

  public void updateUserFromDto(UserDto dto, User user) {
    user.setTelegramId(dto.getTelegramId());
    user.setSubscribeEnabled(dto.isSubscribeEnabled());
    user.setShortDescriptionEnabled(dto.isShortDescriptionEnabled());
  }
}

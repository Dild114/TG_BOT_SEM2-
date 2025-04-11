package app.api.mapper;

import app.api.dto.WebsiteDto;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.entity.Website;
import app.api.entity.WebsiteId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WebsiteMapper {
  public  WebsiteDto toDto(Website website) {
    return WebsiteDto.builder()
            .id(website.getId().getId())
            .url(website.getUrl())
            .userIds(website.getUsers()
                    .stream()
                    .map(u -> u.getUserId().getId())
                    .collect(Collectors.toSet()))
            .build();
  }

  public Website toEntity(WebsiteDto websiteDto) {
    return Website.builder()
            .id(new WebsiteId(websiteDto.getId()))
            .url(websiteDto.getUrl())
            .users(websiteDto.getUserIds().stream()
                    .map(id -> User.builder()
                            .userId(new UserId(id))
                            .build())
                    .collect(Collectors.toSet()))
            .build();
  }
}
package app.api.dto;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.Website;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private Long userId;

  private String telegramId;

  private String name;

  private boolean isSubscribeEnabled;

  private boolean isShortDescriptionEnabled;

  private Set<Website> websites = new HashSet<>();
  private Set<Category> categories = new HashSet<>();
}
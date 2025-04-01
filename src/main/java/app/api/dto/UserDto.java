package app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class UserDto {

  private Long userId;

  private String telegramId;

  private boolean isSubscribeEnabled; //подписка включена или нет

  private boolean isShortDescriptionEnabled; //краткое описание

  private Set<Long> categoriesIds; //категории пользователя

  private Set<Long> websiteIds; //сайты пользователя

  private Set<Long> articlesIds; //сохраненные статьи
}

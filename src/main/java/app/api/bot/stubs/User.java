package app.api.bot.stubs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class User {
  @Setter
  @Getter
  private int pageSize = 5;
}

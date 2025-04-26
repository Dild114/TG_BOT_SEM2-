package app.api.bot.stubs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class User {
  private int pageSize;

  public User() {
    this.pageSize = 5;
  }
}

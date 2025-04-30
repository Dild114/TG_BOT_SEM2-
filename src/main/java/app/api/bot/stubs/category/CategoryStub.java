package app.api.bot.stubs.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryStub {
  private final long categoryId;
  private final String categoryName;

  private boolean categoryActiveStatus = true;

  public void changeCategoryStatus() {
    this.categoryActiveStatus = !this.categoryActiveStatus;
  }
}

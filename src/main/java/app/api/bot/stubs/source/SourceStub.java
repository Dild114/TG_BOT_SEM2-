package app.api.bot.stubs.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SourceStub {
  private final long sourceId;
  private final String sourceName;
  private final String sourceUrl;

  private boolean sourceActiveStatus = true;

  public void changeSourceStatus() {
    this.sourceActiveStatus = !this.sourceActiveStatus;
  }
}

package app.api.bot.stubs;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Getter
@Service
public class SourceServiceStub {
  private final LinkedHashMap<String, PairForSource> sources;

  public SourceServiceStub() {
    this.sources = new LinkedHashMap<>();
  }

  public void changeStatus(String category) {
    PairForSource pair = new PairForSource(!sources.get(category).active(), sources.get(category).url());
    sources.put(category, pair);
  }

  public void addSource(long chatId, String name, String url) {
    if (!sources.containsKey(name)) {
      sources.put(name, new PairForSource(true, url));
    }
  }

  public void deleteSource(String name) {
    sources.remove(name);
  }

}
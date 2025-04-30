package app.api.bot.stubs.source;

import app.api.bot.stubs.category.CategoryStub;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SourceServiceStub {
  private final Map<Long, Map<Long, SourceStub>> sourcesForUsers;
  private static long nextId = 0;


  public SourceServiceStub() {
    this.sourcesForUsers = new HashMap<>();
  }

  public List<SourceStub> getUserSources(long chatId) {
    Map<Long, SourceStub> userSources = sourcesForUsers.getOrDefault(chatId, new HashMap<>());
    List<SourceStub> sortedUserSources = new ArrayList<>(userSources.values());

    sortedUserSources.sort(Comparator.comparing(SourceStub::getSourceId));

    return sortedUserSources;
  }

  public void changeUserSourceStatus(long chatId, long sourceId) {
    sourcesForUsers.get(chatId).get(sourceId).changeSourceStatus();
  }

  public void addSourceToUser(long chatId, String sourceName, String sourceUrl) {
    Map<Long, SourceStub> userSources = sourcesForUsers.computeIfAbsent(chatId, k -> new HashMap<>());
    boolean flag = true;
    for (SourceStub sourceStub : userSources.values()) {
      if (sourceStub.getSourceName().equals(sourceName)) {
        flag = false;
        break;
      }
    }
    if (flag) {
      long sourceId = getNextSourceId();
      userSources.put(sourceId, new SourceStub(sourceId, sourceName, sourceUrl));
    }
  }

  public void deleteSourceFromUser(long chatId, String sourceName) {
    Map<Long, SourceStub> userSources = sourcesForUsers.computeIfAbsent(chatId, k -> new HashMap<>());
    for (SourceStub sourceStub : userSources.values()) {
      if (sourceStub.getSourceName().equals(sourceName)) {
        userSources.remove(sourceStub.getSourceId());
        break;
      }
    }
  }

  public void deleteUserSources(long chatId) {
    sourcesForUsers.remove(chatId);
  }

  private static long getNextSourceId() {
    return ++nextId;
  }
}
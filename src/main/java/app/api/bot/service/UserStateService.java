package app.api.bot.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserStateService {
  private final Map<Long, String> userStates = new ConcurrentHashMap<>();
  private final Map<Long, String> tempSourceNames = new ConcurrentHashMap<>();
  private final Map<Long, String> tempViewMode = new ConcurrentHashMap<>();

  public void setTempViewMode(long chatId, String mode) {
    tempViewMode.put(chatId, mode);
  }

  public String getTempViewMode(long chatId) {
    return tempViewMode.getOrDefault(chatId, "state");
  }

  public void setState(Long chatId, String state) {
    userStates.put(chatId, state);
  }

  public String getState(Long chatId) {
    return userStates.get(chatId);
  }

  public void clearState(Long chatId) {
    userStates.remove(chatId);
    tempSourceNames.remove(chatId);
    tempViewMode.remove(chatId);
  }

  public void setTempSourceName(Long chatId, String name) {
    tempSourceNames.put(chatId, name);
  }

  public String getTempSourceName(Long chatId) {
    return tempSourceNames.get(chatId);
  }
}


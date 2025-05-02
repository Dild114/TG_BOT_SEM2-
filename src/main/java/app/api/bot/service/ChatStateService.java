//package app.api.bot.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Service
//public class userService {
//  private final Map<Long, String> userStates = new HashMap<>();
//  private final Map<Long, String> tempSourceNames = new HashMap<>();
//  private final Map<Long, String> tempViewMode = new HashMap<>();
//
//  public void setTempViewMode(long chatId, String mode) {
//    tempViewMode.put(chatId, mode);
//  }
//
//  public String getTempViewMode(long chatId) {
//    return tempViewMode.getOrDefault(chatId, "state");
//  }
//
//  public void setState(Long chatId, String state) {
//    userStates.put(chatId, state);
//  }
//
//  public String getState(Long chatId) {
//    return userStates.get(chatId);
//  }
//
//  public void clearState(Long chatId) {
//    userStates.remove(chatId);
//    tempSourceNames.remove(chatId);
//    tempViewMode.remove(chatId);
//  }
//
//  public void setTempSourceName(Long chatId, String name) {
//    tempSourceNames.put(chatId, name);
//  }
//
//  public String getTempSourceName(Long chatId) {
//    return tempSourceNames.get(chatId);
//  }
//}
//

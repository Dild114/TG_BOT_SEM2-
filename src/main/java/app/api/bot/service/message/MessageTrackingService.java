package app.api.bot.service.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MessageTrackingService {
  private final Map<Long, List<Integer>> mapForMessagesToDelete = new HashMap<>();
  private final Map<Long, List<Integer>> mapForUndeletableMessages = new HashMap<>();
  private final Map<Long, Integer> mapForLastInlineKeyboardEnabledToUpdate = new HashMap<>();

  public void addMessageToDelete(long chatId, int messageId) {
    mapForMessagesToDelete.computeIfAbsent(chatId, k -> new ArrayList<>()).add(messageId);
  }

  public void addMessageToUndeletableMap(long chatId, int messageId) {
    mapForUndeletableMessages.computeIfAbsent(chatId, k -> new ArrayList<>()).add(messageId);
  }

  public void removeMessageToDelete(long chatId, int messageId) {
    try {
      mapForMessagesToDelete.getOrDefault(chatId, new ArrayList<>()).remove(Integer.valueOf(messageId));
      mapForUndeletableMessages.getOrDefault(chatId, new ArrayList<>()).remove(Integer.valueOf(messageId));
    } catch (Exception e) {
      log.warn("Сообщения {} в чате {} нет", messageId, chatId, e);
    }
  }

  public Integer getLastMessageToDelete(long chatId) {
    List<Integer> messagesToDelete = mapForMessagesToDelete.get(chatId);
    if (messagesToDelete == null || messagesToDelete.isEmpty()) {
      return null;
    }
    return messagesToDelete.get(messagesToDelete.size() - 1);
  }

  public List<Integer> getAllChatMessagesToDelete(long chatId) {
    List<Integer> messagesToDelete = mapForMessagesToDelete.getOrDefault(chatId, new ArrayList<>());
    return Collections.unmodifiableList(messagesToDelete);
  }

  public List<Integer> getUndeletableMessages(long chatId) {
    List<Integer> undeletableMessages = mapForUndeletableMessages.getOrDefault(chatId, new ArrayList<>());
    return Collections.unmodifiableList(undeletableMessages);
  }

  public void setLastInlineKeyboardForChat(long chatId, int messageId) {
    mapForLastInlineKeyboardEnabledToUpdate.put(chatId, messageId);
  }

  public Integer getLastInlineKeyboardId(long chatId) {
    return mapForLastInlineKeyboardEnabledToUpdate.getOrDefault(chatId, null);
  }

  public void removeLastInlineKeyboardId(long chatId) {
    mapForLastInlineKeyboardEnabledToUpdate.remove(chatId);
  }
}

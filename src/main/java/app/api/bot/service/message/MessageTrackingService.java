package app.api.bot.service.message;

import app.api.entity.*;
import app.api.repository.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageTrackingService {
  private final MessageRepository messageRepository;

  @Transactional
  public void addMessage(long chatId, int messageId, boolean deleteStatus, boolean isInlineKeyboard, boolean isReplyKeyboard) {
    Message message = Message
        .builder()
        .chatId(chatId)
        .messageId(messageId)
        .isHaveReplyKeyboard(isReplyKeyboard)
        .isDeleted(deleteStatus)
        .isHaveInlineKeyboard(isInlineKeyboard)
        .build();

    messageRepository.save(message);
  }

  @Transactional
  public void removeMessageToDelete(long chatId, int messageId) {
    messageRepository.deleteMessage(chatId, messageId);
  }
  @Transactional
  public Integer getLastMessageToDelete(long chatId) {
    List<Message> messages = messageRepository.findByChatIdAndIsDeletedTrue(chatId);
    if (messages == null || messages.isEmpty()) {
      return null;
    }
    return messages.get(messages.size() - 1).getMessageId();
  }

  @Transactional
  public List<Integer> getAllChatMessagesToDelete(long chatId) {
    List<Message> messagesToDelete = messageRepository.findByChatIdAndIsDeletedTrue(chatId);
    List<Integer> messageIds = new ArrayList<>(messagesToDelete.stream().map(Message::getMessageId).toList());
    messageIds.sort(Integer::compareTo);
    return messageIds;
  }

  @Transactional
  public List<Integer> getUndeletableMessages(long chatId) {
    List<Message> undeletableMessages = messageRepository.findByChatIdAndIsDeletedFalse(chatId);
    List<Integer> messageIds = new ArrayList<>(undeletableMessages.stream().map(Message::getMessageId).toList());
    messageIds.sort(Integer::compareTo);
    return messageIds;
  }

  public Integer getLastInlineKeyboardId(Long chatId) {
    Integer messageId = messageRepository.findByChatIdAndHaveInlineKeyboardTrue(chatId);
    return messageId;
  }

  @Transactional
  public Integer getLastReplyKeyboardForChat(long chatId) {
    return  messageRepository.findByChatIdAndisHaveReplyKeyboardTrue(chatId);
  }

}

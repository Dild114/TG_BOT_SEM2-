package app.api.bot.service;

import app.api.bot.TelegramBot;
import app.api.bot.service.message.MessageTrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
public class MessageSenderService {
  private final TelegramBot telegramBot;
  private final MessageTrackingService messageTrackingService;

  public MessageSenderService(@Lazy TelegramBot telegramBot, MessageTrackingService messageTrackingService) {
    this.telegramBot = telegramBot;
    this.messageTrackingService = messageTrackingService;
  }

  public Integer sendMessage(long chatId, SendMessage sendMessage) {
    try {
      Message message = telegramBot.execute(sendMessage);
      messageTrackingService.addMessage(chatId, message.getMessageId(), true, false, false);
      return message.getMessageId();
    } catch (TelegramApiException e) {
      log.error("При попытке отправить сообщение {} в чат {} упала ошибка", sendMessage, chatId, e);
    }
    return null;
  }

  public void sendUndeletableMessage(long chatId, SendMessage sendMessage) {
    try {
      Message message = telegramBot.execute(sendMessage);
      messageTrackingService.addMessage(chatId, message.getMessageId(), false, false, false);
    } catch (TelegramApiException e) {
      log.error("При попытке отправить сообщение {} в чат {} упала ошибка", sendMessage, chatId, e);
    }
  }

  public void sendTextMessage(long chatId, String messageText) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(messageText);

    sendMessage(chatId, sendMessage);
  }

  public void updateInlineKeyboard(EditMessageReplyMarkup editMessageReplyMarkup) {
    try {
      telegramBot.execute(editMessageReplyMarkup);
    } catch (TelegramApiException e) {
      log.error("При попытке обновить клавиатуру {} в чате {} упала ошибка",
        editMessageReplyMarkup.getMessageId(),
        editMessageReplyMarkup.getChatId(),
        e
      );
    }
  }

  public void sendMessageWithInlineKeyboard(long chatId, SendMessage sendMessage) {
    Integer lastInlineKeyboardId = messageTrackingService.getLastInlineKeyboardId(chatId);
    if (lastInlineKeyboardId != null) {
      Integer lastMessageId = messageTrackingService.getLastMessageToDelete(chatId);
      if (lastMessageId != null) {
        while (!lastMessageId.equals(lastInlineKeyboardId)) {
          deleteMessage(chatId, lastMessageId);
          lastMessageId = messageTrackingService.getLastMessageToDelete(chatId);
        }
        deleteMessage(chatId, lastInlineKeyboardId);
      }
    }
    Integer newInlineKeyboardId = sendMessage(chatId, sendMessage);
    messageTrackingService.addMessage(chatId, newInlineKeyboardId, true, true, false);
  }

  public void sendMessageWithReplyKeyboard(long chatId, SendMessage sendMessage) {
    Integer newReplyKeyboardId = sendMessage(chatId, sendMessage);
    messageTrackingService.addMessage(chatId, newReplyKeyboardId, true, false, true);
  }

  public void deleteAllMessagesAfterReplyKeyboard(long chatId) {
    Integer lastReplyKeyboardId = messageTrackingService.getLastReplyKeyboardForChat(chatId);
    if (lastReplyKeyboardId != null) {
      Integer lastMessageId = messageTrackingService.getLastMessageToDelete(chatId);
      if (lastMessageId != null) {
        while (!lastMessageId.equals(lastReplyKeyboardId)) {
          deleteMessage(chatId, lastMessageId);
          lastMessageId = messageTrackingService.getLastMessageToDelete(chatId);
        }
      }
    }
  }

  public void deleteMessage(long chatId, int messageId) {
    DeleteMessage deleteMessage = new DeleteMessage();
    deleteMessage.setChatId(chatId);
    deleteMessage.setMessageId(messageId);

    try {
      messageTrackingService.removeMessageToDelete(chatId, messageId);
      telegramBot.execute(deleteMessage);
    } catch (TelegramApiException e) {
      log.error("При попытке удалить сообщение {} в чате {} упала ошибка", messageId, chatId, e);
    }
  }

  public void deleteLastBotMessage(long chatId) {
    Integer deleteMessageId = messageTrackingService.getLastMessageToDelete(chatId);
    if (deleteMessageId != null) {
      deleteMessage(chatId, deleteMessageId);
    } else {
      log.error("При попытке удалить последнее сообщение бота в чате {} упала ошибка", chatId);
    }
  }

  public void deleteAllChatMessagesExceptUndeletable(long chatId) {
    List<Integer> messagesToDelete = messageTrackingService.getAllChatMessagesToDelete(chatId);
    for (int i = messagesToDelete.size() - 1; i >= 0; i--) {
      deleteMessage(chatId, messagesToDelete.get(i));
    }
  }

  public void deleteUndeletableMessages(long chatId) {
    List<Integer> undeletableMessages = messageTrackingService.getUndeletableMessages(chatId);
    for (int i = undeletableMessages.size() - 1; i >= 0; i--) {
      deleteMessage(chatId, undeletableMessages.get(i));
    }
  }

  public void updateMessage(EditMessageText editMessageText) {
    try {
      telegramBot.execute(editMessageText);
    } catch (TelegramApiException e) {
      log.error("При попытке обновить сообщение с клавиатурой {} в чате {} упала ошибка",
        editMessageText.getMessageId(),
        editMessageText.getChatId(),
        e
      );
    }
  }
}

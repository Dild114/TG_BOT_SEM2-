package app.api.bot.service.message.settings;

import app.api.bot.service.keyboard.replyKeyboard.factory.ReplyKeyboardFactory;
import app.api.bot.service.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class SettingsMessageService {
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final MessageSenderService messageSenderService;

  public void sendSettingsMessage(long chatId) {
    messageSenderService.deleteAllChatMessagesExceptUndeletable(chatId);

    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("⚙\uFE0F Настройки");
    sendMessage.setChatId(chatId);
    sendMessage.setReplyMarkup(replyKeyboardFactory.getSettingsMenu().getKeyboardMarkup());

    messageSenderService.sendMessageWithReplyKeyboard(chatId, sendMessage);
  }
}

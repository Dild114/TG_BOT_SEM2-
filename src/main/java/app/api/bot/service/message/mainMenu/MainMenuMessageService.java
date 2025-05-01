package app.api.bot.service.message.mainMenu;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.keyboard.replyKeyboard.factory.ReplyKeyboardFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class MainMenuMessageService {
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final MessageSenderService messageSenderService;

  public void sendMainMenuMessage(long chatId) {
    messageSenderService.deleteAllChatMessagesExceptUndeletable(chatId);

    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("↩ Главная");
    sendMessage.setChatId(chatId);
    sendMessage.setReplyMarkup(replyKeyboardFactory.getMainMenu().getKeyboardMarkup());

    messageSenderService.sendMessageWithReplyKeyboard(chatId, sendMessage);
  }
}

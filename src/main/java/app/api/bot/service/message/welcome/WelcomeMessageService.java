package app.api.bot.service.message.welcome;

import app.api.bot.service.keyboard.replyKeyboard.factory.ReplyKeyboardFactory;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.message.MessageTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class WelcomeMessageService {
  private final MessageTemplateService messageTemplateService;
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final MessageSenderService messageSenderService;

  public void sendWelcomeMessage(long chatId, String userName) {
    messageSenderService.deleteAllChatMessagesExceptUndeletable(chatId);
    messageSenderService.deleteUndeletableMessages(chatId);
    messageSenderService.deleteLastInlineKeyboardId(chatId);
    messageSenderService.deleteLastReplyKeyboardId(chatId);

    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(messageTemplateService.getWelcomeText(userName));
    sendMessage.setReplyMarkup(replyKeyboardFactory.getMainMenu().getKeyboardMarkup());

    messageSenderService.sendUndeletableMessage(chatId, sendMessage);
  }
}

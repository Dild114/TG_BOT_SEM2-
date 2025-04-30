package app.api.bot.service.message.news;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.keyboard.replyKeyboard.factory.ReplyKeyboardFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class NewsMessageService {
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final MessageSenderService messageSenderService;

  //TODO: подумать, какие конкретно сейчас новые статьи выдать определяет сервис бота, или более низкоуровневый?

  public void sendNewsMenuMessage(long chatId) {
    messageSenderService.deleteLastBotMessage(chatId);

    SendMessage sendFirstMessage = new SendMessage();
    sendFirstMessage.setText("Здесь вы можете получить новые статьи с ваших сайтов по вашим категориям \uD83E\uDDD0");
    sendFirstMessage.setChatId(chatId);

    SendMessage sendSecondMessage = new SendMessage();
    sendSecondMessage.setText("Выберите действие:");
    sendSecondMessage.setChatId(chatId);
    sendSecondMessage.setReplyMarkup(replyKeyboardFactory.getNewsMenuReplyKeyboard().getKeyboardMarkup());

    messageSenderService.sendMessageWithInlineKeyboard(chatId, sendFirstMessage);
    messageSenderService.sendMessage(chatId, sendSecondMessage);
  }
}

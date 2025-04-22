package app.api.bot.service.message.source;

import app.api.bot.service.keyboard.inlineKeyboard.SourceMenuInlineKeyboard;
import app.api.bot.service.keyboard.replyKeyboard.factory.ReplyKeyboardFactory;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.message.MessageTrackingService;
import app.api.bot.stubs.PairForSource;
import app.api.bot.stubs.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class SourceMessageService {
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final SourceMenuInlineKeyboard sourceMenuInlineKeyboard;
  private final MessageSenderService messageSenderService;
  private final MessageTrackingService messageTrackingService;

  //TODO: заменить на UserService.getPageSize(chatId) или что-то в этом роде
  private final User user;

  //TODO: Заменить LinkedHashMap на что-то нормальное
  public void sendSourceMenuMassage(long chatId, LinkedHashMap<String, PairForSource> sources) {
    messageSenderService.deleteLastBotMessage(chatId);

    SendMessage sendFirstMessage = new SendMessage();
    sendFirstMessage.setText("Ваши источники:");
    sendFirstMessage.setChatId(chatId);

    //TODO: не забыть тут заменить User на UserService
    sendFirstMessage.setReplyMarkup(sourceMenuInlineKeyboard.createSourcesList(sources, 1, "state", user.getPageSize()));

    SendMessage sendSecondMessage = new SendMessage();
    sendSecondMessage.setText("Выберите действие:");
    sendSecondMessage.setChatId(chatId);
    sendSecondMessage.setReplyMarkup(replyKeyboardFactory.getSourceMenu().getKeyboardMarkup());

    messageSenderService.sendMessageWithInlineKeyboard(chatId, sendFirstMessage);
    messageSenderService.sendMessage(chatId, sendSecondMessage);
  }

  //TODO: Заменить LinkedHashMap на что-то нормальное
  public void updateSourceMenuMessage(long chatId, int pageNum, LinkedHashMap<String, PairForSource> sources, String viewMode) {
    int messageId = messageTrackingService.getLastInlineKeyboardId(chatId);
    EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
    editMessageReplyMarkup.setChatId(chatId);
    editMessageReplyMarkup.setMessageId(messageId);

    //TODO: не забыть тут заменить User на UserService
    editMessageReplyMarkup.setReplyMarkup(sourceMenuInlineKeyboard.createSourcesList(sources, pageNum, viewMode, user.getPageSize()));

    messageSenderService.updateInlineKeyboard(editMessageReplyMarkup);
  }
}

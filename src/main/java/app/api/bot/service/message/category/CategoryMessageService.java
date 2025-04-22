package app.api.bot.service.message.category;

import app.api.bot.service.keyboard.inlineKeyboard.CategoryMenuInlineKeyboard;
import app.api.bot.service.keyboard.replyKeyboard.factory.ReplyKeyboardFactory;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.message.MessageTrackingService;
import app.api.bot.stubs.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class CategoryMessageService {
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final CategoryMenuInlineKeyboard categoryMenuInlineKeyboard;
  private final MessageSenderService messageSenderService;
  private final MessageTrackingService messageTrackingService;

  //TODO: заменить на UserService.getPageSize(chatId) или что-то в этом роде
  private final User user;

  //TODO: Заменить LinkedHashMap на что-то нормальное
  public void sendCategoryMenuMessage(long chatId, LinkedHashMap<String, Boolean> categories) {
    messageSenderService.deleteLastBotMessage(chatId);

    SendMessage sendFirstMessage = new SendMessage();
    sendFirstMessage.setText("Ваши категории:");
    sendFirstMessage.setChatId(chatId);

    //TODO: не забыть тут заменить User на UserService
    sendFirstMessage.setReplyMarkup(categoryMenuInlineKeyboard.createCategoriesList(categories, 1, user.getPageSize()));

    SendMessage sendSecondMessage = new SendMessage();
    sendSecondMessage.setText("Выберите действие:");
    sendSecondMessage.setChatId(chatId);
    sendSecondMessage.setReplyMarkup(replyKeyboardFactory.getCategoryMenu().getKeyboardMarkup());

    messageSenderService.sendMessageWithInlineKeyboard(chatId, sendFirstMessage);
    messageSenderService.sendMessage(chatId, sendSecondMessage);
  }

  //TODO: Заменить LinkedHashMap на что-то нормальное
  public void updateCategoryMenuMessage(long chatId, int pageNum, LinkedHashMap<String, Boolean> categories) {
    int messageId = messageTrackingService.getLastInlineKeyboardId(chatId);
    EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
    editMessageReplyMarkup.setChatId(chatId);
    editMessageReplyMarkup.setMessageId(messageId);

    //TODO: не забыть тут заменить User на UserService
    editMessageReplyMarkup.setReplyMarkup(categoryMenuInlineKeyboard.createCategoriesList(categories, pageNum, user.getPageSize()));

    messageSenderService.updateInlineKeyboard(editMessageReplyMarkup);
  }
}

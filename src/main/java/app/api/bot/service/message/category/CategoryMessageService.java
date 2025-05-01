package app.api.bot.service.message.category;

import app.api.bot.service.keyboard.inlineKeyboard.CategoryMenuInlineKeyboard;
import app.api.bot.service.keyboard.replyKeyboard.factory.ReplyKeyboardFactory;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.message.MessageTrackingService;
import app.api.bot.stubs.category.CategoryStub;
import app.api.bot.stubs.user.UserServiceStub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryMessageService {
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final CategoryMenuInlineKeyboard categoryMenuInlineKeyboard;
  private final MessageSenderService messageSenderService;
  private final MessageTrackingService messageTrackingService;

  private final UserServiceStub userServiceStub;

  public void sendCategoryMenuMessage(long chatId, List<CategoryStub> categories) {
    messageSenderService.deleteLastBotMessage(chatId);

    SendMessage sendFirstMessage = new SendMessage();
    sendFirstMessage.setText("Ваши категории:");
    sendFirstMessage.setChatId(chatId);

    int countPages = userServiceStub.getUserCountStringsInOnePage(chatId);
    sendFirstMessage.setReplyMarkup(categoryMenuInlineKeyboard.createCategoriesList(categories, 1, countPages));

    SendMessage sendSecondMessage = new SendMessage();
    sendSecondMessage.setText("Выберите действие:");
    sendSecondMessage.setChatId(chatId);
    sendSecondMessage.setReplyMarkup(replyKeyboardFactory.getCategoryMenu().getKeyboardMarkup());

    messageSenderService.sendMessageWithInlineKeyboard(chatId, sendFirstMessage);
    messageSenderService.sendMessageWithReplyKeyboard(chatId, sendSecondMessage);
  }

  //TODO: Заменить LinkedHashMap на что-то нормальное
  public void updateCategoryMenuMessage(long chatId, int pageNum, List<CategoryStub> categories) {
    int messageId = messageTrackingService.getLastInlineKeyboardId(chatId);
    EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
    editMessageReplyMarkup.setChatId(chatId);
    editMessageReplyMarkup.setMessageId(messageId);

    //TODO: не забыть тут заменить User на UserService
    int countPages = userServiceStub.getUserCountStringsInOnePage(chatId);
    editMessageReplyMarkup.setReplyMarkup(categoryMenuInlineKeyboard.createCategoriesList(categories, pageNum, countPages));

    messageSenderService.updateInlineKeyboard(editMessageReplyMarkup);
  }
}

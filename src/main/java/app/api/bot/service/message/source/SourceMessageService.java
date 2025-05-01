package app.api.bot.service.message.source;

import app.api.bot.service.keyboard.inlineKeyboard.SourceMenuInlineKeyboard;
import app.api.bot.service.keyboard.replyKeyboard.factory.ReplyKeyboardFactory;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.message.MessageTrackingService;
import app.api.bot.stubs.source.SourceStub;
import app.api.bot.stubs.user.UserServiceStub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceMessageService {
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final SourceMenuInlineKeyboard sourceMenuInlineKeyboard;
  private final MessageSenderService messageSenderService;
  private final MessageTrackingService messageTrackingService;

  private final UserServiceStub userServiceStub;


  //TODO: Заменить LinkedHashMap на что-то нормальное
  public void sendSourceMenuMassage(long chatId, List<SourceStub> sources) {
    messageSenderService.deleteLastBotMessage(chatId);

    SendMessage sendFirstMessage = new SendMessage();
    sendFirstMessage.setText("Ваши источники:");
    sendFirstMessage.setChatId(chatId);

    //TODO: не забыть тут заменить User на UserService
    int countPages = userServiceStub.getUserCountStringsInOnePage(chatId);
    sendFirstMessage.setReplyMarkup(sourceMenuInlineKeyboard.createSourcesList(sources, 1, "state", countPages));

    SendMessage sendSecondMessage = new SendMessage();
    sendSecondMessage.setText("Выберите действие:");
    sendSecondMessage.setChatId(chatId);
    sendSecondMessage.setReplyMarkup(replyKeyboardFactory.getSourceMenu().getKeyboardMarkup());

    messageSenderService.sendMessageWithInlineKeyboard(chatId, sendFirstMessage);
    messageSenderService.sendMessageWithReplyKeyboard(chatId, sendSecondMessage);
  }

  //TODO: Заменить LinkedHashMap на что-то нормальное
  public void updateSourceMenuMessage(long chatId, int pageNum, List<SourceStub> sources, String viewMode) {
    int messageId = messageTrackingService.getLastInlineKeyboardId(chatId);
    EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
    editMessageReplyMarkup.setChatId(chatId);
    editMessageReplyMarkup.setMessageId(messageId);

    //TODO: не забыть тут заменить User на UserService
    int countPages = userServiceStub.getUserCountStringsInOnePage(chatId);
    editMessageReplyMarkup.setReplyMarkup(sourceMenuInlineKeyboard.createSourcesList(sources, pageNum, viewMode, countPages));

    messageSenderService.updateInlineKeyboard(editMessageReplyMarkup);
  }
}

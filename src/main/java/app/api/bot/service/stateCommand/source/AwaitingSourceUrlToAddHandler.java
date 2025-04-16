package app.api.bot.service.stateCommand.source;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.StateCommandHandler;
import app.api.bot.service.UserStateService;
import app.api.bot.stubs.SourceServiceStub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Component
@Order(14)
@RequiredArgsConstructor
@Slf4j
public class AwaitingSourceUrlToAddHandler implements StateCommandHandler {
  private final UserStateService userStateService;
  private final BotMessageService botMessageService;
  //TODO: заменить на сервис нормальный
  private final SourceServiceStub sourceServiceStub;

  private boolean isValidUrl(String url) {
    try {
      new java.net.URL(url).toURI();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean canHandle(long chatId) {
    String state = userStateService.getState(chatId);
    return state != null && state.equals("awaiting_source_url_to_add");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (!isValidUrl(text)) {
      botMessageService.sendMessage(chatId, "❗\uFE0F Некорректная ссылка: " + text);
    } else {
      //TODO: заменить на нормальный сервис
      sourceServiceStub.addSource(chatId, userStateService.getTempSourceName(chatId), text);
      try {
        botMessageService.updateSourceMenuMessage(chatId, 1, sourceServiceStub.getSources(), userStateService.getTempViewMode(chatId)); //TODO заменить на получение категорий и их использование
        botMessageService.sendMessage(chatId, "☑\uFE0F Источник \"" + userStateService.getTempSourceName(chatId) + "\" успешно добавлен");
      } catch (TelegramApiRequestException e) {
        botMessageService.sendMessage(chatId, "❗\uFE0F Источник \"" + userStateService.getTempSourceName(chatId) + "\" добавлен ранее");
        log.error(e.getMessage());
      }
    }
    userStateService.clearState(chatId);
  }
}

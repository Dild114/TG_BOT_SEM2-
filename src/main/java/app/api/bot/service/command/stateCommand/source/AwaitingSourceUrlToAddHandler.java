package app.api.bot.service.command.stateCommand.source;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.ChatStateService;
import app.api.bot.service.message.source.SourceMessageService;
import app.api.bot.stubs.exceptions.InvalidValueException;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(14)
@RequiredArgsConstructor
@Slf4j
public class AwaitingSourceUrlToAddHandler implements StateCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;
  private final SourceMessageService sourceMessageService;
  //TODO: заменить на сервис нормальный
  private final WebsiteService sourceService;

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
    String state = chatStateService.getState(chatId);
    return state != null && state.equals("awaiting_source_url_to_add");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (!isValidUrl(text)) {
      messageSenderService.sendTextMessage(chatId, "❗\uFE0F Некорректная ссылка: " + text);
      messageSenderService.sendTextMessage(chatId, "\uD83D\uDD04 Попробуйте ввести ссылку ещё раз:");
    } else {
      //TODO: заменить на нормальный сервис
      try {
        sourceService.addSourceToUser(chatId, chatStateService.getTempSourceName(chatId), text);
        sourceMessageService.updateSourceMenuMessage(chatId, 1, sourceService.getUserSources(chatId), chatStateService.getTempViewMode(chatId)); //TODO заменить на получение категорий и их использование
        messageSenderService.sendTextMessage(chatId, "☑\uFE0F Источник \"" + chatStateService.getTempSourceName(chatId) + "\" успешно добавлен");
        chatStateService.clearState(chatId);
      } catch (InvalidValueException e) {
        messageSenderService.sendTextMessage(chatId, "⚠\uFE0F Источник с названием " + "\"" + chatStateService.getTempSourceName(chatId) + "\" или ссылкой " + "\"" + message.getText() + "\"" + " уже был добавлен ранее");
        messageSenderService.sendTextMessage(chatId, "\uD83D\uDD04 Попробуйте ввести название а затем ссылку ещё раз:");
        chatStateService.setState(chatId, "awaiting_source_name_to_add");
      }
    }
  }
}

package app.api.bot.service.command.stateCommand.source;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.ChatStateService;
import app.api.bot.service.message.source.SourceMessageService;
import app.api.bot.stubs.source.SourceServiceStub;
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
    String state = chatStateService.getState(chatId);
    return state != null && state.equals("awaiting_source_url_to_add");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (!isValidUrl(text)) {
      messageSenderService.sendTextMessage(chatId, "❗\uFE0F Некорректная ссылка: " + text);
    } else {
      //TODO: заменить на нормальный сервис
      sourceServiceStub.addSourceToUser(chatId, chatStateService.getTempSourceName(chatId), text);
      sourceMessageService.updateSourceMenuMessage(chatId, 1, sourceServiceStub.getUserSources(chatId), chatStateService.getTempViewMode(chatId)); //TODO заменить на получение категорий и их использование
      messageSenderService.sendTextMessage(chatId, "☑\uFE0F Источник \"" + chatStateService.getTempSourceName(chatId) + "\" успешно добавлен");
    }
    chatStateService.clearState(chatId);
  }
}

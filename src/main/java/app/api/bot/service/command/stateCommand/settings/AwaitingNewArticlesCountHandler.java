package app.api.bot.service.command.stateCommand.settings;

import app.api.bot.service.ChatStateService;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(31)
@RequiredArgsConstructor
public class AwaitingNewArticlesCountHandler implements StateCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;

  private final UserService userService;

  @Override
  public boolean canHandle(long chatId) {
    String state = chatStateService.getState(chatId);
    return state != null && state.equals("awaiting_new_articles_count");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (text.length() < 10 && text.matches("\\d+") && Integer.parseInt(text) > 0) {
      int newSize = Integer.parseInt(text);
      //TODO: заменить на нормальный метод из нормального сервиса
      userService.changeUserCountArticlesInOneRequest(chatId, newSize);
      messageSenderService.sendTextMessage(chatId, "☑\uFE0F Новое кол-во статей получаемых одним запросом \"" + message.getText() + "\" успешно установлено");
    } else {
      messageSenderService.sendTextMessage(chatId, "❗\uFE0F Значение \"" + message.getText() + "\" неприемлемо. Кол-во получаемых статей должно быть натуральным числом меньшим 10^9");
    }
    chatStateService.clearState(chatId);
  }
}

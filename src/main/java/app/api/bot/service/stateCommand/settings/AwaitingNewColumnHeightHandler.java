package app.api.bot.service.stateCommand.settings;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.StateCommandHandler;
import app.api.bot.service.InlineKeyboardFactory;
import app.api.bot.service.UserStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(16)
@RequiredArgsConstructor
@Slf4j
public class AwaitingNewColumnHeightHandler implements StateCommandHandler {
  private final UserStateService userStateService;
  private final BotMessageService botMessageService;
  //TODO: заменить на userService.setColumnHeight или что-то в этом роде
  private final InlineKeyboardFactory inlineKeyboardFactory;

  @Override
  public boolean canHandle(long chatId) {
    String state = userStateService.getState(chatId);
    return state != null && state.equals("awaiting_new_column_height");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (text.length() < 10 && text.matches("\\d+") && Integer.parseInt(text) > 0) {
      int newSize = Integer.parseInt(text);
      //TODO: заменить на нормальный метод из нормального сервиса
      inlineKeyboardFactory.setPageSize(newSize);
      botMessageService.sendMessage(chatId, "☑\uFE0F Новая высота столбца \"" + message.getText() + "\" успешно установлена");
    } else {
      botMessageService.sendMessage(chatId, "❗\uFE0F Значение \"" + message.getText() + "\" неприемлемо. Высота столбца должна быть натуральным числом меньшим 10^9");
    }
    userStateService.clearState(chatId);
  }
}

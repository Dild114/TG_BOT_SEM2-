package app.api.bot.service.command.stateCommand.settings;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.ChatStateService;
import app.api.bot.stubs.user.UserServiceStub;
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
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;

  private final UserServiceStub userServiceStub;

  @Override
  public boolean canHandle(long chatId) {
    String state = chatStateService.getState(chatId);
    return state != null && state.equals("awaiting_new_column_height");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (text.length() < 10 && text.matches("\\d+") && Integer.parseInt(text) > 0) {
      int newSize = Integer.parseInt(text);
      //TODO: заменить на нормальный метод из нормального сервиса
      userServiceStub.changeUserCountStringsInOnePage(chatId, newSize);
      messageSenderService.sendTextMessage(chatId, "☑\uFE0F Новая высота столбца \"" + message.getText() + "\" успешно установлена");
    } else {
      messageSenderService.sendTextMessage(chatId, "❗\uFE0F Значение \"" + message.getText() + "\" неприемлемо. Высота столбца должна быть натуральным числом меньшим 10^9");
    }
    chatStateService.clearState(chatId);
  }
}

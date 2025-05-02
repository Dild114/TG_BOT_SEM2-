package app.api.bot.service.command.stateCommand.source;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.message.source.SourceMessageService;
import app.api.bot.stubs.exceptions.InvalidValueException;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(15)
@RequiredArgsConstructor
@Slf4j
public class AwaitingSourceNameToDeleteHandler implements StateCommandHandler {
  private final UserService userService;
  private final MessageSenderService messageSenderService;
  private final SourceMessageService sourceMessageService;
  private final WebsiteService sourceService; //TODO: заменить на реальный сервис

  @Override
  public boolean canHandle(long chatId) {
    String state = userService.getState(chatId);
    return state != null && state.equals("awaiting_source_name_to_delete");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();

    if (text == null || text.isBlank() || text.length() > 20 || text.trim().split("\\s+").length > 4 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ\\s]+$")) {
        messageSenderService.sendTextMessage(chatId, "❗\uFE0F Недопустимое название источника + \"" + message.getText()
          + "\"\nНазвание источника должно состоять из 1-4 слов и не более 20 символов (включая пробелы)");
    } else {
      try {
        //TODO: заменить на реальный сервис
        sourceService.deleteSourceFromUser(chatId, text);
        sourceMessageService.updateSourceMenuMessage(chatId, 1, sourceService.getUserSources(chatId), userService.getTempViewMode(chatId)); //TODO: заменить на реальный сервис и получать источники, а уже потом использовать
        messageSenderService.sendTextMessage(chatId, "☑\uFE0F Источник \"" + message.getText() + "\" успешно удалён");
        userService.clearState(chatId);
      } catch (InvalidValueException e) {
        messageSenderService.sendTextMessage(chatId, "⚠\uFE0F Источник с названием " + "\"" + message.getText() + " не найден");
        messageSenderService.sendTextMessage(chatId, "\uD83D\uDD04 Попробуйте ввести название ещё раз:");
      }
    }
  }
}

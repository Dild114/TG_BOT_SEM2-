package app.api.bot.service.command.stateCommand.source;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.ChatStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(13)
@RequiredArgsConstructor
@Slf4j
public class AwaitingSourceNameToAddHandler implements StateCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;

  @Override
  public boolean canHandle(long chatId) {
    String state = chatStateService.getState(chatId);
    return state != null && state.equals("awaiting_source_name_to_add");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (text == null || text.isBlank() || text.length() > 20 || text.trim().split("\\s+").length > 4 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ\\s]+$")) {
      messageSenderService.sendTextMessage(chatId, "❗\uFE0F Недопустимое название источника + \""
        + message.getText() + "\"\nНазвание источника должно состоять из 1-4 слов и не более 20 символов (включая пробелы)");
      messageSenderService.sendTextMessage(chatId, "\uD83D\uDD04 Попробуйте ввести название источника ещё раз:");
    } else {
      messageSenderService.sendTextMessage(chatId, "\uD83D\uDCDD\uD83D\uDD17 Введите ссылку на источник:");
      chatStateService.setTempSourceName(chatId, text);
      chatStateService.setState(chatId, "awaiting_source_url_to_add");
    }
  }
}

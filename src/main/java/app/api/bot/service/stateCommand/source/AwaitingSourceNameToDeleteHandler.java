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
@Order(15)
@RequiredArgsConstructor
@Slf4j
public class AwaitingSourceNameToDeleteHandler implements StateCommandHandler {
  private final UserStateService userStateService;
  private final BotMessageService botMessageService;
  private final SourceServiceStub sourceServiceStub; //TODO: заменить на реальный сервис

  @Override
  public boolean canHandle(long chatId) {
    String state = userStateService.getState(chatId);
    return state != null && state.equals("awaiting_source_name_to_delete");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();

    if (text.isBlank() || text.length() > 15 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ]+$")) {
        botMessageService.sendMessage(chatId, "❗\uFE0F Недопустимое название источника + \"" + message.getText()
          + "\"\nНазвание источника должно быть единым словом длиной не более 15 символов");
    } else {
      //TODO: заменить на реальный сервис
      sourceServiceStub.deleteSource(text);
      try {
        botMessageService.updateSourceMenuMessage(chatId, 1, sourceServiceStub.getSources(), userStateService.getTempViewMode(chatId)); //TODO: заменить на реальный сервис и получать источники, а уже потом использовать
        botMessageService.sendMessage(chatId, "☑\uFE0F Источник \"" + message.getText() + "\" успешно удалён");
      } catch (TelegramApiRequestException e) {
        botMessageService.sendMessage(chatId, "❗\uFE0F Источник \"" + message.getText() + "\" не найден");
      }
    }
    userStateService.clearState(chatId);
  }
}

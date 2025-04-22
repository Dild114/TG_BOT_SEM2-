package app.api.bot.service.command.stateCommand.source;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.ChatStateService;
import app.api.bot.service.message.source.SourceMessageService;
import app.api.bot.stubs.SourceServiceStub;
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
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;
  private final SourceMessageService sourceMessageService;
  private final SourceServiceStub sourceServiceStub; //TODO: заменить на реальный сервис

  @Override
  public boolean canHandle(long chatId) {
    String state = chatStateService.getState(chatId);
    return state != null && state.equals("awaiting_source_name_to_delete");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();

    if (text.isBlank() || text.length() > 15 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ]+$")) {
        messageSenderService.sendTextMessage(chatId, "❗\uFE0F Недопустимое название источника + \"" + message.getText()
          + "\"\nНазвание источника должно быть единым словом длиной не более 15 символов");
    } else {
      //TODO: заменить на реальный сервис
      sourceServiceStub.deleteSource(text);
      sourceMessageService.updateSourceMenuMessage(chatId, 1, sourceServiceStub.getSources(), chatStateService.getTempViewMode(chatId)); //TODO: заменить на реальный сервис и получать источники, а уже потом использовать
      messageSenderService.sendTextMessage(chatId, "☑\uFE0F Источник \"" + message.getText() + "\" успешно удалён");
    }
    chatStateService.clearState(chatId);
  }
}

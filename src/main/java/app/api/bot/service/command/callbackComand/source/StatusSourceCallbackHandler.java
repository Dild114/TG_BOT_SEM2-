package app.api.bot.service.command.callbackComand.source;

import app.api.bot.service.ChatStateService;
import app.api.bot.service.command.handlerInterfaces.CallbackCommandHandler;
import app.api.bot.service.message.source.SourceMessageService;
import app.api.bot.stubs.SourceServiceStub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Order(6)
@Slf4j
@RequiredArgsConstructor
public class StatusSourceCallbackHandler implements CallbackCommandHandler {
  private final SourceMessageService sourceMessageService;
  private final ChatStateService chatStateService;
  //TODO:заменить на нормальный сервис
  private final SourceServiceStub sourceServiceStub;

  @Override
  public boolean canHandle(String callbackData) {
    return callbackData.startsWith("change_source_status_");
  }

  @Override
  public void handle(CallbackQuery callbackQuery) {
    String callbackData = callbackQuery.getData();
    long chatId = callbackQuery.getMessage().getChatId();

    String[] parts = callbackData.split("_");
    String sourceName = parts[3];
    int pageNum = Integer.parseInt(parts[4]);
    //TODO: заменить на норм сервис, который принимает ещё и chatId
    sourceServiceStub.changeStatus(sourceName);
    //TODO: аналогично меняем заглушку на норм сервис
    sourceMessageService.updateSourceMenuMessage(chatId, pageNum, sourceServiceStub.getSources(), chatStateService.getTempViewMode(chatId));
  }
}

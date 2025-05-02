package app.api.bot.service.command.callbackComand.source;

import app.api.bot.service.command.handlerInterfaces.CallbackCommandHandler;
import app.api.bot.service.message.source.SourceMessageService;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Order(7)
@Slf4j
@RequiredArgsConstructor
public class ViewSourceCallbackHandler implements CallbackCommandHandler {
  private final SourceMessageService sourceMessageService;
  private final UserService userService;
  //TODO:заменить на нормальный сервис
  private final WebsiteService sourceService;

  @Override
  public boolean canHandle(String callbackData) {
    return callbackData.startsWith("change_source_view_");
  }

  @Override
  public void handle(CallbackQuery callbackQuery) {
    String callbackData = callbackQuery.getData();
    long chatId = callbackQuery.getMessage().getChatId();

    String[] parts = callbackData.split("_");
    int pageNum = Integer.parseInt(parts[4]);
    String newView = parts[3];
    userService.setTempViewMode(chatId, newView);
    //TODO: меняем sourceServiceStub на норм сервис, принимающий chatId
    sourceMessageService.updateSourceMenuMessage(chatId, pageNum, sourceService.getUserSources(chatId), newView);
  }
}

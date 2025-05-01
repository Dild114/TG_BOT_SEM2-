package app.api.bot.service.command.basicCommand.source;

import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.message.source.SourceMessageService;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(7)
@RequiredArgsConstructor
public class GetSourcesCommandHandler implements BasicCommandHandler {
  private final WebsiteService sourceService;
  private final SourceMessageService sourceMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "\uD83C\uDF10 Источники".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    // TODO: List<SourceDto> userSources = sourceService.getSources(chatId)
    sourceMessageService.sendSourceMenuMassage(chatId, sourceService.getUserSources(chatId)); // TODO: заменить на userSources
  }
}

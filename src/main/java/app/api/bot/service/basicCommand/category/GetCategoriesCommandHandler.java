package app.api.bot.service.basicCommand.category;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.BasicCommandHandler;
import app.api.bot.stubs.CategoryServiceStub;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(5)
@RequiredArgsConstructor
public class GetCategoriesCommandHandler implements BasicCommandHandler {
  private final CategoryServiceStub categoryServiceStub; // TODO: заменить на CategoryService
  private final BotMessageService botMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "\uD83D\uDCD1 Категории".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    // TODO: List<CategoryDto> userCategories = categoryService.getCategories(chatId)
    botMessageService.sendCategoryMenuMessage(chatId, categoryServiceStub.getCategories()); // TODO: заменить на userCategories
  }
}

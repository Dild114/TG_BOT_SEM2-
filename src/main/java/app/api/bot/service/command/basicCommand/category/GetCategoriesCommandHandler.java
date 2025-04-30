package app.api.bot.service.command.basicCommand.category;

import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.message.category.CategoryMessageService;
import app.api.bot.stubs.category.CategoryServiceStub;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(5)
@RequiredArgsConstructor
public class GetCategoriesCommandHandler implements BasicCommandHandler {
  private final CategoryServiceStub categoryServiceStub; // TODO: заменить на CategoryService
  private final CategoryMessageService categoryMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "\uD83D\uDCD1 Категории".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    // TODO: List<CategoryDto> userCategories = categoryService.getCategories(chatId)

    categoryMessageService.sendCategoryMenuMessage(chatId, categoryServiceStub.getUserCategories(chatId)); // TODO: прописать изменение заглушки
  }
}

package app.api.bot.service.command.callbackComand.category;

import app.api.bot.service.command.handlerInterfaces.CallbackCommandHandler;
import app.api.bot.service.message.category.CategoryMessageService;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Order(4)
@Slf4j
@RequiredArgsConstructor
public class StatusCategoryCallbackHandler implements CallbackCommandHandler {
  private final CategoryMessageService categoryMessageService;
  //TODO: меняем на нормальный сервис
  private final CategoryService categoryService;

  @Override
  public boolean canHandle(String callbackData) {
    return callbackData.startsWith("change_category_status_");
  }

  @Override
  public void handle(CallbackQuery callbackQuery) {
    String callbackData = callbackQuery.getData();
    long chatId = callbackQuery.getMessage().getChatId();

    String[] parts = callbackData.split("_");
    int categoryId = Integer.parseInt(parts[3]);
    int pageNum = Integer.parseInt(parts[4]);
    //TODO: тут меняем на сервис и он так же принимает chatId
    categoryService.changeUserCategoryStatus(chatId, (long) categoryId);
    //TODO: аналогично меняем заглушку на норм сервис
    categoryMessageService.updateCategoryMenuMessage(chatId, pageNum, categoryService.getUserCategories(chatId));
  }
}

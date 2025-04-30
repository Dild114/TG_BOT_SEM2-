package app.api.bot.service.command.callbackComand.category;

import app.api.bot.service.command.handlerInterfaces.CallbackCommandHandler;
import app.api.bot.service.message.category.CategoryMessageService;
import app.api.bot.stubs.category.CategoryServiceStub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Order(3)
@Slf4j
@RequiredArgsConstructor
public class PageCategoryCallbackHandler implements CallbackCommandHandler {
  private final CategoryMessageService categoryMessageService;
  //TODO: меняем на нормальный сервис
  private final CategoryServiceStub categoryServiceStub;

  @Override
  public boolean canHandle(String callbackData) {
    return callbackData.startsWith("page_category_");
  }

  @Override
  public void handle(CallbackQuery callbackQuery) {
    String callbackData = callbackQuery.getData();
    long chatId = callbackQuery.getMessage().getChatId();

    String[] parts = callbackData.split("_");
    int pageNum = Integer.parseInt(parts[2]);
    // TODO: заменить заглушки на нормальный сервис,
    // TODO: при этом в сервисе прописать, чтобы он возвращал именно названия категорий
    //TODO: меняем categoryServiceStub
    categoryMessageService.updateCategoryMenuMessage(chatId, pageNum, categoryServiceStub.getUserCategories(chatId));
  }
}

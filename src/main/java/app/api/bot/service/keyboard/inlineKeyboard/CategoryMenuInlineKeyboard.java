package app.api.bot.service.keyboard.inlineKeyboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMenuInlineKeyboard {
  private final InlineKeyboardHelper inlineKeyboardHelper;

  //TODO: заменить categories чтобы нормально соотносилось, (проверить, подходит ли LinkedHashMap)
  public InlineKeyboardMarkup createCategoriesList(
    LinkedHashMap<String, Boolean> categories,
    int pageNum,
    int pageSize
  ) {

    InlineKeyboardMarkup categoryListKeyboard = new InlineKeyboardMarkup();

    int countPages = (int) Math.ceil(categories.size() / (double) pageSize);

    int start = (pageNum - 1) * pageSize;
    int end = Math.min((pageNum) * pageSize, categories.size());

    //TODO: аналогично проверить, всё ли норм из-зи LinkedHashMap
    List<String> currentPageCategories = new ArrayList<>(categories.keySet()).subList(start, end);

    List<List<InlineKeyboardButton>> categoriesKeyboard = new ArrayList<>();

    for (String categoryName : currentPageCategories) {
      String status = categories.get(categoryName) ? "✅" : "❌";
      InlineKeyboardButton button = inlineKeyboardHelper.createCallbackButton(
        status + " " + categoryName,
        "change_category_status_" + categoryName + "_" + pageNum);
      categoriesKeyboard.add(List.of(button));
    }

    List<InlineKeyboardButton> navigation = inlineKeyboardHelper.buildNavigationButtons("page_category", pageNum, countPages);
    categoriesKeyboard.add(navigation);

    categoryListKeyboard.setKeyboard(categoriesKeyboard);
    return categoryListKeyboard;
  }
}

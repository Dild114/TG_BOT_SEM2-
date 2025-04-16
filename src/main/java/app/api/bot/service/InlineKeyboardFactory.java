package app.api.bot.service;

import app.api.bot.stubs.PairForSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Component
//TODO: Попилить монолит
public class InlineKeyboardFactory {
  private final InlineKeyboardMarkup closeButton = createCloseMessageKeyboard();
  @Setter
  private int pageSize = 5;

  private InlineKeyboardMarkup createCloseMessageKeyboard() {
    InlineKeyboardMarkup closeMessageKeyboard = new InlineKeyboardMarkup();

    InlineKeyboardButton closeMessageButton = new InlineKeyboardButton();
    closeMessageButton.setText("❎ Закрыть");
    closeMessageButton.setCallbackData("close_message");

    List<InlineKeyboardButton> row1 = List.of(closeMessageButton);

     closeMessageKeyboard.setKeyboard(List.of(row1));

     return closeMessageKeyboard;
  }

  // TODO: тоже переделать потом с сервисами
  public InlineKeyboardMarkup createCategoriesList(long chatId, LinkedHashMap<String, Boolean> categories, int pageNum) {

    InlineKeyboardMarkup categoryListKeyboard = new InlineKeyboardMarkup();

    int countPages = (int) Math.ceil(categories.size() / (double) pageSize);

    int start = (pageNum - 1) * pageSize;
    int end = Math.min((pageNum) * pageSize, categories.size());

    List<String> currentPageCategories = new ArrayList<>(categories.keySet()).subList(start, end);

    List<List<InlineKeyboardButton>> categoriesKeyboard = new ArrayList<>();

    for (String categoryName : currentPageCategories) {
      InlineKeyboardButton category = new InlineKeyboardButton();
      String status = categories.get(categoryName) ? "✅" : "❌";
      category.setText(status + " " + categoryName);
      category.setCallbackData("change_category_status_" + categoryName + "_" + pageNum);
      List<InlineKeyboardButton> newRow = new ArrayList<>();
      newRow.add(category);
      categoriesKeyboard.add(newRow);
    }

    InlineKeyboardButton prevButton = new InlineKeyboardButton();
    prevButton.setText("⏪");
    prevButton.setCallbackData("page_category_" + (pageNum - 1));

    InlineKeyboardButton stopButton = new InlineKeyboardButton();
    stopButton.setText("⏹\uFE0F");
    stopButton.setCallbackData("ничего не делает");

    InlineKeyboardButton nextButton = new InlineKeyboardButton();
    nextButton.setText("⏩");
    nextButton.setCallbackData("page_category_" + (pageNum + 1));

    InlineKeyboardButton currentPage = new InlineKeyboardButton();
    currentPage.setText("Стр " + pageNum + "/" + Math.max(countPages, 1));
    currentPage.setCallbackData("ничего не делает");


    List<InlineKeyboardButton> navigateButtons = new ArrayList<>();
    if (pageNum > 1) {
      navigateButtons.add(prevButton);
    } else {
      navigateButtons.add(stopButton);
    }
    navigateButtons.add(currentPage);
    if (pageNum < countPages) {
      navigateButtons.add(nextButton);
    } else {
      navigateButtons.add(stopButton);
    }

    categoriesKeyboard.add(navigateButtons);

    categoryListKeyboard.setKeyboard(categoriesKeyboard);

    return categoryListKeyboard;
  }

  public InlineKeyboardMarkup createSourcesList(long chatId, LinkedHashMap<String, PairForSource> sources, int pageNum, String viewMode) {
    //TODO: pageSize должен браться из UserDto = userService.getUser(chatId)
    // UserDto.get(pageSize)

    InlineKeyboardMarkup sourceListKeyboard = new InlineKeyboardMarkup();

    int countPages = (int) Math.ceil(sources.size() / (double) pageSize);

    int start = (pageNum - 1) * pageSize;
    int end = Math.min((pageNum) * pageSize, sources.size());

    List<String> currentPageSources = new ArrayList<>(sources.keySet()).subList(start, end);

    List<List<InlineKeyboardButton>> sourcesKeyboard = new ArrayList<>();

    InlineKeyboardButton sourceViewButton = new InlineKeyboardButton();
    if ("state".equals(viewMode)) {
      sourceViewButton.setText("Вид: Состояние");
      sourceViewButton.setCallbackData("change_source_view_link_" + pageNum);
    } else {
      sourceViewButton.setText("Вид: Ссылочный");
      sourceViewButton.setCallbackData("change_source_view_state_" + pageNum);
    }
    sourcesKeyboard.add(List.of(sourceViewButton));

    for (String sourceName : currentPageSources) {
      InlineKeyboardButton sourceNameButton = new InlineKeyboardButton();
      if ("state".equals(viewMode)) {
        String status = sources.get(sourceName).active() ? "✅" : "❌";
        sourceNameButton.setText(status + " " + sourceName);
        sourceNameButton.setCallbackData("change_source_status_" + sourceName + "_" + pageNum);
      } else {
        sourceNameButton.setText("\uD83D\uDD17 " + sourceName);
        sourceNameButton.setUrl(sources.get(sourceName).url());
      }

      List<InlineKeyboardButton> newRow = new ArrayList<>();
      newRow.add(sourceNameButton);
      sourcesKeyboard.add(newRow);
    }

    InlineKeyboardButton prevButton = new InlineKeyboardButton();
    prevButton.setText("⏪");
    prevButton.setCallbackData("page_source_" + (pageNum - 1));

    InlineKeyboardButton stopButton = new InlineKeyboardButton();
    stopButton.setText("⏹\uFE0F");
    stopButton.setCallbackData("ничего не делает");

    InlineKeyboardButton nextButton = new InlineKeyboardButton();
    nextButton.setText("⏩");
    nextButton.setCallbackData("page_source_" + (pageNum + 1));

    InlineKeyboardButton currentPage = new InlineKeyboardButton();
    currentPage.setText("Стр " + pageNum + "/" + Math.max(countPages, 1));
    currentPage.setCallbackData("ничего не делает");

    List<InlineKeyboardButton> navigateButtons = new ArrayList<>();
    if (pageNum > 1) {
      navigateButtons.add(prevButton);
    } else {
      navigateButtons.add(stopButton);
    }
    navigateButtons.add(currentPage);
    if (pageNum < countPages) {
      navigateButtons.add(nextButton);
    } else {
      navigateButtons.add(stopButton);
    }

    sourcesKeyboard.add(navigateButtons);

    sourceListKeyboard.setKeyboard(sourcesKeyboard);

    return sourceListKeyboard;
  }
}

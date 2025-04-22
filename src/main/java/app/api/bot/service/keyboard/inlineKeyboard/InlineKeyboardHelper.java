package app.api.bot.service.keyboard.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class InlineKeyboardHelper {
  public InlineKeyboardButton createCallbackButton(String text, String callbackData) {
    InlineKeyboardButton button = new InlineKeyboardButton();
    button.setText(text);
    button.setCallbackData(callbackData);
    return button;
  }

  public InlineKeyboardButton createUrlButton(String text, String url) {
    InlineKeyboardButton button = new InlineKeyboardButton();
    button.setText(text);
    button.setUrl(url);
    return button;
  }

  public List<InlineKeyboardButton> buildNavigationButtons(String prefix, int pageNum, int totalPages) {
    InlineKeyboardButton prev = pageNum > 1
      ? createCallbackButton("⏪", prefix + "_" + (pageNum - 1))
      : createCallbackButton("⏹️", "ничего не делает");

    InlineKeyboardButton current = createCallbackButton("Стр " + pageNum + "/" + Math.max(totalPages, 1), "ничего не делает");

    InlineKeyboardButton next = pageNum < totalPages
      ? createCallbackButton("⏩", prefix + "_" + (pageNum + 1))
      : createCallbackButton("⏹️", "ничего не делает");

    return List.of(prev, current, next);
  }
}

package app.api.bot.service;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Getter
@Component
public class ReplyKeyboardFactory {
  private final ReplyKeyboardMarkup mainMenu = createMainMenu();
  private final ReplyKeyboardMarkup categoryMenu = createCategoryMenu();
  private final ReplyKeyboardMarkup sourceMenu = createSourceMenu();
  private final ReplyKeyboardMarkup settingsMenu = createSettingsMenu();

  private ReplyKeyboardMarkup createMainMenu() {
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    keyboardMarkup.setResizeKeyboard(true);
    keyboardMarkup.setOneTimeKeyboard(false);
    keyboardMarkup.setSelective(true);

    KeyboardRow row1 = new KeyboardRow();
    row1.add("\uD83D\uDCF0 Новости");

    KeyboardRow row2 = new KeyboardRow();
    row2.add("\uD83C\uDF10 Источники");

    KeyboardRow row3 = new KeyboardRow();
    row3.add("\uD83D\uDCD1 Категории");

    KeyboardRow row4 = new KeyboardRow();
    row4.add("ℹ️ Инструкция");

    keyboardMarkup.setKeyboard(List.of(row1, row2, row3, row4));

    return keyboardMarkup;
  }

  private ReplyKeyboardMarkup createCategoryMenu() {
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    keyboardMarkup.setResizeKeyboard(true);
    keyboardMarkup.setOneTimeKeyboard(false);
    keyboardMarkup.setSelective(true);

    KeyboardRow row1 = new KeyboardRow();
    row1.add("➕ Добавить категорию");

    KeyboardRow row2 = new KeyboardRow();
    row2.add("\uD83D\uDDD1 Удалить категорию");

    KeyboardRow row3 = new KeyboardRow();
    row3.add("↩ Главная");

    keyboardMarkup.setKeyboard(List.of(row1, row2, row3));

    return keyboardMarkup;
  }

  private ReplyKeyboardMarkup createSourceMenu() {
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    keyboardMarkup.setResizeKeyboard(true);
    keyboardMarkup.setOneTimeKeyboard(false);
    keyboardMarkup.setSelective(true);

    KeyboardRow row1 = new KeyboardRow();
    row1.add("➕ Добавить");

    KeyboardRow row2 = new KeyboardRow();
    row2.add("\uD83D\uDDD1 Удалить");

    KeyboardRow row3 = new KeyboardRow();
    row3.add("↩ Главная");

    keyboardMarkup.setKeyboard(List.of(row1, row2, row3));

    return keyboardMarkup;
  }

  // TODO: добавить кнопок и методов, подумать, мб заменить на inline
  private ReplyKeyboardMarkup createSettingsMenu() {
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    keyboardMarkup.setResizeKeyboard(true);
    keyboardMarkup.setOneTimeKeyboard(false);
    keyboardMarkup.setSelective(true);

    KeyboardRow row1 = new KeyboardRow();
    row1.add("Изменить кол-во элементов, отображаемых на одной странице");

    KeyboardRow row2 = new KeyboardRow();
    row2.add("↩ Главная");

    keyboardMarkup.setKeyboard(List.of(row1, row2));

    return keyboardMarkup;
  }
}

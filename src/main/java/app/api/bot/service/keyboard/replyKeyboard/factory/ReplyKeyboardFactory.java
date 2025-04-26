package app.api.bot.service.keyboard.replyKeyboard.factory;

import app.api.bot.service.keyboard.replyKeyboard.keyboard.CategoryMenuReplyKeyboard;
import app.api.bot.service.keyboard.replyKeyboard.keyboard.MainMenuReplyKeyboard;
import app.api.bot.service.keyboard.replyKeyboard.keyboard.NewsMenuReplyKeyboard;
import app.api.bot.service.keyboard.replyKeyboard.keyboard.SettingsMenuReplyKeyboard;
import app.api.bot.service.keyboard.replyKeyboard.keyboard.SourceMenuReplyKeyboard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class ReplyKeyboardFactory {
  private final MainMenuReplyKeyboard mainMenu;
  private final CategoryMenuReplyKeyboard categoryMenu;
  private final SourceMenuReplyKeyboard sourceMenu;
  private final SettingsMenuReplyKeyboard settingsMenu;
  private final NewsMenuReplyKeyboard newsMenuReplyKeyboard;
}

package app.api.bot.service;

import app.api.bot.TelegramBot;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class BotMessageService {
  private final TelegramBot telegramBot;
  private final ReplyKeyboardFactory replyKeyboardFactory;
  private final InlineKeyboardFactory inlineKeyboardFactory;
  private final ConcurrentHashMap<Long, List<Integer>> mapForMessagesToDelete;
  private final ConcurrentHashMap<Long, Integer> mapForLastInlineKeyboardEnabledToUpdate;

  public BotMessageService(@Lazy TelegramBot telegramBot, ReplyKeyboardFactory replyKeyboardFactory, InlineKeyboardFactory inlineKeyboardFactory) {
    this.telegramBot = telegramBot;
    this.replyKeyboardFactory = replyKeyboardFactory;
    this.inlineKeyboardFactory = inlineKeyboardFactory;
    this.mapForMessagesToDelete = new ConcurrentHashMap<>();
    this.mapForLastInlineKeyboardEnabledToUpdate = new ConcurrentHashMap<>();
  }

  @PostConstruct
  public void setBotCommands() {
    List<BotCommand> commands = List.of(
    new BotCommand("/start", "начать"),
    new BotCommand("/help", "помощь"),
    new BotCommand("/settings", "настройки")
    );

    SetMyCommands setMyCommands = new SetMyCommands(commands, new BotCommandScopeDefault(), null);

    try {
      telegramBot.execute(setMyCommands);
    } catch (TelegramApiException e) {
      log.error("При попытке добавить меню команд упала ошибка", e);
    }
  }

  public void sendWelcomeMessage(long chatId, String userName) {
    String filePath = "src\\main\\java\\app\\api\\bot\\message\\welcome.txt";
    String welcomeText = FileUtils.readFileAsString(filePath).replace("{name}", userName);

    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(welcomeText);

    for (int messageId : mapForMessagesToDelete.getOrDefault(chatId, new ArrayList<>())) {
      deleteMessage(chatId, messageId);
    }
    mapForMessagesToDelete.remove(chatId);
    mapForLastInlineKeyboardEnabledToUpdate.remove(chatId);

    sendMessage.setReplyMarkup(replyKeyboardFactory.getMainMenu());

    try {
      telegramBot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error("При попытке отправить приветственное сообщение в чат {} упала ошибка", chatId, e);
    }
  }

  public void sendHelpMessage(long chatId) {
    String filePath = "src\\main\\java\\app\\api\\bot\\message\\help.txt";
    String helpText = FileUtils.readFileAsString(filePath);
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(helpText);
    sendMessage.setReplyMarkup(inlineKeyboardFactory.getCloseButton());

    try {
      telegramBot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error("При попытке отправить сообщение с инструкцией в чат {} упала ошибка", chatId, e);
    }
  }

  public void sendSettingsMessage(long chatId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("⚙\uFE0F Настройки");
    sendMessage.setChatId(chatId);

    for (int messageId : mapForMessagesToDelete.getOrDefault(chatId, new ArrayList<>())) {
      deleteMessage(chatId, messageId);
    }
    mapForMessagesToDelete.remove(chatId);
    mapForLastInlineKeyboardEnabledToUpdate.remove(chatId);

    sendMessage.setReplyMarkup(replyKeyboardFactory.getSettingsMenu());

    try {
      Message message = telegramBot.execute(sendMessage);
      mapForMessagesToDelete.computeIfAbsent(chatId, k -> new ArrayList<>()).add(message.getMessageId());
    } catch (TelegramApiException e) {
      log.error("При попытке отправить сообщение с настройками в чат {} упала ошибка", chatId, e);
    }
  }

  public void sendMainMenuMessage(long chatId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("↩ Главная");
    sendMessage.setChatId(chatId);

    for (int messageId : mapForMessagesToDelete.getOrDefault(chatId, new ArrayList<>())) {
      deleteMessage(chatId, messageId);
    }
    mapForMessagesToDelete.remove(chatId);
    mapForLastInlineKeyboardEnabledToUpdate.remove(chatId);

    sendMessage.setReplyMarkup(replyKeyboardFactory.getMainMenu());

    try {
      Message message = telegramBot.execute(sendMessage);
      mapForMessagesToDelete.computeIfAbsent(chatId, k -> new ArrayList<>()).add(message.getMessageId());
    } catch (TelegramApiException e) {
      log.error("При попытке перейти к главному меню в чате {} упала ошибка", chatId, e);
    }
  }

  public void sendCategoryMenuMessage(long chatId, LinkedHashMap<String, Boolean> categories) {
    SendMessage sendFirstMessage = new SendMessage();
    sendFirstMessage.setText("Ваши категории:");
    sendFirstMessage.setChatId(chatId);
    sendFirstMessage.setReplyMarkup(inlineKeyboardFactory.createCategoriesList(categories, 1));

    SendMessage sendSecondMessage = new SendMessage();
    sendSecondMessage.setText("Выберите действие:");
    sendSecondMessage.setChatId(chatId);
    sendSecondMessage.setReplyMarkup(replyKeyboardFactory.getCategoryMenu());

    try {
      Message firstMessage = telegramBot.execute(sendFirstMessage);
      Message secondMessage = telegramBot.execute(sendSecondMessage);
      mapForMessagesToDelete.computeIfAbsent(chatId, k -> new ArrayList<>()).addAll(List.of(firstMessage.getMessageId(), secondMessage.getMessageId()));
      mapForLastInlineKeyboardEnabledToUpdate.put(chatId, firstMessage.getMessageId());
    } catch (TelegramApiException e) {
      log.error("При попытке перейти к меню категорий в чате {} упала ошибка", chatId, e);
    }
  }

  public void updateCategoryMenuMessage(long chatId, int pageNum, LinkedHashMap<String, Boolean> categories) throws TelegramApiRequestException {
    int messageId = mapForLastInlineKeyboardEnabledToUpdate.get(chatId);
    EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
    editMessageReplyMarkup.setChatId(chatId);
    editMessageReplyMarkup.setMessageId(messageId);
    editMessageReplyMarkup.setReplyMarkup(inlineKeyboardFactory.createCategoriesList(categories, pageNum));
    try {
      telegramBot.execute(editMessageReplyMarkup);
    } catch (TelegramApiException e) {
      throw new TelegramApiRequestException(e.getMessage());
    }
  }

  public void sendSourceMenuMassage(long chatId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("Сюда добавить список ресурсов");
    sendMessage.setChatId(chatId);
    sendMessage.setReplyMarkup(replyKeyboardFactory.getSourceMenu());

    try {
      Message message = telegramBot.execute(sendMessage);
      mapForMessagesToDelete.computeIfAbsent(chatId, k -> new ArrayList<>()).add(message.getMessageId());
    } catch (TelegramApiException e) {
      log.error("При попытке перейти к меню ресурсов в чате {} упала ошибка", chatId, e);
    }
  }

  public void sendMessage(long chatId, String messageText) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(messageText);

    try {
      Message message = telegramBot.execute(sendMessage);
      mapForMessagesToDelete.computeIfAbsent(chatId, k -> new ArrayList<>()).add(message.getMessageId());
    } catch (TelegramApiException e) {
      log.error("При попытке отправить сообщение в чат {} упала ошибка", chatId, e);
    }
  }

  public void deleteMessage(long chatId, int messageId) {
    DeleteMessage deleteMessage = new DeleteMessage();
    deleteMessage.setChatId(chatId);
    deleteMessage.setMessageId(messageId);

    try {
      telegramBot.execute(deleteMessage);
    } catch (TelegramApiException e) {
      log.error("При попытке удалить сообщение {} в чате {} упала ошибка", messageId, chatId, e);
    }
  }

  public void deleteLastBotMessage(long chatId) {
    DeleteMessage deleteMessage = new DeleteMessage();
    deleteMessage.setChatId(chatId);
    List<Integer> messagesToDelete = mapForMessagesToDelete.get(chatId);
    deleteMessage.setMessageId(messagesToDelete.get(messagesToDelete.size() - 1));

    try {
      telegramBot.execute(deleteMessage);
      messagesToDelete.remove(messagesToDelete.size() - 1);
    } catch (TelegramApiException e) {
      log.error("При попытке удалить последнее сообщение бота в чате {} упала ошибка", chatId, e);
    }
  }
}

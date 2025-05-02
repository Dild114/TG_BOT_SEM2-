package app.api.service;

import app.api.bot.service.*;
import app.api.entity.User;
import app.api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.*;

@Slf4j
@Service

public class UserService {
  private final UserRepository userRepository;
  private final ArticleService articleService;
  private final MessageSenderService messageSenderService;

  public UserService(UserRepository userRepository, @Lazy ArticleService articleService, MessageSenderService messageSenderService) {
    this.userRepository = userRepository;
    this.articleService = articleService;
    this.messageSenderService = messageSenderService;
  }

  @Transactional
  public void createUser(Long chatId) {
    User user = User.builder()
        .chatId(chatId)
        .briefContentOfArticlesStatus(false)
        .countStringsInOnePage(5)
        .countArticlesInOneRequest(3)
        .tempViewMode("state")
        .build();

    userRepository.save(user);
    articleService.addRandomArticlesToUser(user.getChatId());
  }

  @Transactional
  public List<Long> getUsersId() {
    List<User> users = userRepository.findAll();
    return users.stream().map(User::getChatId).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public User getUserById(Long chatId) {
    return userRepository.findById(chatId).orElse(null);
  }

  @Transactional(readOnly = true)
  public int getUserCountStringsInOnePage(long chatId) {
    User user = getUserById(chatId);
    return user.getCountStringsInOnePage();
  }

  @Transactional(readOnly = true)
  public long getUserCountArticlesInOneRequest(long chatId) {
    User user = getUserById(chatId);
    return user.getCountArticlesInOneRequest();
  }

  @Transactional(readOnly = true)
  public boolean getUserBriefStatus(long chatId) {
    User user = getUserById(chatId);
    return user.isBriefContentOfArticlesStatus();
  }

  @Transactional
  public void changeUserCountStringsInOnePage(long chatId, int newCountPages) {
    User user = getUserById(chatId);
    user.setCountStringsInOnePage(newCountPages);
    userRepository.save(user);
  }

  @Transactional
  public void changeUserCountArticlesInOneRequest(long chatId, int newCountArticles) {
    User user = getUserById(chatId);
    user.setCountArticlesInOneRequest(newCountArticles);
    userRepository.save(user);
  }

  @Transactional
  public void changeUserMakeBriefStatus(long chatId, boolean newBriefStatus) {
    User user = getUserById(chatId);
    user.setBriefContentOfArticlesStatus(newBriefStatus);
    userRepository.save(user);
  }

  @Transactional
  public void deleteUser(Long chatId) {
    if (!userRepository.existsById(chatId)) {
      log.warn("User not found: {}, for delete", chatId);
      return;
    }
    messageSenderService.deleteAllChatMessagesExceptUndeletable(chatId);
    messageSenderService.deleteUndeletableMessages(chatId);
    userRepository.deleteById(chatId);
  }

  @Transactional
  public void setState(Long chatId, String state) {
    User user = getUserById(chatId);
    if (user != null) {
      user.setState(state);
      userRepository.save(user);
    }
  }

  @Transactional(readOnly = true)
  public String getState(Long chatId) {
    User user = getUserById(chatId);
    return user != null ? user.getState() : null;
  }

  @Transactional
  public void setTempSourceName(Long chatId, String name) {
    User user = getUserById(chatId);
    if (user != null) {
      user.setTempSourceName(name);
      userRepository.save(user);
    }
  }

  @Transactional(readOnly = true)
  public String getTempSourceName(Long chatId) {
    User user = getUserById(chatId);
    return user != null ? user.getTempSourceName() : null;
  }

  @Transactional
  public void setTempViewMode(Long chatId, String mode) {
    User user = getUserById(chatId);
    if (user != null) {
      user.setTempViewMode(mode);
      userRepository.save(user);
    }
  }

  @Transactional(readOnly = true)
  public String getTempViewMode(Long chatId) {
    User user = getUserById(chatId);
    return user != null ? user.getTempViewMode() : null;
  }

  @Transactional
  public void clearState(Long chatId) {
    User user = getUserById(chatId);
    if (user != null) {
      user.setState(null);
      user.setTempSourceName(null);
      user.setTempViewMode(null);
      userRepository.save(user);
    }
  }
}
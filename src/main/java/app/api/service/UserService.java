package app.api.service;

import app.api.entity.User;
import app.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public void createUser(Long chatId) {
    User user = User.builder()
        .chatId(chatId)
        .briefContentOfArticlesStatus(false)
        .countStringsInOnePage(5)
        .countArticlesInOneRequest(3)
        .build();
    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public User getUserById(Long chatId) {
    return userRepository.findById(chatId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with chatId: " + chatId));
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
    userRepository.deleteById(chatId);
  }
}
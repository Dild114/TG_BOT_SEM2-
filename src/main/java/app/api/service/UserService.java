package app.api.service;

import app.api.dto.UserDto;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.entity.Website;
import app.api.entity.WebsiteId;
import app.api.mapper.UserMapper;
import app.api.repository.UserRepository;
import app.api.repository.WebsiteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final WebsiteRepository websiteRepository;
  private final UserMapper userMapper;

  public UserDto createUser(UserDto userDto) {
    User newUser = userRepository.save(userMapper.toEntity(userDto));
    log.info("User created: {}", newUser);
    return userMapper.toDto(newUser);
  }

  public void deleteUser(Long userId) {
    UserId id = new UserId(userId);
    if (!userRepository.existsById(id)) {
      log.info("User with id {} does not exist", userId);
      throw new EntityNotFoundException("User does not exist: " + userId);
    }
    userRepository.deleteById(id);
  }

  public UserDto addWebsiteToUser(Long userId, Long websiteId) {
    User user = userRepository.findById(new UserId(userId))
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    Website website = websiteRepository.findById(new WebsiteId(websiteId))
            .orElseThrow(() -> new EntityNotFoundException("Site not found: " + websiteId));

    // Добавление сайта, если его еще нет
    if (user.getWebsites().contains(website)) {
      log.info("User with ID {} already has website with ID {}", userId, websiteId);
      return userMapper.toDto(user); // Можно вернуть пользователя с сообщением
    }

    user.getWebsites().add(website);
    user = userRepository.save(user);
    return userMapper.toDto(user);
  }

  public UserDto removeWebsiteFromUser(Long userId, Long websiteId) {
    User user = userRepository.findById(new UserId(userId))
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

    boolean removed = user.getWebsites().removeIf(a -> a.getWebsiteId().getId().equals(websiteId));
    if (!removed) {
      log.warn("User with ID {} does not have website with ID {}", userId, websiteId);
      throw new EntityNotFoundException("The user's website was not found: " + websiteId);
    }
    user = userRepository.save(user);
    log.info("Website with ID {} removed from user with ID {}", websiteId, userId);
    return userMapper.toDto(user);
  }
}

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
    return userMapper.toDto(newUser);
  }

  public void deleteUser(Long userId) {
    UserId id = new UserId(userId);
    if (!userRepository.existsById(id)) {
      throw new EntityNotFoundException("User does not exist: " + userId);
    }
    userRepository.deleteById(id);
  }

  public UserDto addWebsiteToUser(Long userId, Long websiteId) {
    User user = userRepository.findById(new UserId(userId))
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    Website website = websiteRepository.findById(new WebsiteId(websiteId))
            .orElseThrow(() -> new EntityNotFoundException("Site not found: " + websiteId));

    if (user.getWebsites().contains(website)) {
      return userMapper.toDto(user);
    }

    user.getWebsites().add(website);
    user = userRepository.save(user);
    return userMapper.toDto(user);
  }

  public UserDto removeWebsiteFromUser(Long userId, Long websiteId) {
    User user = userRepository.findById(new UserId(userId))
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

    boolean removed = user.getWebsites().removeIf(a -> a.getId().getId().equals(websiteId));
    if (!removed) {
      throw new EntityNotFoundException("The user's website was not found: " + websiteId);
    }
    user = userRepository.save(user);
    return userMapper.toDto(user);
  }
}
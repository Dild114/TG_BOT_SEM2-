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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
  private final UserRepository userRepository;
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
}
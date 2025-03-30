package app.api.service;

import app.api.entity.User;
import app.api.entity.UserId;
import app.api.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersService {
  public UsersRepository usersRepository;

  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public UserId createUser(String userName, String password) {
    log.info("Creating user {}", userName);
    User user = new User(userName, password);
    try {
      UserId userId = usersRepository.createAccount(user);
      log.info("User {} created", userId);

      return userId;
    } catch (Exception e) {
      log.error("Error creating user", e);
    }
    return null;
  }

  public void deleteUser(UserId userId) {
    usersRepository.deleteAccount(userId);
    log.info("Deleting user {}", userId);
  }

  public void updateUserData(UserId userId, User user) {
    log.info("update user Data in UserService");
    usersRepository.updateAccount(userId, user);
  }

  public void updateUserName(UserId userId, String newName) {
    log.info("update Username with id: {}", userId.id());
    usersRepository.updateNameAccount(userId, newName);
  }
}
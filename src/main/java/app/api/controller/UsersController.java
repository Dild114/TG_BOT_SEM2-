package app.api.controller;

import app.api.controller.interfaceDrivenControllers.UserControllerInterface;
import app.api.controller.requests.UserRequest;
import app.api.entity.User;
import app.api.entity.UserData;
import app.api.entity.UserId;
import app.api.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UsersController implements UserControllerInterface {

  private final UsersService usersService;

  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public ResponseEntity<UserId> createUser(UserRequest userRequest) {
    log.info("create user");
    UserId userId = usersService.createUser(userRequest.name(), userRequest.password());
    return ResponseEntity.status(HttpStatus.CREATED).body(userId);
  }

  @Override
  public ResponseEntity<User> findUserById(Long userId) {
    log.info("Find user by id: {}", userId);
    User user = usersService.getUserById(new UserId(userId));
    if (user == null) {
      log.info("User not found");
      return ResponseEntity.notFound().build();
    }
    log.info("User found", user);
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @Override
  public ResponseEntity<?> deleteUser(Long id) {
    log.info("deleteUser");
    UserId userId = new UserId(id);
    usersService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<?> updateUserData(Long id, UserRequest userRequest) {
    log.info("Update user data with user id: {}", id);
    UserId userId = new UserId(id);
    UserData userData = UserData.builder().userName(userRequest.name()).password(userRequest.password()).build();
    usersService.updateUserData(userId, userData);
    return ResponseEntity.ok("update successful");
  }
}
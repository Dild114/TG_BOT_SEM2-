package app.api.controller;

import app.api.controller.interfaceDrivenControllers.UserControllerInterface;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
    log.info("createUser");
    UserId userId = usersService.createUser(userRequest.name(), userRequest.password());
    return ResponseEntity.status(HttpStatus.CREATED).body(userId);
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
    log.info("Update user data with id: {}", id);
    UserId userId = new UserId(id);
    User user = new User(userRequest.name(), userRequest.password());
    usersService.updateUserData(userId, user);
    return ResponseEntity.ok("update successful");
  }

  @Override
  public ResponseEntity<?> updateUserName(Long id, String name) {
    log.info("update username with id: {}", id);
    UserId userId = new UserId(id);
    usersService.updateUserName(userId, name);
    return ResponseEntity.noContent().build();
  }
}
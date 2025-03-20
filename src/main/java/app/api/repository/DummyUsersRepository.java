package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserData;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DummyUsersRepository implements UsersRepository {
  private final AtomicLong countId = new AtomicLong(0);
  private final List<User> repository = new ArrayList<>();

  @Override
  public UserId generateId() {
    return new UserId(countId.incrementAndGet());
  }

  @Override
  public void createAccount(User user) {
    repository.add(user);
  }

  @Override
  public void deleteAccount(UserId userId) {
    repository.removeIf(user -> user.getUserId().equals(userId));
  }

  @Override
  public void updateAccount(UserId userId, UserData userData) {
    User user = getUserById(userId);
    deleteAccount(userId);
    if (userData.getUserName() == null) {
      userData.setUserName(user.getUserName());
    }
    if (userData.getEmail() == null) {
      userData.setEmail(user.getEmail());
    }
    if (userData.getPassword() == null) {
      userData.setPassword(user.getPassword());
    }
    User newUser = new User(userId, userData.getUserName(), userData.getPassword());
    repository.add(newUser);
  }

  @Override
  public User getUserById(UserId userId) {
    for (User user : repository) {
      if (user.getUserId().equals(userId)) {
        return user;
      }
    }
    return null;
  }
}

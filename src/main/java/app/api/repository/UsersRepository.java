package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserData;
import app.api.entity.UserId;

public interface UsersRepository {
  UserId generateId();
  void createAccount(User user);
  void deleteAccount(UserId userId);
  void updateAccount(UserId userId, UserData userData);
  User getUserById(UserId userId);
}

package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserId;

public interface UsersRepository {
  UserId generateId();
  UserId createAccount(User user);
  void deleteAccount(UserId userId);
  void updateAccount(UserId userId, User user);
  void updateNameAccount(UserId userID, String newName);

}

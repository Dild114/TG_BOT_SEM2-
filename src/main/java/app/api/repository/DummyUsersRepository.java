package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

@Repository
public class DummyUsersRepository implements UsersRepository {
  int countId = 0;
  @Override
  public UserId generateId() {
    countId += 1;
    return new UserId(countId);
  }

  @Override
  public UserId createAccount(User user) {
    return generateId();
  }

  @Override
  public void deleteAccount(UserId userId) {
  }


  @Override
  public void updateAccount(UserId userId, User user) {
  }

  @Override
  public void updateNameAccount(UserId userId, String newName) {
  }
}

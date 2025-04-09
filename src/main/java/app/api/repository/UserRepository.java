package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserId;
import app.api.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, UserId> {

  @Query("select a.websites from User a where a.userId = :userId")
  Set<Website> getUserWebsites(Long userId);
}

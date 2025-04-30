package app.api.repository;

import app.api.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;


public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u LEFT JOIN FETCH u.websites WHERE u.chatId = :chatId")
  Optional<User> findByIdWithWebsites(@Param("chatId") Long chatId);
}

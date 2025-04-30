package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {}

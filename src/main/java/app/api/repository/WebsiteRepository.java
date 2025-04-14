package app.api.repository;

import app.api.entity.Website;
import app.api.entity.WebsiteId;
import app.api.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, WebsiteId> {
  List<Website> findAllWebsitesByUserId(Long user_id);
}
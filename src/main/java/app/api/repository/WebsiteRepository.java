package app.api.repository;

import app.api.entity.Website;
import app.api.entity.WebsiteId;
import app.api.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, WebsiteId> {

  //@Query("select w from Website w join WebsitesOfUser c on a.id = c.website_id where c.user_id = :userId")
  //List<Website> findAllWebsite(@Param("userId") UserId userId); //получение сайтов пользователя

//  List<Website> findByUsers_id(UserId userId);

  Optional<Website> findById(WebsiteId websiteId);

  void deleteById(WebsiteId websiteId);

  Website save(Website website);

}

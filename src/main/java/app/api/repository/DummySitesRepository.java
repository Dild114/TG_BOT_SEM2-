package app.api.repository;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DummySitesRepository implements SitesRepository {
  private final List<Site> repository = new ArrayList<>();
  private final AtomicLong countId = new AtomicLong(0);

  @Override
  public SiteId generateId() {
    return new SiteId(countId.incrementAndGet());
  }

  @Override
  public Site getSiteById(SiteId id) {
    for (Site site : repository) {
      if (site.id().equals(id)) {
        return site;
      }
    }
    return null;
  }

  @Override
  public List<Site> findAllSite(UserId userId) {
    return repository;
  }

  @Override
  public void deleteSiteById(SiteId id) {
    repository.removeIf(user -> user.id().equals(id));
  }

  @Override
  public void add(Site site) {
    repository.add(site);
  }
}
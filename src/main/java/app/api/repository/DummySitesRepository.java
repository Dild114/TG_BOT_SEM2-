package app.api.repository;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DummySitesRepository implements SitesRepository {

  @Override
  public SiteId getSiteId(Site site) {
    return null;
  }

  @Override
  public List<Site> findAllSite(UserId userId) {
    return null;
  }

  @Override
  public void deleteSiteById(SiteId id, UserId userId) {
  }

  @Override
  public void add(SiteId siteId, UserId userId) {

  }
}
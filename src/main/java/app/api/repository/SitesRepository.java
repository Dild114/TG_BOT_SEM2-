package app.api.repository;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;

import java.util.List;

public interface SitesRepository {
  List<Site> findAllSite(UserId userId);
  void deleteSiteById(SiteId id);
  void add(Site site);
  SiteId generateId();
  Site getSiteById(SiteId id);
}

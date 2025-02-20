package app.api.service;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.repository.SitesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SitesService {
  public SitesRepository sitesRepository;

  public SitesService(SitesRepository sitesRepository) {
    this.sitesRepository = sitesRepository;
  }

  public List<Site> getSites(UserId userId) {
    List<Site> sites = sitesRepository.findAllSite(userId);
    log.info("Get sites");
    return sites;
  }
  public void deleteSite(SiteId siteId, UserId userId) {
    sitesRepository.deleteSiteById(siteId, userId);
    log.info("Delete site {}", siteId);
  }
  public void addSite(SiteId siteId, UserId userId) {
    sitesRepository.add(siteId, userId);
    log.info("Add site {}", siteId);
  }
}

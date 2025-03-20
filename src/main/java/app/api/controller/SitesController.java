package app.api.controller;

import app.api.controller.requests.SiteRequest;
import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.service.SitesService;
import app.api.controller.interfaceDrivenControllers.SiteControllerInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SitesController implements SiteControllerInterface {

  private final SitesService sitesService;

  public SitesController(SitesService sitesService) {
    this.sitesService = sitesService;
  }

  @Override
  public ResponseEntity<Map<String, Integer>> getSites() {
    log.info("Getting all sites");
    Map<String, Integer> sites = new HashMap<>();
    int ind = 0;
    for (var site : Sites.values()) {
      sites.put(site.getUrl(), ind++);
    }
    return ResponseEntity.status(HttpStatus.OK).body(sites);
  }

  @Override
  public ResponseEntity<List<Site>> mySites(Long userId) {
    log.info("Getting sites for userId: {}", userId);
    List<Site> sites = sitesService.getSites(new UserId(userId));
    return ResponseEntity.status(HttpStatus.OK).body(sites);
  }

  @Override
  public ResponseEntity<SiteId> addSite(SiteRequest siteRequest) {
    log.info("Add site with name");
    SiteId siteId = sitesService.addSite(siteRequest.url);
    return ResponseEntity.status(HttpStatus.OK).body(siteId);
  }

  @Override
  public ResponseEntity<Void> deleteSite(Long siteId, Long userId) {
    log.info("Deleting site for userId: {}", userId);
    sitesService.deleteSite(new SiteId(siteId), new UserId(userId));
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

package app.api.controller;

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

@Slf4j
@RestController
public class SitesController implements SiteControllerInterface {

  private final SitesService sitesService;

  public SitesController(SitesService sitesService) {
    this.sitesService = sitesService;
  }

  @Override
  public ResponseEntity<?> getSites() {
    log.info("Getting all sites");
    HashMap<String, Integer> sites = new HashMap<>();
    int ind = 0;
    for (var site : Sites.values()) {
      sites.put(site.getUrl(), ind++);
    }
    return ResponseEntity.status(HttpStatus.OK).body(sites);
  }

  @Override
  public ResponseEntity<?> mySites(int userId) {
    log.info("Getting sites for userId: {}", userId);
    List<Site> sites = sitesService.getSites(new UserId(userId));
    return ResponseEntity.ok(sites);
  }

  @Override
  public ResponseEntity<SiteId> addSite(int siteId, @RequestBody UserId userId) {
    log.info("Adding site for userId: {}", userId);
    try {
      sitesService.addSite(new SiteId(siteId), userId);
      return ResponseEntity.status(HttpStatus.CREATED).body(new SiteId(siteId));
    } catch (Exception e) {
      log.error("Adding site failed: ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Integer> deleteSite(int siteId, UserId userId) {
    log.info("Deleting site for userId: {}", userId);
    try {
      sitesService.deleteSite(new SiteId(siteId), userId);
      return ResponseEntity.ok(siteId);
    } catch (Exception e) {
      log.error("Deleting site failed", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}

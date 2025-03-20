package app.api.service;

import app.api.entity.*;
import app.api.repository.DummySitesRepository;
import app.api.repository.DummyUsersRepository;
import app.api.repository.SitesRepository;
import app.api.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsersService {
  public UsersRepository usersRepository;
  public SitesRepository sitesRepository;

  public UsersService() {
    this.usersRepository = new DummyUsersRepository();
    this.sitesRepository = new DummySitesRepository();
  }

  public UserId createUser(String userName, String password) {
    log.info("Creating user {}", userName);
    try {
      UserId userId = usersRepository.generateId();
      User user = new User(userId, userName, password);
      usersRepository.createAccount(user);
      log.info("User {} created", userId);
      return userId;
    } catch (Exception e) {
      log.error("Error creating user", e);
      return null;
    }
  }

  public User getUserById(UserId userId) {
    return usersRepository.getUserById(userId);
  }

  public void deleteUser(UserId userId) {
    usersRepository.deleteAccount(userId);
    log.info("Deleting user {}", userId);
  }

  public void updateUserData(UserId userId, UserData userData) {
    log.info("update user Data in UserService");
    usersRepository.updateAccount(userId, userData);
  }

  public void addSite(UserId userId, SiteId siteId) {
    User user = usersRepository.getUserById(userId);
    List<Site> sites = user.getSites();
    for (Site site : sites) {
      if (site.id().equals(siteId)) return;
    }
    Site site = sitesRepository.getSiteById(siteId);
    sites.add(site);
    user.setSites(sites);
    usersRepository.deleteAccount(userId);
    usersRepository.createAccount(user);
  }

  public void removeSite(UserId userId, SiteId siteId) {
    User user = usersRepository.getUserById(userId);
    List<Site> sites = user.getSites();
    for (Site site : sites) {
      if (site.id().equals(siteId)) {
        sites.remove(site);
      }
    }
    user.setSites(sites);
    usersRepository.deleteAccount(userId);
    usersRepository.createAccount(user);
  }
}
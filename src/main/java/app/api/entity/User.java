package app.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  private Long chatId;

  @Column(name = "brief_content_of_articles_status")
  private boolean briefContentOfArticlesStatus;

  @Column(name = "message_storage_time_day")
  private long messageStorageTimeDay;

  @Column(name = "count_strings_in_one_page")
  private int countStringsInOnePage;

  @Column(name = "count_articles_in_one_request")
  private int countArticlesInOneRequest;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Category> categories = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Website> websites = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Article> articles = new ArrayList<>();
}
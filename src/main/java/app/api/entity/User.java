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
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 1)
  private Long chatId;

  @Column(name = "briefContentOfArticlesStatus")
  private boolean briefContentOfArticlesStatus;

  @Column(name = "message_storage_time_day")
  private long messageStorageTimeDay;

  @Column(name = "countStringsInOnePage")
  private int countStringsInOnePage;

  @Column(name = "countArticlesInOneRequest")
  private int countArticlesInOneRequest;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Category> categories = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Website> websites = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return chatId != null && chatId.equals(user.chatId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chatId);
  }
}

package app.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "websites")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Website {
  @EmbeddedId
  private WebsiteId id;

  private String url;

  @ManyToMany(mappedBy = "websites")
  private Set<User> users = new HashSet<>();

  public WebsiteId getWebsiteId() {
    return this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Website website = (Website) o;
    return Objects.equals(id, website.id) && Objects.equals(url, website.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url);
  }
}

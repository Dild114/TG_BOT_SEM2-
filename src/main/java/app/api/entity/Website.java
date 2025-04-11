package app.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
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

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Website website = (Website) o;
    return getId() != null && Objects.equals(getId(), website.getId());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(id);
  }
}
package app.api.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteId implements Serializable {
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "website_id_seq")
  @SequenceGenerator(name = "website_id_seq", sequenceName = "website_id_seq", allocationSize = 1)
  private Long id;

  public Long getId() {
    return id;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    WebsiteId websiteId = (WebsiteId) o;
    return id != null && Objects.equals(id, websiteId.id);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(id);
  }

}
package app.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

  @EmbeddedId
  private CategoryId id;

  private String name;

  @ManyToMany(mappedBy = "categories")
  private Set<Article> articles = new HashSet<>();

  @ManyToMany(mappedBy = "categories")
  private Set<User> users = new HashSet<>();


  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return Objects.equals(id, category.id) && Objects.equals(name, category.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}

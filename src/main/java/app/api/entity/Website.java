package app.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "websites")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class Website {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "website_seq")
  @SequenceGenerator(name = "website_seq", sequenceName = "website_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "url")
  private String url;

  @Column(name = "is_enabled")
  boolean isEnabled = true;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}

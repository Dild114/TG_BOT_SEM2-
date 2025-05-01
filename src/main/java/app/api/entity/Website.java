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
  private Long sourceId;

  @Column(name = "name")
  private String sourceName;

  @Column(name = "url")
  private String sourceUrl;

  @Column(name = "is_enabled")
  private boolean sourceActiveStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}

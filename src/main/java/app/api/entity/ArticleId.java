package app.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ArticleId implements Serializable {

//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
//  @SequenceGenerator(name = "article_seq", sequenceName = "article_id_seq", allocationSize = 1)
  //так не работает
  @GeneratedValue(strategy = GenerationType.IDENTITY) // так работает через sql рапрос но не работает post запрос
  private Long id;
}

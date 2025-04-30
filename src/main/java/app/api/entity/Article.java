//package app.api.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.time.OffsetDateTime;
//
//@Entity
//@Table(name = "articles")
//@Getter
//@Builder
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Article {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
//    @SequenceGenerator(name = "article_seq", sequenceName = "article_id_seq", allocationSize = 1)
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "url")
//    private String url;
//
//    @Column(name = "creation_date")
//    // offsetdatetime
//    private OffsetDateTime creationDate;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "website_id", nullable = false)
//    private Website website;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//}

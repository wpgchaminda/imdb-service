package com.imdb.service.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "title_principal",
    indexes = {
        @Index(name = "index_title_principal_id",  columnList="id ASC", unique = true),
        @Index(name = "index_title_principal_title_id", columnList="title_id ASC", unique = false),
        @Index(name = "index_title_principal_person_id", columnList="person_id ASC", unique = false),
        @Index(name = "index_title_principal_category_id", columnList="category_id ASC", unique = false)})
public class TitlePrincipal implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "title_id", referencedColumnName = "id")
  private Title title;

  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "nconst")
  private Person person;

  @Column(name = "order_id")
  private Integer orderId;

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "job_id", referencedColumnName = "id")
  private Job job;

  @Column(name = "characters")
  private String characters;
}

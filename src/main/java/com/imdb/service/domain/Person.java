package com.imdb.service.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person",
    indexes = {
        @Index(name = "index_person_id",  columnList="nconst ASC", unique = true),
        @Index(name = "index_person_primary_name", columnList="primary_name ASC", unique = false)})
public class Person implements Serializable {
  @Id
  @Column(name = "nconst")
  private String nconst;

  @Column(name = "primary_name")
  private String primaryName;

  @Column(name = "birth_year")
  private Integer birthYear;

  @Column(name = "death_year")
  private Integer deathYear;

  @Column(name = "primary_profession")
  private String primaryProfession;

  @Column(name = "known_for_titles")
  private String knownForTitles;
}

package com.imdb.service.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "title")
public class Title implements Serializable {
  @Id
  @Column(name = "id")
  private String id;

  @ManyToOne
  @JoinColumn(name = "title_type_id", referencedColumnName = "id")
  private TitleType titleType;

  @Column(name = "primary_title")
  private String primaryTitle;

  @Column(name = "original_title")
  private String originalTitle;

  @Column(name = "is_adult")
  private Boolean isAdult;

  @Column(name = "start_year")
  private Integer startYear;

  @Column(name = "end_year")
  private Integer endYear;

  @Column(name = "runtime_minutes")
  private Integer runtimeMinutes;

  @Column(name = "average_rating")
  private BigDecimal averageRating;

  @Column(name = "num_votes")
  private Integer numVotes;

  @ManyToMany
  @JoinTable(
      name = "title_genre",
      joinColumns = @JoinColumn(name = "title_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
  private Set<Genre> genres;

  @OneToMany(mappedBy = "title",fetch = FetchType.EAGER, cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<TitleCrew> titleCrews;

  @OneToMany(mappedBy = "title",fetch = FetchType.EAGER, cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<TitlePrincipal> titlePrincipals;
}

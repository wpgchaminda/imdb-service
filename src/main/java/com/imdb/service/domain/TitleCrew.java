package com.imdb.service.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "title_crew")
public class TitleCrew implements Serializable {
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

  @ManyToOne
  @JoinColumn(name = "crew_type_id", referencedColumnName = "id")
  private CrewType crewType;
}

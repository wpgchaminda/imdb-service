package com.imdb.service.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "crew_type")
public class CrewType implements Serializable {
  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;
}

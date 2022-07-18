package com.imdb.service.web.api.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonResult {
  private String id;
  private String primaryName;
  private Integer birthYear;
  private Integer deathYear;
  private String primaryProfession;
  private String knownForTitles;
}

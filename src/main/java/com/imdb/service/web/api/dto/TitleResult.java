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
public class TitleResult {
  private String id;
  private Integer typeId;
  private String type;
  private String primaryTitle;
  private String originalTitle;
  private Boolean isAdult;
  private Integer startYear;
  private Integer endYear;
  private Integer runtimeMinutes;
  private BigDecimal averageRating;
  private Integer numVotes;
  private List<String> genres;
  private List<TitleCrewResult> crews;
  private List<TitlePrincipalResult> principals;
}

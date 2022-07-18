package com.imdb.service.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BothActorsPlayedTogetherResult {
  private String titleId;
  private String primaryTitle;
  private String actorOneId;
  private String actorOneName;
  private String actorTwoId;
  private String actorTwoName;
}

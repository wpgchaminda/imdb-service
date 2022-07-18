package com.imdb.service.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TitleCrewResult {
  private String personId;
  private String personName;
  private Integer typeId;
  private String type;
}

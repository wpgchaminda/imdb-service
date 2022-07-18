package com.imdb.service.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectorAndWriterSamePersonResult {
  private String titleId;
  private String primaryTitle;
  private String personId;
  private String personName;
  private Boolean isPersonLiving;

}
